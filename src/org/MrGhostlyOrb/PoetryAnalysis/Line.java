package org.MrGhostlyOrb.PoetryAnalysis;

import java.util.*;

public class Line{
    public ArrayList<String> getWords() {
        return words;
    }

    private final ArrayList<String> words;

    public Line(String line){
        String[] words = line.split("\\s+");
        this.words = new ArrayList<>(Arrays.asList(words));
    }

    public String getFirstLetters() {
        StringBuilder firstLetters = new StringBuilder();
        for (String word : this.words) {
            String firstLetter;
            try{
                int i = 0;
                while (true){
                    if(Character.isLetter(word.charAt(i))){
                        firstLetter = String.valueOf(word.charAt(i)).toLowerCase();
                        break;
                    }
                    else{
                        i += 1;
                    }
                }
            }
            catch (Exception e){
                System.out.println("Exception");
                firstLetter = "";
            }

            firstLetters.append(firstLetter);

        }
        return firstLetters.toString();
    }

    public String getFirstLettersNoRepeats() {
        StringBuilder firstLetters = new StringBuilder();
        System.out.println(words);
        for (String word : this.words) {
            String firstLetter;
            try{
                int i = 0;
                while (true){
                    if(Character.isLetter(word.charAt(i))){
                        firstLetter = String.valueOf(word.charAt(i)).toLowerCase();
                        break;
                    }
                    else{
                        i += 1;
                    }
                }
            }
            catch (Exception e){
                System.out.println("Exception");
                firstLetter = "";
            }

            firstLetters.append(firstLetter);

        }
        System.out.println(firstLetters);
        char[] chars = firstLetters.toString().toCharArray();
        Set<Character> charSet = new LinkedHashSet<>();
        for (char c : chars) {
            charSet.add(c);
        }
        return charSet.toString();
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
