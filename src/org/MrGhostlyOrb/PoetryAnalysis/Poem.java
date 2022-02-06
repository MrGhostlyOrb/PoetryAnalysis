package org.MrGhostlyOrb.PoetryAnalysis;

import org.apache.commons.codec.language.Soundex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Poem {
    private ArrayList<Stanza> stanzas;

    public Poem(String filePath) {
        try {
            // Read file
            File textFile = new File(filePath);
            Scanner fileReader = new Scanner(textFile);
            ArrayList<Line> lines = new ArrayList<>();
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("No file found, exiting...");
        }
    }
    @Override
    public String toString() {
        return "Poem{" +
                "stanzas=" + stanzas +
                '}';
    }

    public static void main(String[] args) throws IOException {
        String[] poemFiles;
        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File("./poetry");

        // Populates the array with names of files and directories
        poemFiles = f.list();
        for(String poemFileName : poemFiles) {
            //String poemTitle = "poem_text";
            String poemTitle = poemFileName.replaceAll("\\.[^.]*$", "");
            Poem poem = new Poem("poetry/" + poemTitle + ".txt");
            String rhymeScheme = poem.getRhymeScheme();
            System.out.println(rhymeScheme);

            File testFile = new File("poetry/"+ poemTitle + ".lab");
            FileWriter myWriter = new FileWriter(testFile);
            myWriter.write(rhymeScheme);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");

            //System.out.println(Arrays.toString(poemFiles));
        }

    }
    private String getRhymeScheme() {
        StringBuilder rhymeScheme = new StringBuilder();
        //Using Linked HashMap to retain insertion order.
        Metaphone metaphone = new Metaphone();
        Soundex soundex = new Soundex();
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int alphabetCounter = 0;
        String previousSound = "";
        for (Stanza stanza : stanzas) {
            LinkedHashMap<String, Character> soundsAndLetters = new LinkedHashMap<>();
            LinkedHashMap<String, String> sounds = new LinkedHashMap<>();
            for (Line line : stanza.getLines()) {
                String lastWord = line.getLastWord();
                String sound = metaphone.Encode(lastWord);
                String soundexSound = soundex.soundex(lastWord);
                System.out.println(sound);
                //System.out.println(sound2);
                sounds.put(lastWord, sound);
            }
            //Convert sounds into arraylists.
            List<String> listValues = new ArrayList<String>(sounds.values());
            for (int i = 0; i < listValues.size(); i++) {
                //Firs case of the line for the stanza
                StringBuilder stringBuilder = new StringBuilder();
                String currentSound = listValues.get(i).substring(1);
                if (i == 0) {
                    stringBuilder.append(alphabet[alphabetCounter]);
                    rhymeScheme.append(stringBuilder.toString());
                    //Store the letter into the hashmap for its sound.
                    soundsAndLetters.put(currentSound, alphabet[alphabetCounter]);
                } else if (soundsAndLetters.containsKey(currentSound)) {
                    //Rhyme was found.
                    Character alphabetSoundLetter = soundsAndLetters.get(currentSound);
                    stringBuilder.append(alphabetSoundLetter.toString());
                    rhymeScheme.append(stringBuilder.toString());
                } else {
                    alphabetCounter += 1;
                    stringBuilder.append(alphabet[alphabetCounter]);
                    rhymeScheme.append(stringBuilder.toString());

                }
            }
            //rhymeScheme.append(stanza.getRhymeScheme());
            alphabetCounter += 1;
        }
        return rhymeScheme.toString();
    }


}
