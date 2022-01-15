package org.MrGhostlyOrb.PoetryAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Poem {
    private ArrayList<Stanza> stanzas;

    public Poem(String filePath){
        try{

            // Read file
            File textFile = new File(filePath);
            Scanner fileReader = new Scanner(textFile);
            ArrayList<Line> lines = new ArrayList<>();
            this.stanzas = new ArrayList<>();
            Line line;

            // Loop through all ines in the file
            while(fileReader.hasNextLine()){

                // Assign a new line variable
                line = new Line(fileReader.nextLine());

                // If the line is blank aka ' ' then create a new stanza
                if (Objects.equals(line.toString(), " ")){
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
        }
        catch(FileNotFoundException e){
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

    public static void main(String[] args) {
        Poem poem = new Poem("poem_text.txt");
        String rhymeScheme = poem.getRhymeScheme();
    }

    private String getRhymeScheme() {
        StringBuilder rhymeScheme = new StringBuilder();
        for (Stanza stanza : stanzas) {
            rhymeScheme.append(stanza.getRhymeScheme());
        }
        return rhymeScheme.toString();
    }
}
