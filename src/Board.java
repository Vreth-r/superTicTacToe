import java.util.Objects;
import java.util.Objects.*;

public class Board {
    Marker[][] board = new Marker[9][10];

    public enum Marker{
        B("_"), X("X"), O("O"), XW("X Win"), OW("O Win"), T("Tie"), N("Neither");

        Marker(String v){
            value = v;
        }
        private String value;

        public String getValue(){
            return value;
        }
    }

    public Board() {
        /* {index in inner ArrayList (index outer ArrayList)}
            0 (0) 1 (0) 2 (0) █ 0 (1) 1 (1) 2 (1) █ 0 (2) 1 (2) 2 (2)
            3 (0) 4 (0) 5 (0) █ 3 (1) 4 (1) 5 (1) █ 3 (2) 4 (2) 5 (2)
            6 (0) 7 (0) 8 (0) █ 6 (1) 7 (1) 8 (1) █ 6 (2) 7 (2) 8 (2)
            ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
            0 (3) 1 (3) 2 (3) █ 0 (4) 1 (4) 2 (4) █ 0 (5) 1 (5) 2 (5)
            3 (3) 4 (3) 5 (3) █ 3 (4) 4 (4) 5 (4) █ 3 (5) 4 (5) 5 (5)
            6 (3) 7 (3) 8 (3) █ 6 (4) 7 (4) 8 (4) █ 6 (5) 7 (5) 8 (5)
            ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
            0 (6) 1 (6) 2 (6) █ 0 (7) 1 (7) 2 (7) █ 0 (8) 1 (8) 2 (8)
            3 (6) 4 (6) 5 (6) █ 3 (7) 4 (7) 5 (7) █ 3 (8) 4 (8) 5 (8)
            6 (6) 7 (6) 8 (6) █ 6 (7) 7 (7) 8 (7) █ 6 (8) 7 (8) 8 (8)

            The outer list is a list of the inner games, indexed like above
            The inner lists are also indexed like above, with an additional index (0/1) that states if the game is won or not
         */
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 8; j++) {
                this.board[i][j] = Marker.B;
            }
            this.board[i][9] = Marker.N;
        }
    }

    public void displayBoard() {
        // Print out the board in readable text format
        System.out.println("\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        for (int i = 0; i <= 8; i += 3) { // Loop games in groups of 3 (outer list)
            for (int j = 0; j <= 8; j += 3) {
                System.out.println(
                        this.board[i][j].getValue() + " " + this.board[i][j + 1].getValue() + " " + this.board[i][j + 2].getValue() + " █ " +
                                this.board[i + 1][j].getValue() + " " + this.board[i + 1][j + 1].getValue() + " " + this.board[i + 1][j + 2].getValue() + " █ " +
                                this.board[i + 2][j].getValue() + " " + this.board[i + 2][j + 1].getValue() + " " + this.board[i + 2][j + 2].getValue());
            }
            System.out.println("▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        }
    }

    public void setPosition(int posOuter, int posInner, Marker marker) {
        /*
           Sets any place on the board
           posOuter: Outer/big board game, 0-8
           posInner: Inner/small board game, 0-8
           marker: Marker either X or O
        */

        // Only run if inserting into empty space in non won board
        if (Objects.equals(this.board[posOuter][posInner], Marker.B) && Objects.equals(this.board[posOuter][9], Marker.N)) {
            this.board[posOuter][posInner] = marker;
            // Check for game win here, check by using the position of the last move from this context and see if there are lines present in its row, column, or diag
            Marker result = checkWinner(posOuter);

            // Check for winner and declare status
            if(Objects.equals(result, Marker.X)){this.board[posOuter][9] = Marker.XW; System.out.println("X Wins");}
            else if(Objects.equals(result, Marker.O)){this.board[posOuter][9] = Marker.OW; System.out.println("O Wins");}
            else if(Objects.equals(result, Marker.T)){this.board[posOuter][9] = Marker.T; System.out.println("Tie");}
        }
    }

    public Marker checkWinner(int posOuter) {
        // Check rows, columns, and diagonals for completion
        Marker winner = checkRows(this.board[posOuter]);
        if (!Objects.equals(winner, Marker.N)) {
            return winner;
        }

        winner = checkColumns(this.board[posOuter]);
        if (!Objects.equals(winner, Marker.N)) {
            return winner;
        }

        winner = checkDiagonals(this.board[posOuter]);
        if (!Objects.equals(winner, Marker.N)) {
            return winner;
        }

        // Check for tie
        if (isTie(this.board[posOuter])) {
            return Marker.T; // Tie
        }

        return Marker.N; // No winner yet
    }

    private Marker checkRows(Marker[] board) {
        for (int i = 0; i < 3; i++) {
            int startIndex = i * 3;
            if (!Objects.equals(board[startIndex], Marker.B) && Objects.equals(board[startIndex], board[startIndex + 1]) && Objects.equals(board[startIndex], board[startIndex + 2])) {
                return board[startIndex]; // Return the winning player number
            }
        }
        return Marker.N; // No winner in rows
    }

    private Marker checkColumns(Marker[] board) {
        for (int i = 0; i < 3; i++) {
            if (!Objects.equals(board[i], Marker.B) && Objects.equals(board[i], board[i + 3]) && Objects.equals(board[i], board[i + 6])) {
                return board[i]; // Return the winning player number
            }
        }
        return Marker.N; // No winner in columns
    }

    private Marker checkDiagonals(Marker[] board) {
        if (!Objects.equals(board[0], Marker.B) && Objects.equals(board[0], board[4]) && Objects.equals(board[0], board[8])) {
            return board[0]; // Return the winning player number
        }
        if (!Objects.equals(board[2], Marker.B) && Objects.equals(board[2], board[4]) && Objects.equals(board[2], board[6])) {
            return board[2]; // Return the winning player number
        }
        return Marker.N; // No winner in diagonals
    }

    private boolean isTie(Marker[] board) {
        for (Marker cell : board) {
            if (Objects.equals(cell, Marker.B)) {
                return false; // There is an empty cell, game not tied yet
            }
        }
        return true; // All cells filled, game tied
    }
}
