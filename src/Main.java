import java.util.Objects;
import javax.swing.*;

/* TODO
    Add error checking
    format comments to the side of lines to save space
    Updates to Board.java for unique ST3 rules
 */
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