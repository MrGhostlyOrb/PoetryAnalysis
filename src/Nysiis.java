import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Nysiis {

    // Test harness
    public static void main(String[] args) {
        Poem poem = TestPoem.getTestPoem();
        try {
            System.out.println(Nysiis.getRhymeScheme(poem));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getRhymeScheme(Poem poem) throws FileNotFoundException {
        ArrayList<String> encodings = new ArrayList<>();
        String actualRhyme = poem.getActualRhymeScheme();

        // loop through each line of the poem
        for (String line : poem.getLines()) {
            // line is a single line of the poem
            // get the last word of the line
            String lastWord = line.split(" ")[line.split(" ").length - 1];
            org.apache.commons.codec.language.Nysiis nysiis = new org.apache.commons.codec.language.Nysiis();
            String encoding = nysiis.encode(lastWord);
            // Get the last 2 letters of encoding
            encoding = encoding.substring(encoding.length() - 2);
            // Add encoding to list of encodings
            encodings.add(encoding);
        }

        System.out.println(encodings);
        // Get the alphabet
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // get alphabet array
        char[] alphabetArray = alphabet.toCharArray();

        ArrayList<String> rhymeScheme = new ArrayList<>();

        LinkedHashMap<String, Character> rhymeMap = new LinkedHashMap<>();

        int i = 0;

        // loop through encodings
        for (String encoding : encodings) {
            if (rhymeMap.containsKey(encoding)) {
                continue;
            }

            if(i == 0) {
                rhymeMap.put(encoding, alphabetArray[0]);
            }
            else{
                rhymeMap.put(encoding, alphabetArray[i]);
            }
            i++;
        }

        System.out.println(rhymeMap);

        // Loop through each line of the poem
        for (String line : poem.getLines()) {
            // line is a single line of the poem
            // get the last word of the line
            String lastWord = line.split(" ")[line.split(" ").length - 1];
            org.apache.commons.codec.language.Nysiis nysiis = new org.apache.commons.codec.language.Nysiis();
            String encoding = nysiis.encode(lastWord);
            // Get the last 2 letters of encoding
            encoding = encoding.substring(encoding.length() - 2);
            // Add encoding to list of encodings
            encodings.add(encoding);

            System.out.println(line + " : " + rhymeMap.get(encoding));
            rhymeScheme.add(rhymeMap.get(encoding).toString());
        }
        if (actualRhyme.length() > 0) {
            int correct = 0;
            for (int j = 0; j < rhymeScheme.size(); j++) {
                if (rhymeScheme.get(j).equals(actualRhyme.charAt(j))) {
                    correct++;
                }
            }
            System.out.println("Percentage correct: " + (correct / (double) rhymeScheme.size()));

        }
        return rhymeScheme.toString();

    }
}
