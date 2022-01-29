package org.MrGhostlyOrb.PoetryAnalysis;

import org.apache.commons.codec.language.Soundex;

import java.util.*;

public class Stanza {

    private final ArrayList<Line> lines;
    char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    int alphabetCounter = 0;
    String previousSound = "";
    LinkedHashMap<String, Character> soundsAndLetters = new LinkedHashMap<>();

    public Stanza(ArrayList<Line> lines) {
        this.lines = lines;

    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public String getRhymeScheme() {
        StringBuilder rhymeScheme = new StringBuilder();
        //Using Linked HashMap to retain insertion order.
        LinkedHashMap<String, String> sounds = new LinkedHashMap<>();
        Soundex soundex = new Soundex();

        for (Line line : lines) {
            String lastWord = line.getLastWord();
            String sound = soundex.soundex(lastWord);
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
                Character alphabetSoundLetter = soundsAndLetters.get(currentSound);
                stringBuilder.append(alphabetSoundLetter.toString());
                rhymeScheme.append(stringBuilder.toString());
            } else {
                alphabetCounter+=1;
                stringBuilder.append(alphabet[alphabetCounter]);
                rhymeScheme.append(stringBuilder.toString());

            }
        }
        return rhymeScheme.toString();
    }


}

