import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class ST3GUI extends JFrame implements ActionListener {
    private final int BOARD_SIZE = 9;
    private final int WIN_SIZE = 600;
    private JPanel[] innerGames;
    private JButton[][] buttons;
    private JLabel statusMessage;
    private Board board;
    private Board.Marker turn;
    private Color turnColor;
    private int status;

    public ST3GUI() {
        setTitle("Super Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        this.innerGames = new JPanel[BOARD_SIZE];
        this.statusMessage = new JLabel("SUPER TIC TAC TOE!!!!!!!!");
        this.statusMessage.setHorizontalAlignment(SwingConstants.CENTER);
        this.buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        this.board = new Board();
        this.turn = Board.Marker.X;
        this.turnColor = Color.red;

        // Initialize the main board
        for (int i = 0; i < BOARD_SIZE; i++) {
            this.innerGames[i] = new JPanel(new GridLayout(3, 3));
            for (int j = 0; j < BOARD_SIZE; j++) {
                    this.buttons[i][j] = new JButton(Board.Marker.B.getValue()); // Set all to blanks
                    this.buttons[i][j].setBackground(Color.lightGray); // Set all to light grey
                    this.buttons[i][j].addActionListener(this);
                    this.innerGames[i].add(buttons[i][j]);
            }
            add(this.innerGames[i]);
        }

        setSize(WIN_SIZE, WIN_SIZE+200); // Set window size
        setVisible(true); // Make the window visible
    }

    public void actionPerformed(ActionEvent e) {
        /*
            Listens for actions on the GUI, will be a button press
         */
        JButton button = (JButton) e.getSource(); // Get whatever button was clicked
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(button == buttons[i][j]){ // Find co-ords of button clicked for cross-referencing into board object
                    if(this.board.setPosition(i, j, this.turn)){ // Attempt to set position
                        this.status = this.board.isGameDone(i);
                        switch(this.status){ // Cases for each outcome of isGameDone (see its docs)
                            case 2: // If Board complete

                                break;
                            case 1: // If inner game complete

                                break;
                            case 0: // If nothing completed
                                button.setText(this.turn.getValue()); // Set visuals
                                button.setBackground(this.turnColor);
                                button.setEnabled(false); // turn button off once pressed

                                if(Objects.equals(turn, Board.Marker.X)){ // Flip turn/color
                                    this.turn = Board.Marker.O;
                                    this.turnColor = Color.blue;
                                }else{
                                    this.turn = Board.Marker.X;
                                    this.turnColor = Color.red;
                                }
                                this.statusMessage.setText(this.turn.getValue() + "'s Turn"); // Set turn message
                                break;
                        }
                    } else { // If invalid
                        this.statusMessage.setText("Invalid move, try again");
                    }
                }
            }
        }
    }
}
