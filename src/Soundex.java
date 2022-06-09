import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Soundex {
    public static void main(String[] args) {

    }

    public static String getRhymeScheme(Poem poem) throws FileNotFoundException {


        ArrayList<String> encodings = new ArrayList<>();
        String actualRhyme = poem.getActualRhymeScheme();

        // loop through each line of the poem
        for (String line : poem.getLines()) {
            // line is a single line of the poem
            // get the last word of the line
            String lastWord = line.split(" ")[line.split(" ").length - 1];
            org.apache.commons.codec.language.Soundex soundex = new org.apache.commons.codec.language.Soundex();
            String encoding = soundex.encode(lastWord);
            // Remove first letter of encoding
            try {
                encoding = encoding.substring(1);
            }
            catch (Exception e){
                System.out.println("Encoding failed");
            }

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
                // If the encoding is +- 50 then classify it as the same
                int encodingInt = Integer.parseInt(encoding);
                int lowerBound = encodingInt - 30;
                int upperBound = encodingInt + 30;
                // If anything in the rhymeMap is within the bounds of the encoding then classify it as the same
                // Create a copy of the rhymeMap
                LinkedHashMap<String, Character> rhymeMapCopy = new LinkedHashMap<>(rhymeMap);
                for (String key : rhymeMapCopy.keySet()) {
                    int keyInt = Integer.parseInt(key);
                    if (keyInt >= lowerBound && keyInt <= upperBound) {
                        rhymeMap.put(encoding, rhymeMap.get(key));
                        break;
                    }
                    else{
                        rhymeMap.put(encoding, alphabetArray[i]);
                    }
                }
            }
            i++;
        }
        System.out.println("Rhyme map : " + rhymeMap);

        // loop through each line of the poem
        for(String line : poem.getLines()) {
            // line is a single line of the poem
            // get the last word of the line
            String lastWord = line.split(" ")[line.split(" ").length - 1];
            org.apache.commons.codec.language.Soundex soundex = new org.apache.commons.codec.language.Soundex();
            String encoding = soundex.encode(lastWord);
            // Remove first letter of encoding
            try {
                encoding = encoding.substring(1);
            }
            catch (Exception e){
                System.out.println("Encoding failed");
            }

            // Add encoding to list of encodings
            encodings.add(encoding);

            System.out.println(line + " : " + rhymeMap.get(encoding));
            rhymeScheme.add(rhymeMap.get(encoding).toString());
        }

        if (actualRhyme.length() > 0){
            // Check percentage correct of rhymeScheme and actualRhyme
            int correct = 0;
            for (int j = 0; j < rhymeScheme.size(); j++) {
                if (rhymeScheme.get(j).equals(actualRhyme.substring(j, j + 1))) {
                    correct++;
                }
            }
            System.out.println("Percentage correct: " + (correct / (double) rhymeScheme.size())*100 + "%");
        }

        return rhymeScheme.toString();


    }
}
