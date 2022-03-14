package org.MrGhostlyOrb.PoetryAnalysis;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import org.apache.commons.codec.language.Soundex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Poem {
    private ArrayList<Stanza> stanzas;
    private ArrayList<Line> lines = new ArrayList<>();
    private String rhymeScheme;
    private ArrayList<LinkedHashMap<Character, Character>> alliterationScheme;


    public Poem(String filePath) {
        try {
            // Read file
            File textFile = new File(filePath);
            Scanner fileReader = new Scanner(textFile);
            this.lines = new ArrayList<>();
            ArrayList<Line> poemLines = new ArrayList<>();
            this.stanzas = new ArrayList<>();
            Line line;

            // Loop through all ines in the file
            while (fileReader.hasNextLine()) {

                // Assign a new line variable
                line = new Line(fileReader.nextLine());

                // If the line is blank aka ' ' then create a new stanza
                if (Objects.equals(line.toString(), " ")) {
                    Stanza stanza = new Stanza(poemLines);
                    this.stanzas.add(stanza);
                }

                // Otherwise, add the line to the list
                else {
                    poemLines.add(line);
                }
            }

            // Add final stanza to poem
            Stanza stanza = new Stanza(poemLines);
            this.stanzas.add(stanza);
            fileReader.close();
            for (Stanza sta : this.stanzas) {
                this.lines.addAll(sta.getLines());
            }
            this.rhymeScheme = createRhymeScheme();
            this.alliterationScheme = createAlliterationScheme();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("No file found, exiting...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        HashMap<Character, Attribute> alliterationColours = new HashMap<>();
        alliterationColours.put('A', Attribute.YELLOW_TEXT());
        alliterationColours.put('B', Attribute.BRIGHT_BLUE_TEXT());
        alliterationColours.put('C', Attribute.BLUE_TEXT());
        alliterationColours.put('D', Attribute.BRIGHT_CYAN_TEXT());
        alliterationColours.put('E', Attribute.BRIGHT_GREEN_TEXT());
        alliterationColours.put('F', Attribute.BRIGHT_MAGENTA_TEXT());
        alliterationColours.put('G', Attribute.BRIGHT_RED_TEXT());
        alliterationColours.put('H', Attribute.BRIGHT_YELLOW_TEXT());
        alliterationColours.put('I', Attribute.CYAN_TEXT());
        alliterationColours.put('J', Attribute.GREEN_TEXT());
        alliterationColours.put('K', Attribute.MAGENTA_TEXT());
        alliterationColours.put('L', Attribute.RED_TEXT());
        alliterationColours.put('M', Attribute.YELLOW_TEXT());
        alliterationColours.put('N', Attribute.BRIGHT_BLUE_TEXT());
        alliterationColours.put('O', Attribute.BLUE_TEXT());
        alliterationColours.put('P', Attribute.BRIGHT_CYAN_TEXT());
        alliterationColours.put('Q', Attribute.BRIGHT_GREEN_TEXT());
        alliterationColours.put('R', Attribute.BRIGHT_MAGENTA_TEXT());
        alliterationColours.put('S', Attribute.BRIGHT_RED_TEXT());
        alliterationColours.put('T', Attribute.BRIGHT_YELLOW_TEXT());
        alliterationColours.put('U', Attribute.CYAN_TEXT());
        alliterationColours.put('V', Attribute.GREEN_TEXT());
        alliterationColours.put('W', Attribute.MAGENTA_TEXT());
        alliterationColours.put('X', Attribute.RED_TEXT());
        alliterationColours.put('Y', Attribute.YELLOW_TEXT());
        alliterationColours.put('Z', Attribute.BRIGHT_BLUE_TEXT());

        HashMap<Character, Attribute> rhymeColours = new HashMap<>();
        rhymeColours.put('A', Attribute.YELLOW_BACK());
        rhymeColours.put('B', Attribute.BRIGHT_BLUE_BACK());
        rhymeColours.put('C', Attribute.BLUE_BACK());
        rhymeColours.put('D', Attribute.BRIGHT_CYAN_BACK());
        rhymeColours.put('E', Attribute.BRIGHT_GREEN_BACK());
        rhymeColours.put('F', Attribute.BRIGHT_MAGENTA_BACK());
        rhymeColours.put('G', Attribute.BRIGHT_RED_BACK());
        rhymeColours.put('H', Attribute.BRIGHT_YELLOW_BACK());
        rhymeColours.put('I', Attribute.CYAN_BACK());
        rhymeColours.put('J', Attribute.GREEN_BACK());
        rhymeColours.put('K', Attribute.MAGENTA_BACK());
        rhymeColours.put('L', Attribute.RED_BACK());
        rhymeColours.put('M', Attribute.YELLOW_BACK());
        rhymeColours.put('N', Attribute.BRIGHT_BLUE_BACK());
        rhymeColours.put('O', Attribute.BLUE_BACK());
        rhymeColours.put('P', Attribute.BRIGHT_CYAN_BACK());
        rhymeColours.put('Q', Attribute.BRIGHT_GREEN_BACK());
        rhymeColours.put('R', Attribute.BRIGHT_MAGENTA_BACK());
        rhymeColours.put('S', Attribute.BRIGHT_RED_BACK());
        rhymeColours.put('T', Attribute.BRIGHT_YELLOW_BACK());
        rhymeColours.put('U', Attribute.CYAN_BACK());
        rhymeColours.put('V', Attribute.GREEN_BACK());
        rhymeColours.put('W', Attribute.MAGENTA_BACK());
        rhymeColours.put('X', Attribute.RED_BACK());
        rhymeColours.put('Y', Attribute.YELLOW_BACK());
        rhymeColours.put('Z', Attribute.BRIGHT_BLUE_BACK());

        String getRhyme = getRhymeScheme();
        ArrayList<LinkedHashMap<Character, Character>> getAlliterationScheme = getAlliterationScheme();

        ArrayList<String> computedColouredLines = new ArrayList<>();

        for (int i = 0; i < getRhyme.toCharArray().length; i++) {
            char rhyme = getRhyme.charAt(i);

            ArrayList<Line> colouredLines = this.lines;
            String colouredLine = colouredLines.get(i).removeLastWord() + Ansi.colorize(colouredLines.get(i).getLastWord(), rhymeColours.get(rhyme));
            computedColouredLines.add(colouredLine);
        }

        // Loop to add colour to alliteration
        System.out.println("Poem with highlighted alliteration : \n");
        for (int i = 0; i < this.lines.size(); i++) {

            String firstLetters = this.lines.get(i).getFirstLetters();
            ArrayList<String> colouredWords = new ArrayList<>();
            for (int j = 0; j < lines.get(i).getWords().size(); j++) {
                String word = lines.get(i).getWords().get(j);
                char alliterationColour = getAlliterationScheme.get(i).get(firstLetters.toCharArray()[j]);

                // Only highlight words longer than 2 letters
                // Could also remove any stop words here
                if (word.toCharArray().length > 2){
                    String colouredWord = Ansi.colorize(word.substring(0, 1), alliterationColours.get(alliterationColour)) + word.substring(1);
                    colouredWords.add(colouredWord);
                }
                else{
                    colouredWords.add(word);
                }

            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String word : colouredWords){

                stringBuilder.append(word).append(" ");
            }

            // Print line with alliteration highlighted
            System.out.println(stringBuilder.toString());
        }

        System.out.println();
        System.out.println("Poem with highlighted Rhyme Scheme : \n");

        for (int i = 0; i < computedColouredLines.size(); i++) {

            // Print line with rhyme scheme highlighted
            System.out.println(computedColouredLines.get(i));
        }
        return "";
    }

    private ArrayList<LinkedHashMap<Character, Character>> getAlliterationScheme() {
        return alliterationScheme;
    }

    public String getRhymeScheme() {
        return rhymeScheme;
    }

    public static void main(String[] args) throws IOException {
        String[] poemFiles;
        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File("./poetry/general");

        // Populates the array with names of files and directories
        poemFiles = f.list();
        if (poemFiles != null) {
            for (String poemFileName : poemFiles) {
                if (!poemFileName.endsWith(".aut") && !poemFileName.endsWith(".man")) {
                    //String poemTitle = "poem_text";
                    String poemTitle = poemFileName.replaceAll("\\.[^.]*$", "");
                    Poem poem = new Poem("poetry/general/" + poemTitle);
                    System.out.println(poem);
                    String rhymeScheme = poem.getRhymeScheme();
                    System.out.println("Calculated rhyme scheme of " + Ansi.colorize(poemTitle, Attribute.BLACK_TEXT(), Attribute.MAGENTA_BACK()) + " : " + rhymeScheme + "\n\n");

                    File testFile = new File("poetry/general/" + poemTitle + ".aut");
                    FileWriter myWriter = new FileWriter(testFile);
                    myWriter.write(rhymeScheme);
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.\n");
                }
            }
        }
    }

    public String createRhymeScheme() {
        StringBuilder rhymeScheme = new StringBuilder();
        Soundex soundex = new Soundex();
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int alphabetCounter = 0;
        for (Stanza stanza : stanzas) {
            LinkedHashMap<String, Character> soundsAndLetters = new LinkedHashMap<>();
            LinkedHashMap<String, String> sounds = new LinkedHashMap<>();
            for (Line line : stanza.getLines()) {
                String lastWord = line.getLastWord();
                String sound = soundex.encode(lastWord);
                sounds.put(lastWord, sound);
            }
            //Change this value to change algorithm
            List<String> values = new ArrayList<>(sounds.values());
            for (int i = 0; i < values.size(); i++) {
                //First case of the line for the stanza
                StringBuilder stringBuilder = new StringBuilder();

                //For soundex analysis
                String currentSound = values.get(i).substring(1);

                if (i == 0) {
                    stringBuilder.append(alphabet[alphabetCounter]);
                    rhymeScheme.append(stringBuilder.toString());
                    //Store the letter into the hashmap for its sound.
                    soundsAndLetters.put(currentSound, alphabet[alphabetCounter]);
                } else if (soundsAndLetters.containsKey(currentSound)) {
                    //Rhyme was found.
                    Character alphabetSoundLetter = null;
                    if (soundsAndLetters.containsKey(currentSound)) {
                        alphabetSoundLetter = soundsAndLetters.get(currentSound);
                    }
                    assert alphabetSoundLetter != null;
                    stringBuilder.append(alphabetSoundLetter.toString());
                    rhymeScheme.append(stringBuilder.toString());
                } else {
                    alphabetCounter += 1;
                    stringBuilder.append(alphabet[alphabetCounter]);
                    rhymeScheme.append(stringBuilder.toString());
                }
            }
            alphabetCounter += 1;
        }
        return rhymeScheme.toString();
    }

    public ArrayList<LinkedHashMap<Character, Character>> createAlliterationScheme() throws Exception {
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        ArrayList<LinkedHashMap<Character, Character>> alliteration = new ArrayList<>();
        for (Stanza stanza : stanzas) {
            for (Line line : stanza.getLines()) {
                int alphabetCounter = 0;
                LinkedHashMap<Character, Character> letters = new LinkedHashMap<>();
                String lineLetters = line.getFirstLetters();
                for (int i = 0; i < lineLetters.toCharArray().length; i++) {
                    char chara = lineLetters.charAt(i);
                    if (!letters.containsKey(chara)){
                        letters.put(chara, alphabet[alphabetCounter]);
                        alphabetCounter+=1;
                    }
                    else{
                        letters.put(chara, letters.get(chara));
                    }
                }
                alliteration.add(letters);

            }

        }
        return alliteration;
    }
}
