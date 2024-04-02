import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ST3GUI extends JFrame implements ActionListener {
    private final int BOARD_SIZE = 9;
    private final int WIN_SIZE = 600;
    private JPanel[] innerGames;
    private JButton[][] buttons;
    private Board board;
    private Board.Marker turn;

    public ST3GUI() {
        setTitle("Super Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        innerGames = new JPanel[BOARD_SIZE];
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        board = new Board();
        turn = Board.Marker.X;

        // Initialize the main board
        for (int i = 0; i < BOARD_SIZE; i++) {
            innerGames[i] = new JPanel(new GridLayout(3, 3));
            for (int j = 0; j < BOARD_SIZE; j++) {
                    buttons[i][j] = new JButton(Board.Marker.B.getValue()); // Set all to blanks
                    buttons[i][j].setBackground(Color.lightGray); // Set all to light grey
                    buttons[i][j].addActionListener(this);
                    innerGames[i].add(buttons[i][j]);
            }
            add(innerGames[i]);
        }

        setSize(WIN_SIZE, WIN_SIZE); // Set window size
        setVisible(true); // Make the window visible
    }

    // ActionListener implementation
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource(); // Get whatever button was clicked
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(button == buttons[i][j]){ // Find co-ords of button clicked for cross-referencing into board object
                    if(board.setPosition(i, j, turn)){ // Attempt to set position
                        // TODO: it might be better practice to change the return of setPosition to be true if it was successful or elligible and have
                        // the game win be through a different system
                    } else {

                    }
                }
            }
        }
        // Handle button click event
        button.setText("X"); // For demonstration, always set X
        button.setBackground(Color.red);
        button.setEnabled(false); // Disable button after click
        // Here, you'd handle the game logic (e.g., checking for win/draw)
        return;
    }
}
