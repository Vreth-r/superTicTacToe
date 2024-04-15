import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ST3GUI extends JFrame implements ActionListener {
    private final int BOARD_SIZE = 9;
    private final int WIN_SIZE = 600;
    private JPanel[] innerGames;
    private JButton[][] buttons;
    private JLabel statusMessage;
    private String smString;
    private Board board;
    private ToggleSwitch<Board.Marker> turn; // See the bottom class for implementation
    private ToggleSwitch<Color> turnColor;
    private ToggleSwitch<Color> winColor;
    private ToggleSwitch<Color> activeColor;
    private int status; // Used for board status checking
    private boolean gameDone;

    public ST3GUI() {
        setTitle("Super Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 3, 4, 4));

        this.innerGames = new JPanel[BOARD_SIZE];
        this.smString = "SUPER TIC TAC TOE";
        this.statusMessage = new JLabel(smString);
        this.statusMessage.setHorizontalAlignment(JLabel.CENTER);
        JLabel spacer = new JLabel("Press X to exit"); // Label for formatting, otherwise the status message would be on the left side
        spacer.setHorizontalAlignment(JLabel.CENTER);
        this.buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        this.board = new Board();
        this.turn = new ToggleSwitch<Board.Marker>(Board.Marker.X, Board.Marker.O);
        this.turnColor = new ToggleSwitch<Color>(Color.red, Color.blue);
        this.winColor = new ToggleSwitch<Color>(Color.pink, Color.cyan);
        this.gameDone = false;

        // Initialize the main board
        for (int i = 0; i < BOARD_SIZE; i++) {
            this.innerGames[i] = new JPanel(new GridLayout(3, 3));
            for (int j = 0; j < BOARD_SIZE; j++) {
                    this.buttons[i][j] = new JButton(Board.Marker.B.getValue()); // Set all to blanks
                    this.buttons[i][j].setBackground(Color.green); // Set all to light grey
                    this.buttons[i][j].addActionListener(this);
                    this.innerGames[i].add(buttons[i][j]);
            }
            add(this.innerGames[i]);
        }

        add(spacer);
        add(this.statusMessage);
        setSize(WIN_SIZE, WIN_SIZE+200); // Set window size
        setVisible(true); // Make the window visible
    }

    public void actionPerformed(ActionEvent e) {
        /*
            Listens for actions on the GUI, will be a button press
        */
        JButton button = (JButton) e.getSource(); // Get whatever button was clicked
        int gameToPlay;
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(button == this.buttons[i][j] && !gameDone){ // Find co-ords of button clicked for cross-referencing into board object
                    if(this.board.setPosition(i, j, this.turn.getValue())){ // Attempt to set position
                        this.status = this.board.isGameDone(i); // Grab the game status
                        this.smString = this.turn.getInverseValue() + "'s Turn"; // Set turn message
                        button.setText(this.turn.getValue().getValue()); // Set X or O on board
                        if(this.status == 1){ // If an inner game completed
                            for(int k = 0; k < 9; k++){ // Flip all buttons in the game to the win color and disable them
                                this.buttons[i][k].setBackground(winColor.getValue());
                                this.buttons[i][k].setEnabled(false);
                            }
                            this.smString = this.turn.getValue() + " wins a game!";
                        } else if(this.status == 2) { // If the board is completed
                            for(int k = 0; k < BOARD_SIZE; k++) {
                                for (int l = 0; l < BOARD_SIZE; l++) {
                                    this.buttons[k][l].setEnabled(false); // Disable all buttons
                                }
                                this.gameDone = true; // Flags the game to be done
                                this.smString = this.turn.getValue() + " wins the board!";
                            }
                            break; // Get out of the loop to not execute the standard position set path
                        } else { // Else if just a position was set
                            button.setBackground(this.turnColor.getValue());
                            button.setEnabled(false); // turn button off once pressed
                        }

                        gameToPlay = this.board.getGameToPlay();
                        // Set all active but unplayable buttons to grey, set playable active buttons to green
                        for(int k = 0; k < 9; k++){
                            for(int l = 0; l < 9; l++){
                                if(gameToPlay == -1){
                                    if(this.buttons[k][l].isEnabled()) {this.buttons[k][l].setBackground(Color.green);}
                                } else {
                                    if (this.buttons[k][l].isEnabled()) {this.buttons[k][l].setBackground(Color.lightGray);} // Reset a button to grey if its active (not played)
                                }
                            }
                        }

                        for(int k = 0; k < 9; k++){
                            if (gameToPlay > -1 && this.buttons[gameToPlay][k].isEnabled()) {this.buttons[gameToPlay][k].setBackground(Color.green);}
                        }
                        this.turn.toggle(); // Flip turn
                        this.turnColor.toggle(); // Flip color
                        this.winColor.toggle(); // Flip win color
                        this.statusMessage.setText(smString); // Set turn message
                    } else { // If invalid
                        this.statusMessage.setText("Invalid move, try again");
                    }
                    break; // don't need to look at other buttons if already found
                }
            }
        }
    }
}

class ToggleSwitch<T> {
    /*
        Custom implementation to handle simple turn/color toggling
    */
    private T firstValue;
    private T secondValue;
    private boolean isSwitched;

    public ToggleSwitch(T firstValue, T secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.isSwitched = false; // By default, the switch is set to the first value
    }

    public void toggle() {
        isSwitched = !isSwitched;
    }

    public T getInverseValue(){
        return isSwitched ? firstValue : secondValue;
    }

    public T getValue() {
        return isSwitched ? secondValue : firstValue;
    }
}
