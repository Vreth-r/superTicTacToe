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
    private int status; // Used for board status checking

    public ST3GUI() {
        setTitle("Super Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 3, 4, 4));

        innerGames = new JPanel[BOARD_SIZE];
        smString = "SUPER TIC TAC TOE";
        statusMessage = new JLabel(smString);
        statusMessage.setHorizontalAlignment(JLabel.CENTER);
        JLabel spacer = new JLabel("Press X to exit"); // Label for formatting, otherwise the status message would be on the left side
        spacer.setHorizontalAlignment(JLabel.CENTER);
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        board = new Board();
        turn = new ToggleSwitch<Board.Marker>(Board.Marker.X, Board.Marker.O);
        turnColor = new ToggleSwitch<Color>(Color.red, Color.blue);
        winColor = new ToggleSwitch<Color>(Color.pink, Color.cyan);

        // Initialize the main board
        for (int i = 0; i < BOARD_SIZE; i++) {
            innerGames[i] = new JPanel(new GridLayout(3, 3));
            for (int j = 0; j < BOARD_SIZE; j++) {
                    buttons[i][j] = new JButton(Board.Marker.B.getValue()); // Set all to blanks
                    buttons[i][j].setBackground(Color.green); // Set all to light grey
                    buttons[i][j].addActionListener(this);
                    innerGames[i].add(buttons[i][j]);
            }
            add(innerGames[i]);
        }

        add(spacer);
        add(statusMessage);
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
                if(button == buttons[i][j]){ // Find co-ords of button clicked for cross-referencing into board object
                    if(board.setPosition(i, j, turn.getValue())){ // Attempt to set position
                        status = board.isGameDone(i); // Grab the game status
                        smString = turn.getInverseValue() + "'s Turn"; // Set turn message
                        button.setText(turn.getValue().getValue()); // Set X or O on board

                        if(status == 1){ // If an inner game completed
                            winGameVisuals(i, winColor.getValue());
                            smString = turn.getValue() + " wins a game!";

                        } else if(status == 2) { // If the board is completed
                            winGameVisuals(i, winColor.getValue());
                            // For each game that isn't a game that makes the winning pattern, gray it out
                            for(int k = 0; k < 9; k++){
                                if(k != board.winningGames[0] && k != board.winningGames[1] && k != board.winningGames[2]){
                                    for(int l = 0; l < 9; l++){
                                        buttons[k][l].setEnabled(false);
                                        buttons[k][l].setBackground(Color.gray);
                                    }
                                }
                            }
                            statusMessage.setText(turn.getValue() + " wins the board!");
                            return; // Get out of the loop to not execute the standard position set path

                        } else if(status == 3){
                            winGameVisuals(i, Color.GRAY);
                            smString = "Tie game!";

                        } else if(status == 4){
                            for(int k = 0; k < BOARD_SIZE; k++) {
                                for (int l = 0; l < BOARD_SIZE; l++) {
                                    buttons[k][l].setEnabled(false); // Disable all buttons
                                    buttons[k][l].setBackground(Color.GRAY);
                                }
                            }
                            statusMessage.setText("Tie Board!");
                            return;
                        } else { // Else if just a position was set
                            button.setBackground(turnColor.getValue());
                            button.setEnabled(false); // turn button off once pressed
                        }

                        gameToPlay = board.getGameToPlay();
                        // Set all active but unplayable buttons to grey, set playable active buttons to green
                        setNonPlayable(true);
                        setPlayable(gameToPlay);
                        toggleSwitches();
                        statusMessage.setText(smString); // Set turn message
                    } else { // If invalid
                        statusMessage.setText("Invalid move, try again");
                    }
                    return; // don't need to look at other buttons if already found
                }
            }
        }
    }

    private void setPlayable(int outerPos){
        // Scans the given game for active buttons and sets them to green
        if(outerPos == -1){
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++) {
                    if (buttons[i][j].isEnabled()) {
                        buttons[i][j].setBackground(Color.green);
                    }
                }
            }
        } else {
            for (int i = 0; i < 9; i++) {
                if (buttons[outerPos][i].isEnabled()) {
                    buttons[outerPos][i].setBackground(Color.green);
                }
            }
        }
    }

    private void setNonPlayable(boolean enableStatus){
        // Scans the whole board for active buttons and greys them out
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                if (buttons[i][j].isEnabled()) {
                    buttons[i][j].setBackground(Color.lightGray);
                    buttons[i][j].setEnabled(enableStatus);
                }
            }
        }
    }

    private void winGameVisuals(int innerPos, Color color){
        // Sets a whole inner game to a color
        for(int i = 0; i < 9; i++){
            buttons[innerPos][i].setBackground(color);
            buttons[innerPos][i].setEnabled(false);
        }
    }

    private void toggleSwitches(){
        turn.toggle(); // Flip turn
        turnColor.toggle(); // Flip color
        winColor.toggle(); // Flip win color
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
        isSwitched = false; // By default, the switch is set to the first value
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
