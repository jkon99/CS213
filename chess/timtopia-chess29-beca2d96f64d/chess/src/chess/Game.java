package chess;

/**
 * The app to start the game
 * 
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class Game {
	/**
	 * Our main method. Makes the board.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		Board board = new Board();
		System.out.println(board.toString());
		board.prompt();
	}
}
