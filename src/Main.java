import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.displayBoard();
        board.setPosition(0, 0, Board.Marker.X);
        board.setPosition(0, 2, Board.Marker.O);
        board.setPosition(0, 4, Board.Marker.X);
        board.setPosition(0, 3, Board.Marker.X);
        board.setPosition(0, 8, Board.Marker.X);

        board.setPosition(5, 0, Board.Marker.O);
        board.setPosition(5, 4, Board.Marker.O);
        board.setPosition(5, 8, Board.Marker.O);

        board.displayBoard();
    }

}