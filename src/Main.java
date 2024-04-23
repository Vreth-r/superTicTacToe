import javax.swing.*;

// TODO:
// Game doesnt stop when row is won
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ST3GUI();
            }
        });
    }
}