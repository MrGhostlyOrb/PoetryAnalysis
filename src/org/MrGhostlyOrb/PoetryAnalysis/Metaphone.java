package org.MrGhostlyOrb.PoetryAnalysis;

/// <summary>
/// Implements the Metaphone algorithm
/// </summary>
public class Metaphone
{
    // Constants
    protected  int maxEncodedLength = 6;
    protected  char nullChar = (char)0;
    protected String vowels = "AEIOU";

    // For tracking position within current String
    protected String _text;
    protected int _pos;

    /// <summary>
    /// Encodes the given text using the Metaphone algorithm.
    /// </summary>
    /// <param name="text">Text to encode</param>
    /// <returns></returns>
    public String encode(String text)
    {
        // Process normalized text
        initializeText(normalize(text));

        // Write encoded String to StringBuilder
        StringBuilder builder = new StringBuilder();

        // Special handling of some String prefixes:
        //     PN, KN, GN, AE, WR, WH and X
        switch (peek())
        {
            case 'P':
            case 'K':
            case 'G':
                if (peek(1) == 'N')
                    moveAhead();
                break;

            case 'A':
                if (peek(1) == 'E')
                    moveAhead();
                break;

            case 'W':
                if (peek(1) == 'R')
                    moveAhead();
                else if (peek(1) == 'H')
                {
                    builder.append('W');
                    moveAhead(2);
                }
                break;

            case 'X':
                builder.append('S');
                moveAhead();
                break;
        }

        //
        while (!endOfText() && builder.length() < maxEncodedLength)
        {
            // Cache this character
            char c = peek();

            // Ignore duplicates except CC
            if (c == peek(-1) && c != 'C')
            {
                moveAhead();
                continue;
            }

            // Don't change F, J, L, M, N, R or first-letter vowel
            if (isOneOf(c, "FJLMNR") ||
                    (builder.length() == 0 && isOneOf(c, vowels)))
            {
                builder.append(c);
                moveAhead();
            }
            else
            {
                int charsConsumed = 1;

                switch (c)
                {
                    case 'B':
                        // B = 'B' if not -MB
                        if (peek(-1) != 'M' || peek(1) != nullChar)
                            builder.append('B');
                        break;

                    case 'C':
                        // C = 'X' if -CIA- or -CH-
                        // Else 'S' if -CE-, -CI- or -CY-
                        // Else 'K' if not -SCE-, -SCI- or -SCY-
                        if (peek(-1) != 'S' || !isOneOf(peek(1), "EIY"))
                        {
                            if (peek(1) == 'I' && peek(2) == 'A')
                                builder.append('X');
                            else if (isOneOf(peek(1), "EIY"))
                                builder.append('S');
                            else if (peek(1) == 'H')
                            {
                                if ((_pos == 0 && !isOneOf(peek(2), vowels)) ||
                                        peek(-1) == 'S')
                                    builder.append('K');
                                else
                                    builder.append('X');
                                charsConsumed++;    // Eat 'CH'
                            }
                            else builder.append('K');
                        }
                        break;

                    case 'D':
                        // D = 'J' if DGE, DGI or DGY
                        // Else 'T'
                        if (peek(1) == 'G' && isOneOf(peek(2), "EIY"))
                            builder.append('J');
                        else
                            builder.append('T');
                        break;

                    case 'G':
                        // G = 'F' if -GH and not B--GH, D--GH, -H--GH, -H---GH
                        // Else dropped if -GNED, -GN, -DGE-, -DGI-, -DGY-
                        // Else 'J' if -GE-, -GI-, -GY- and not GG
                        // Else K
                        if ((peek(1) != 'H' || isOneOf(peek(2), vowels)) &&
                                (peek(1) != 'N' || (peek(1) != nullChar &&
                                        (peek(2) != 'E' || peek(3) != 'D'))) &&
                                (peek(-1) != 'D' || !isOneOf(peek(1), "EIY")))
                        {
                            if (isOneOf(peek(1), "EIY") && peek(2) != 'G')
                                builder.append('J');
                            else
                                builder.append('K');
                        }
                        // Eat GH
                        if (peek(1) == 'H')
                            charsConsumed++;
                        break;

                    case 'H':
                        // H = 'H' if before or not after vowel
                        if (!isOneOf(peek(-1), vowels) || isOneOf(peek(1), vowels))
                            builder.append('H');
                        break;

                    case 'K':
                        // K = 'C' if not CK
                        if (peek(-1) != 'C')
                            builder.append('K');
                        break;

                    case 'P':
                        // P = 'F' if PH
                        // Else 'P'
                        if (peek(1) == 'H')
                        {
                            builder.append('F');
                            charsConsumed++;    // Eat 'PH'
                        }
                        else
                            builder.append('P');
                        break;

                    case 'Q':
                        // Q = 'K'
                        builder.append('K');
                        break;

                    case 'S':
                        // S = 'X' if SH, SIO or SIA
                        // Else 'S'
                        if (peek(1) == 'H')
                        {
                            builder.append('X');
                            charsConsumed++;    // Eat 'SH'
                        }
                        else if (peek(1) == 'I' && isOneOf(peek(2), "AO"))
                            builder.append('X');
                        else
                            builder.append('S');
                        break;

                    case 'T':
                        // T = 'X' if TIO or TIA
                        // Else '0' if TH
                        // Else 'T' if not TCH
                        if (peek(1) == 'I' && isOneOf(peek(2), "AO"))
                            builder.append('X');
                        else if (peek(1) == 'H')
                        {
                            builder.append('0');
                            charsConsumed++;    // Eat 'TH'
                        }
                        else if (peek(1) != 'C' || peek(2) != 'H')
                            builder.append('T');
                        break;

                    case 'V':
                        // V = 'F'
                        builder.append('F');
                        break;

                    case 'W':
                    case 'Y':
                        // W,Y = Keep if not followed by vowel
                        if (isOneOf(peek(1), vowels))
                            builder.append(c);
                        break;

                    case 'X':
                        // X = 'S' if first character (already done)
                        // Else 'KS'
                        builder.append("KS");
                        break;

                    case 'Z':
                        // Z = 'S'
                        builder.append('S');
                        break;
                }
                // Advance over consumed characters
                moveAhead(charsConsumed);
            }
        }
        // Return result
        return builder.toString();
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="text"></param>
    protected void initializeText(String text)
    {
        _text = text;
        _pos = 0;
    }

    /// <summary>
    /// Indicates if the current position is at the end of
    /// the text.
    /// </summary>
    protected boolean endOfText()
    {
       return _pos >= _text.length();
    }

    /// <summary>
    /// Moves the current position ahead one character.
    /// </summary>
    void moveAhead()
    {
        moveAhead(1);
    }

    /// <summary>
    /// Moves the current position ahead the specified number.
    /// of characters.
    /// </summary>
    /// <param name="count">Number of characters to move
    /// ahead.</param>
    void moveAhead(int count)
    {
        _pos = Math.min(_pos + count, _text.length());
    }

    /// <summary>
    /// Returns the character at the current position.
    /// </summary>
    /// <returns></returns>
    protected char peek()
    {
        return peek(0);
    }

    /// <summary>
    /// Returns the character at the specified position.
    /// </summary>
    /// <param name="ahead">Position to read relative
    /// to the current position.</param>
    /// <returns></returns>
    protected char peek(int ahead)
    {
        int pos = (_pos + ahead);
        if (pos < 0 || pos >= _text.length())
            return nullChar;
        return _text.toCharArray()[pos];
    }

    /// <summary>
    /// Indicates if the specified character occurs within
    /// the specified String.
    /// </summary>
    /// <param name="c">Character to find</param>
    /// <param name="chars">String to search</param>
    /// <returns></returns>
    protected boolean isOneOf(char c, String chars)
    {
        return (chars.indexOf(c) != -1);
    }

    /// <summary>
    /// Normalizes the given String by removing characters
    /// that are not letters and converting the result to
    /// upper case.
    /// </summary>
    /// <param name="text">Text to be normalized</param>
    /// <returns></returns>
    protected String normalize(String text)
    {
        StringBuilder builder = new StringBuilder();
        for (char c: text.toCharArray()){
            if (Character.isLetter(c))
                builder.append(Character.toUpperCase(c));
        }
        return builder.toString();
    }
}