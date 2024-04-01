import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ST3GUI extends JFrame implements ActionListener {
    private final int BOARD_SIZE = 3;
    private JPanel[][] innerGames;
    private JButton[][][] buttons;

    public ST3GUI() {
        setTitle("Nested Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        innerGames = new JPanel[BOARD_SIZE][BOARD_SIZE];
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE][BOARD_SIZE * BOARD_SIZE];

        // Initialize the main board
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                innerGames[i][j] = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
                for (int k = 0; k < BOARD_SIZE * BOARD_SIZE; k++) {
                    buttons[i][j][k] = new JButton("");
                    buttons[i][j][k].addActionListener(this);
                    innerGames[i][j].add(buttons[i][j][k]);
                }
                add(innerGames[i][j]);
            }
        }

        setSize(300, 300); // Set window size
        setVisible(true); // Make the window visible
    }

    // ActionListener implementation
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        // Handle button click event
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                for (int k = 0; k < BOARD_SIZE * BOARD_SIZE; k++) {
                    if (buttons[i][j][k] == button) {
                        button.setText("X"); // For demonstration, always set X
                        button.setEnabled(false); // Disable button after click
                        // Here, you'd handle the game logic (e.g., checking for win/draw)
                        return;
                    }
                }
            }
        }
    }
}
