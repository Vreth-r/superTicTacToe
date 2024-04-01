import java.util.Objects;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        Board board = new Board(); // Init board
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ST3GUI();
            }
        });
    }
}