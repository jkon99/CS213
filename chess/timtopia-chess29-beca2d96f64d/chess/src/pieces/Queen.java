package pieces;

/**
 *A queen piece in the game of chess
 * 
 * 
 *@author Timothy Zhang
 *@author Jonathan Konopka
 *
 */
public class Queen extends Piece {
	
	/**
	 * Class constructor specifying the color of the queen
	 * 
	 * @param white	the color of the queen. <code>true</code> if the queen is white; <code>false</code> if the queen is black.
	 */
	public Queen(boolean white) {
		super(white);
	}

	/**
	 *	Prints the color and queen representation
	 */
	public String toString() {
		if(this.getColor())
			return "wQ";
		else
			return "bQ";
	}
}
