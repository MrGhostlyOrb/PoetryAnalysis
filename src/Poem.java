import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Poem {
    private ArrayList<String> lines;
    private String name;

    public Poem(ArrayList<String> lines) {
        this.lines = lines;
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActualRhymeScheme() throws FileNotFoundException {
        StringBuilder rhymeScheme = new StringBuilder();
        // read .man file for poem
        String line = "";
        try{
            File file = new File("poetry/" + this.name + ".man");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()){
                //Split line into chars
                line = scanner.nextLine();
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Manually labelled file not found...");
        }

        return line;
    }

    public String getName() {
        return name;
    }
}
