import java.util.Objects;

public class Board {
    String[][] board = new String[9][10];

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
                this.board[i][j] = "_";
            }
            this.board[i][9] = "F";
        }
    }

    void displayBoard() {
        // Print out the board in readable text format
        System.out.println("\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        for (int i = 0; i <= 8; i += 3) { // Loop games in groups of 3 (outer list)
            for (int j = 0; j <= 8; j += 3) {
                System.out.println(
                        this.board[i][j] + " " + this.board[i][j + 1] + " " + this.board[i][j + 2] + " █ " +
                                this.board[i + 1][j] + " " + this.board[i + 1][j + 1] + " " + this.board[i + 1][j + 2] + " █ " +
                                this.board[i + 2][j] + " " + this.board[i + 2][j + 1] + " " + this.board[i + 2][j + 2]);
            }
            System.out.println("▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        }
    }

    void setPosition(int posOuter, int posInner, String marker) {
        /*
           Sets any place on the board
           posOuter: Outer/big board game, 0-8
           posInner: Inner/small board game, 0-8
           marker: X or O string
        */
        // Error check insertion string

        // Only run if inserting into empty space in non won board
        if (Objects.equals(this.board[posOuter][posInner], "_") && Objects.equals(this.board[posOuter][9], "F")) {
            this.board[posOuter][posInner] = marker;
            // Check for game win here, check by using the position of the last move from this context and see if there are lines present in its row, column, or diag
            if(isDone(posOuter)) {
                this.board[posOuter][9] = "T";
            }
        }
    }
    private Boolean isDone(int posOuter){
        /*
           Helper method that checks a T3 game to be done, runs after every move
           posOuter: index of inner game on the outer game
        */

    }
}
