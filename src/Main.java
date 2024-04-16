import javax.swing.*;

/* TODO
    Add error checking
    format comments to the side of lines to save space
    add gray out of all games except the winning line when board is won
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ST3GUI();
            }
        });
    }
}