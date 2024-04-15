import javax.swing.*;

/* TODO
    Add error checking
    format comments to the side of lines to save space
    fix green active playable bug
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