package org.MrGhostlyOrb.PoetryAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.diogonunes.jcolor.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Use Case 1: use Ansi.colorize() to format inline
        System.out.println(Ansi.colorize("This text will be yellow on magenta", Attribute.YELLOW_TEXT(), Attribute.MAGENTA_BACK()));
        System.out.println("\n");
        ArrayList<String> lines = new ArrayList<>();
        String[] poemFiles;
        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File("./poetry");

        // Populates the array with names of files and directories
        poemFiles = f.list();
        if (poemFiles != null) {
            for(String poemFileName : poemFiles) {
                //String poemTitle = "poem_text";
                String poemTitle = poemFileName.replaceAll("\\.[^.]*$", "");
                Poem poem = new Poem("poetry/" + poemTitle);
                String rhymeScheme = poem.getRhymeScheme();
                System.out.println(rhymeScheme);

                File testFile = new File("poetry/"+ poemTitle + ".aut");
                FileWriter myWriter = new FileWriter(testFile);
                myWriter.write(rhymeScheme);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");

                //System.out.println(Arrays.toString(poemFiles));
            }
        }
    }
}
