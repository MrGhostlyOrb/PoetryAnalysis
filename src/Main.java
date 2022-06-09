import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {



        // Create a JFrame with a grid layout
        JFrame frame = new JFrame("Poetry Analysis");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1));

        // Add a text area and submit button
        JTextArea textArea = new JTextArea(10, 20);
        JButton submitButton = new JButton("GENERATE RHYME SCHEME");

        //Increase font size
        Font font = textArea.getFont();
        Font newFont = new Font(font.getFontName(), font.getStyle(), font.getSize() + 15);
        textArea.setFont(newFont);
        textArea.setToolTipText("Enter a poem to generate a rhyme scheme");

        // button style
        submitButton.setFont(newFont);
        submitButton.setBackground(Color.WHITE);
        submitButton.setForeground(Color.BLACK);
        submitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        submitButton.setIcon(new ImageIcon("https://cdn3.iconfinder.com/data/icons/flat-actions-icons-9/792/Tick_Mark_Dark-512.png"));
        submitButton.setBackground(Color.YELLOW);

        //Create a label to display the rhyme scheme
        JLabel rhymeSchemeLabel = new JLabel("<html>Please enter a poem in the text area below <br> Click the button to generate a rhyme scheme.</html>", SwingConstants.CENTER);
        rhymeSchemeLabel.setFont(newFont);
        rhymeSchemeLabel.setForeground(Color.BLACK);
        rhymeSchemeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //Create a label to display the rhyme scheme
        JLabel generateLabel = new JLabel("<html>Add a \".txt\" file in the \"poetry\" directory to analyse multiple poems at once. <br> Click the button below once poems have been added.</html>", SwingConstants.CENTER);
        generateLabel.setFont(newFont);
        generateLabel.setForeground(Color.BLACK);
        generateLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton generateButton = new JButton("GENERATE RHYME SCHEME(S) FROM \"poetry\" DIRECTORY");

        // button style
        generateButton.setFont(newFont);
        generateButton.setBackground(Color.WHITE);
        generateButton.setForeground(Color.BLACK);
        generateButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        generateButton.setIcon(new ImageIcon("https://cdn3.iconfinder.com/data/icons/flat-actions-icons-9/792/Tick_Mark_Dark-512.png"));
        generateButton.setBackground(Color.YELLOW);

        //Add the text area and button to the frame
        frame.add(rhymeSchemeLabel);
        frame.add(textArea);
        frame.add(submitButton);
        frame.add(generateLabel);


        ArrayList<String> poemList = new ArrayList<>();
        String[] poemNames = new File("poetry").list();
        if (poemNames != null) {
            for (String poemFileName : poemNames) {
                if (poemFileName.endsWith("man")) {
                    continue;
                }
                poemList.add(poemFileName);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : poemList) {
            stringBuilder.append(s).append("<br>");
        }


        JLabel poetryLabel = new JLabel("<html>Poems currently in \"poetry\" directory: <br> " + stringBuilder.toString() + "</html>");
        poetryLabel.setFont(newFont);
        poetryLabel.setForeground(Color.BLACK);
        poetryLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.add(poetryLabel);
        frame.add(generateButton);

        frame.setSize(1000, 1000);
        frame.setVisible(true);

        // Add an action listener to the submit button
        submitButton.addActionListener(e -> {
            // Get the text from the text area
            String text = textArea.getText();
            // split text into arraylist of lines
            ArrayList<String> lines = new ArrayList<>(Arrays.asList(text.split("\n")));

            // Create a new poem
            Poem poem = new Poem(lines);
            // Get the rhyme scheme
            String rhymeScheme = null;
            String metaphoneRhymeScheme = null;
            String nysiisRhymeScheme = null;
            try {
                rhymeScheme = Soundex.getRhymeScheme(poem);
                metaphoneRhymeScheme = Metaphone.getRhymeScheme(poem);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            // Print the rhyme scheme
            System.out.println("Generated rhyme scheme : " + rhymeScheme);

            //show dialog of rhyme scheme
            JOptionPane.showMessageDialog(frame, "Soundex Scheme : \n" + rhymeScheme + "\n\nMetaphone Scheme : \n" + metaphoneRhymeScheme + "\n\nNysiis Scheme : \n" + nysiisRhymeScheme);

        });

        // Add an action listener to the submit button
        generateButton.addActionListener(e -> {

            //Read poems from /poetry directory
            String[] poemFiles = new File("poetry").list();
            if (poemFiles != null) {
                for (String poemFileName : poemFiles) {
                    if (poemFileName.endsWith("man")){
                        continue;
                    }
                    // create a new File instance by converting the given pathname string into an abstract pathname
                    File f = new File("poetry/" + poemFileName);

                    Scanner lineScanner = null;
                    try {
                        lineScanner = new Scanner(f);
                    } catch (FileNotFoundException exception) {
                        exception.printStackTrace();
                    }

                    ArrayList<String> lines = new ArrayList<String>();
                    while (lineScanner.hasNextLine()) {
                        lines.add(lineScanner.nextLine());
                    }

                    Poem poem = new Poem(lines);
                    poem.setName(poemFileName.replaceAll("\\.[^.]*$", ""));
                    try {
                        Soundex.getRhymeScheme(poem);
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    // Ctreate a GUI element to display the rhyme scheme and poem title
                    JFrame jFrame = new JFrame(poem.getName());
                    jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    jFrame.setLayout(new GridLayout(4, 1));
                    JLabel rhymeschemelabel = null;
                    try {
                        rhymeschemelabel = new JLabel("Soundex : " + Soundex.getRhymeScheme(poem));
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    JLabel MetaphoneLabel = null;
                    try {
                        MetaphoneLabel = new JLabel("Metaphone : " + Metaphone.getRhymeScheme(poem));
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }

                    rhymeschemelabel.setFont(new Font("Serif", Font.PLAIN, 20));
                    MetaphoneLabel.setFont(new Font("Serif", Font.PLAIN, 20));
                    JLabel poemTitleLabel = new JLabel(poem.getName());
                    poemTitleLabel.setFont(new Font("Serif", Font.PLAIN, 20));
                    jFrame.add(poemTitleLabel);
                    jFrame.add(rhymeschemelabel);
                    jFrame.add(MetaphoneLabel);

                    jFrame.pack();
                    jFrame.setVisible(true);

                }

            }
        });




    }
}
