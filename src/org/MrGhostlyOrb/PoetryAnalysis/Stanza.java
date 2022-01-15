package org.MrGhostlyOrb.PoetryAnalysis;

import org.apache.commons.codec.language.Soundex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Stanza {
    private final ArrayList<Line> lines;

    public Stanza(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public String getRhymeScheme() {
        String rhymeScheme = "";
        HashMap<String, String> sounds = new HashMap<>();
        Soundex soundex = new Soundex();

        for (Line line : lines) {
            String lastWord = line.getLastWord();
            String sound = soundex.soundex(lastWord);
            sounds.put(lastWord, sound);
        }

        for (Map.Entry<String, String> entry : sounds.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            System.out.println(key);
            System.out.println(value);
        }

        return rhymeScheme;
    }
}
