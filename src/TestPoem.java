import java.util.ArrayList;

public class TestPoem {
    static Poem getTestPoem() {
        Poem poem = new Poem();
        poem.setName("The_Raven");
        ArrayList<String> lines = new ArrayList<>();
        lines.add("The Raven");
        lines.add("Once upon a midnight dreary");
        lines.add("We had no dream");
        poem.setLines(lines);
        return poem;
    }
}
