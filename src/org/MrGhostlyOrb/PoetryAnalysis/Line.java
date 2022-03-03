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

    public ArrayList<Character> getFirstLetters(){
        ArrayList<Character> firstLetters = new ArrayList<>();
        for (String word : words) {
            char firstLetter = word.charAt(0);
            firstLetters.add(firstLetter);
        }
        return firstLetters;
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

    public String removeLastWord() {
        StringBuilder line = new StringBuilder();
       for (int i =0; i<words.size(); i++){
           if (i != words.size() - 1) {
               line.append(words.get(i)).append(" ");
           }

       }
       return line.toString();

    }
}
