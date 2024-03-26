import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.displayBoard();
        board.setPosition(0, 1, "X");
        board.displayBoard();
    }

}