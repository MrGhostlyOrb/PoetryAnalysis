package org.MrGhostlyOrb.PoetryAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Poetry Analysis...\n");

        ArrayList<String> lines = new ArrayList<>();

        try{
            File textFile = new File("poem_text.txt");
            Scanner fileReader = new Scanner(textFile);
            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                lines.add(line);
            }
            fileReader.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("No file found, exiting...");
            return;
        }

        System.out.println("Poem line by line readout : \n");
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println("\n");
        System.out.println("Now for some analysis...\n");
        System.out.println("Section 1: Is it a poem?\n");

        int correctLines = 0;
        for(String line : lines){
            if(line.endsWith(",") || line.endsWith(".")){
                correctLines += 1;
            }
            else{
                System.out.println("It's not a poem :(");
            }
        }

        if (correctLines == lines.size()){
            System.out.println("It's a poem!");
        }

    }
}
