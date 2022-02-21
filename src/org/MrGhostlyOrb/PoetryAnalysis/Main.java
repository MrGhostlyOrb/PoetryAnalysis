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
        generatePoemTest("general");
        generatePoemTest("shakespearean_sonnets");
        generatePoemTest("petrarchan_sonnets");
    }

    public static void generatePoemTest(String directory) throws IOException {
        String[] poemFiles;
        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File("./poetry/" + directory);

        // Populates the array with names of files and directories
        poemFiles = f.list();
        if (poemFiles != null) {
            for (String poemFileName : poemFiles) {
                if (!poemFileName.endsWith(".doublemetaphone") && !poemFileName.endsWith(".man") && !poemFileName.startsWith(".")) {
                    //String poemTitle = "poem_text";
                    String poemTitle = poemFileName.replaceAll("\\.[^.]*$", "");
                    Poem poem = new Poem("poetry/"+directory+"/" + poemTitle);
                    System.out.println(poem);
                    String rhymeScheme = poem.getRhymeScheme();
                    System.out.println("Calculated rhyme scheme of " + Ansi.colorize(poemTitle, Attribute.BLACK_TEXT(), Attribute.MAGENTA_BACK()) + " : " + rhymeScheme + "\n\n");

                    File testFile = new File("python_experiments/generated/"+directory+"/" + poemTitle + ".doublemetaphone");
                    FileWriter myWriter = new FileWriter(testFile);
                    myWriter.write(rhymeScheme);
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                }
            }
        }
    }
}
