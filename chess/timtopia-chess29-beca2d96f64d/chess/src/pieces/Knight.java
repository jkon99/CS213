package pieces;

/**
 *  A knight piece in the game of chess
 * 
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class Knight extends Piece {
	/**
	 * Class constructor specifying the color of the knight
	 * 
	 * @param white	the color of the knight. <code>true</code> if the knight is white; <code>false</code> if the knight is black.
	 */
	public Knight(boolean white) {
		super(white);
	}

	/**
	 *	Prints the color and knight representation
	 */
	public String toString() {
		if(this.getColor())
			return "wN";
		else
			return "bN";
	}
}
