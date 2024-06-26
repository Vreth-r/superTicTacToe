import java.util.Objects;

public class Board {
    Marker[][] board = new Marker[9][10];
    int gameToPlay;
    int lastPosPlayed;
    int[] winningGames = new int[3];

    public enum Marker{
        B("_"), X("X"), O("O"), T("Tie"), N("Neither");

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
                board[i][j] = Marker.B;
            }
            board[i][9] = Marker.N;
        }

        gameToPlay = -1; // Keeps track of the last inner game, -1 means any game can be played in
        lastPosPlayed = -1; // Keeps track of the last pos played for when inner games are won
    }

    public int getGameToPlay(){
        return gameToPlay;
    }

    public boolean isPositionPlayable(int posOuter, int posInner) {
        /*
            Determines if the position is playable according to ST3 rules
                The starting player may start in any game they wish,
                every turn afterward must play in the outer game corresponding to the space played in the inner game
                (ex: if they started in the middle game's top right space, the other player can play in any space in the top right game)


        */
        boolean isSpaceEmpty = Objects.equals(board[posOuter][posInner], Marker.B) && Objects.equals(board[posOuter][9], Marker.N);
        boolean validGame = gameToPlay == -1 || gameToPlay == posOuter; // is it the first turn or is this game the game to play
        return isSpaceEmpty && validGame;

    }

    public boolean setPosition(int posOuter, int posInner, Marker marker) {
        /*
           Sets any place on the board,
                returns true if placement is valid and successful
                returns false if placement is invalid and did not occur
           posOuter: Outer/big board game, 0-8
           posInner: Inner/small board game, 0-8
           marker: Marker either X or O
        */

        // Only run if inserting into empty space in non won board
        if (!isPositionPlayable(posOuter, posInner)) {return false;}
        board[posOuter][posInner] = marker; // Edit board
        if(!Objects.equals(board[posInner][9], Marker.N)){ // If game to be played is completed
            gameToPlay = -1;
        } else {
            gameToPlay = posInner; // Set the game to play
        }
        lastPosPlayed = posInner;
        return true;
    }

    public int isGameDone(int posOuter){
        /*
            Checks an inner game for completion,
                if complete, check if the entire board is complete
                return 0 if the inner game didnt complete
                return 1 if the inner game is won
                return 2 if the outer board is won
                return 3 if the inner game is tied
                return 4 if the outer boars is tied
            posOuter: Outer/big board game, 0-8
        */
        int returnCode = 0;
        Marker result = checkWinner(board[posOuter], Marker.B); // Checks for inner game win
        if(!Objects.equals(result, Marker.N)) { // If the inner game is won
            if(Objects.equals(result, Marker.T)) {
                returnCode = 3;
            } else {
                returnCode = 1;
            }
            if(lastPosPlayed == posOuter){
                gameToPlay = -1;
            }
            board[posOuter][9] = result; // Dump the winners marker into the status index

            Marker[] outerGamesResults = new Marker[9]; // Check the outer game status

            for(int i = 0; i <= 8; i++){ // Grab all results of games
                outerGamesResults[i] = board[i][9];
            }
            Marker outerResult = checkWinner(outerGamesResults, Marker.N);
            if(Objects.equals(outerResult, Marker.T)) {
                returnCode = 4; // return if tie
            } else if(!Objects.equals(outerResult, Marker.N)) {
                returnCode = 2; // return if outer game is won

            }
        }
        return returnCode;
    }

    public Marker checkWinner(Marker[] board, Marker tieMarker) {
        /*
            Checks the rows, columns, and diagonals for the winning marker, or if there is a tie
                The private helper methods will return the winning marker if it exists, and the neither marker otherwise
                This allows this method to check the inner games and the outer game
            board: a Marker list, will either be an inner game or a list containing all the status of the inner games
         */
        Marker winner = checkRows(board);
        if (!Objects.equals(winner, Marker.N) && !Objects.equals(winner, Marker.T)) {
            return winner;
        }

        winner = checkColumns(board);
        if (!Objects.equals(winner, Marker.N) && !Objects.equals(winner, Marker.T)) {
            return winner;
        }

        winner = checkDiagonals(board);
        if (!Objects.equals(winner, Marker.N) && !Objects.equals(winner, Marker.T)) {
            return winner;
        }

        // Check for tie
        if (isTie(board, tieMarker)) {
            return Marker.T; // Tie
        }

        return Marker.N; // No winner yet
    }

    private Marker checkRows(Marker[] board) {
        for (int i = 0; i < 3; i++) {
            int startIndex = i * 3;
            if (!Objects.equals(board[startIndex], Marker.B) && Objects.equals(board[startIndex], board[startIndex + 1]) && Objects.equals(board[startIndex], board[startIndex + 2])) {
                winningGames[0] = startIndex; winningGames[1] = startIndex + 1; winningGames[2] = startIndex + 2;
                return board[startIndex]; // Return the winning player marker
            }
        }
        return Marker.N; // No winner in rows
    }

    private Marker checkColumns(Marker[] board) {
        for (int i = 0; i < 3; i++) {
            if (!Objects.equals(board[i], Marker.B) && Objects.equals(board[i], board[i + 3]) && Objects.equals(board[i], board[i + 6])) {
                winningGames[0] = i; winningGames[1] = i + 3; winningGames[2] = i + 6;
                return board[i]; // Return the winning player marker
            }
        }
        return Marker.N; // No winner in columns
    }

    private Marker checkDiagonals(Marker[] board) {
        if (!Objects.equals(board[0], Marker.B) && Objects.equals(board[0], board[4]) && Objects.equals(board[0], board[8])) {
            winningGames[0] = 0; winningGames[1] = 4; winningGames[2] = 8;
            return board[0]; // Return the winning player marker
        }
        if (!Objects.equals(board[2], Marker.B) && Objects.equals(board[2], board[4]) && Objects.equals(board[2], board[6])) {
            winningGames[0] = 2; winningGames[1] = 4; winningGames[2] = 6;
            return board[2]; // Return the winning player marker
        }
        return Marker.N; // No winner in diagonals
    }

    private boolean isTie(Marker[] board, Marker tieMarker) {
        // tieMarker is for checking the board win.
        // Inner games check for the Blank marker, the board win generated array is populated instead with Neither markers
        for (Marker cell : board) {
            if (Objects.equals(cell, tieMarker)) {
                return false; // There is an empty cell, game not tied yet
            }
        }
        return true; // All cells filled, game tied
    }
}
