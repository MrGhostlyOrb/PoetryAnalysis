package org.MrGhostlyOrb.PoetryAnalysis;

import java.util.ArrayList;
import java.util.Arrays;

public class Line {
    private final ArrayList<String> words;

    public Line(String line){
        String[] words = line.split("\\s+");
        this.words = new ArrayList<>(Arrays.asList(words));
    }

    public String getLastWord() {
        return words.get(words.size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        for (String word: this.words) {
            word += " ";
            line.append(word);
        }
        return line.toString();
    }
}
