package pieces;

/**
 * Abstract class that contains the basic structure of a chess piece
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 */
public abstract class Piece {
	/**
	 *  Color of the spot. <code>true</code> if the piece is white; <code>false</code> if the piece is black
	 */
	private boolean white;
	
	/**
	 * Class constructor specifying the color of the Piece
	 * 
	 * @param white the color of the piece. <code>true</code> if the piece is white; <code>false</code> if the piece is black
	 */
	public Piece(boolean white) {
		this.white = white;
	}
	
	/**
	 * Returns the color of the piece
	 * 
	 * @return <code>true</code> if the piece is white; <code>false</code> if the piece is black.
	 */
	public boolean getColor() {
		return white;
	}
}
