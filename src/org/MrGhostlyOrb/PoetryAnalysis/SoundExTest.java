package org.MrGhostlyOrb.PoetryAnalysis;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Soundex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SoundExTest {
    public static void main(String[] args) throws EncoderException {
        Soundex soundex = new Soundex();
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> endWords = new ArrayList<>();
        lines.add("Soft on the sunset sky");
        lines.add("Bright daylight closes,Leaving, when light doth die,");
        lines.add("Pale hues that mingling lie,—");
        lines.add("Ashes of roses.");

        for (String line : lines) {
            Collections.addAll(words, line.split(" "));

            //Get all end words
            endWords.add(words.get(words.size() -1));
        }

        Map<Integer, ArrayList<Integer>> dict = new HashMap<>();

        //Compare every word to every other word and check rhymes
        for (int i = 0; i < endWords.size(); i++){
            ArrayList<Integer> rhymingLines = new ArrayList<>();
            for(int j = 0; j < endWords.size(); j++){
                System.out.println("Comparing : " + endWords.get(i) + " and " + endWords.get(j));
                int diff = soundex.difference(endWords.get(i), endWords.get(j));
                System.out.println(diff);

                if(diff > 1){
                    //If the rhyme is strong enough add it to the dictionary
                    rhymingLines.add(j);
                }


            }

            //Line x rhymes with lines x, y, z
            dict.put(i, rhymingLines);
        }

        Map <Character, Integer> rhymeScheme = new HashMap<>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int alphabetCounter = 0;

        for(int i = 0; i < dict.size(); i++){
            for(int j = 0; j < dict.size(); j++){
                if(dict.get(i) ==  dict.get(j)){
                    rhymeScheme.put(alphabet[alphabetCounter], i);
                }
                else{
                    alphabetCounter += 1;
                }
            }

        }

        System.out.println(rhymeScheme);


    }
}
