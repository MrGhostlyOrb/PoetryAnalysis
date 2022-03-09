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
    private ArrayList<Line> lines;
    private String rhymeScheme;
    private ArrayList<LinkedHashMap<Character, Character>> alliterationScheme;


    public Poem(String filePath) {
        try {
            // Read file
            File textFile = new File(filePath);
            Scanner fileReader = new Scanner(textFile);
            this.lines = new ArrayList<>();
            this.stanzas = new ArrayList<>();
            Line line;

            // Loop through all ines in the file
            while (fileReader.hasNextLine()) {

                // Assign a new line variable
                line = new Line(fileReader.nextLine());

                // If the line is blank aka ' ' then create a new stanza
                if (Objects.equals(line.toString(), " ")) {
                    Stanza stanza = new Stanza(lines);
                    this.stanzas.add(stanza);
                    lines = new ArrayList<>();
                }

                // Otherwise, add the line to the list
                else {
                    lines.add(line);
                }
            }

            // Add final stanza to poem
            Stanza stanza = new Stanza(lines);
            this.stanzas.add(stanza);
            fileReader.close();
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
        HashMap<Character, Attribute> rhymeColour = new HashMap<>();
        rhymeColour.put('A', Attribute.YELLOW_TEXT());
        rhymeColour.put('B', Attribute.BRIGHT_BLUE_TEXT());
        rhymeColour.put('C', Attribute.BLUE_TEXT());
        rhymeColour.put('D', Attribute.BRIGHT_CYAN_TEXT());
        rhymeColour.put('E', Attribute.BRIGHT_GREEN_TEXT());
        rhymeColour.put('F', Attribute.BRIGHT_MAGENTA_TEXT());
        rhymeColour.put('G', Attribute.BRIGHT_RED_TEXT());
        rhymeColour.put('H', Attribute.BRIGHT_YELLOW_TEXT());
        rhymeColour.put('I', Attribute.CYAN_TEXT());
        rhymeColour.put('J', Attribute.GREEN_TEXT());
        rhymeColour.put('K', Attribute.MAGENTA_TEXT());
        rhymeColour.put('L', Attribute.RED_TEXT());
        rhymeColour.put('M', Attribute.YELLOW_TEXT());
        rhymeColour.put('N', Attribute.YELLOW_TEXT());
        rhymeColour.put('O', Attribute.YELLOW_TEXT());
        rhymeColour.put('P', Attribute.YELLOW_TEXT());
        rhymeColour.put('Q', Attribute.YELLOW_TEXT());
        rhymeColour.put('R', Attribute.YELLOW_TEXT());
        rhymeColour.put('S', Attribute.YELLOW_TEXT());
        rhymeColour.put('T', Attribute.YELLOW_TEXT());
        rhymeColour.put('U', Attribute.YELLOW_TEXT());
        rhymeColour.put('V', Attribute.YELLOW_TEXT());
        rhymeColour.put('W', Attribute.YELLOW_TEXT());
        rhymeColour.put('X', Attribute.YELLOW_TEXT());
        rhymeColour.put('Y', Attribute.YELLOW_TEXT());
        rhymeColour.put('Z', Attribute.YELLOW_TEXT());
        String getRhyme = getRhymeScheme();
        ArrayList<LinkedHashMap<Character, Character>> getAlliterationScheme = getAlliterationScheme();
        System.out.println(getAlliterationScheme);
        for (int i = 0; i < lines.size(); i++) {
            char rhyme = getRhyme.charAt(i);

            ArrayList<Line> colouredLines = lines;
            String colouredLine = colouredLines.get(i).removeLastWord() + Ansi.colorize(colouredLines.get(i).getLastWord(), rhymeColour.get(rhyme));
            Line line = new Line(colouredLine);
            System.out.println(line);
            String firstLetters = line.getFirstLettersNoRepeats();
            for (int j = 0; j < lines.get(i).getWords().size(); j++) {
                String word = lines.get(i).getWords().get(j);
                System.out.println(lines.get(i).getWords().size());
                System.out.println("Word : " + word);
                System.out.println(getAlliterationScheme.get(i));
                char alliterationColour = getAlliterationScheme.get(i).get(0);
                System.out.println(alliterationColour);
            }
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
        File f = new File("./poetry");

        // Populates the array with names of files and directories
        poemFiles = f.list();
        if (poemFiles != null) {
            for (String poemFileName : poemFiles) {
                if (!poemFileName.endsWith(".aut") && !poemFileName.endsWith(".man")) {
                    //String poemTitle = "poem_text";
                    String poemTitle = poemFileName.replaceAll("\\.[^.]*$", "");
                    Poem poem = new Poem("poetry/" + poemTitle);
                    System.out.println(poem);
                    String rhymeScheme = poem.getRhymeScheme();
                    System.out.println("Calculated rhyme scheme of " + Ansi.colorize(poemTitle, Attribute.BLACK_TEXT(), Attribute.MAGENTA_BACK()) + " : " + rhymeScheme + "\n\n");

                    File testFile = new File("poetry/" + poemTitle + ".aut");
                    FileWriter myWriter = new FileWriter(testFile);
                    myWriter.write(rhymeScheme);
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                }
            }
        }
    }

    public String createRhymeScheme() {
        StringBuilder rhymeScheme = new StringBuilder();
        Soundex soundex = new Soundex();
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int alphabetCounter = 0;
        for (Stanza stanza : stanzas) {
            LinkedHashMap<String, Character> soundsAndLetters = new LinkedHashMap<>();
            LinkedHashMap<String, String> sounds = new LinkedHashMap<>();
            for (Line line : stanza.getLines()) {
                String lastWord = line.getLastWord();
                String sound = soundex.soundex(lastWord);
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
