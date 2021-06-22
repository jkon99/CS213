package pieces;

/**
 *  A bishop piece in the game of chess
 * 
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 */
public class Bishop extends Piece {

	/**
	 * Class constructor specifying the color of the bishop
	 * 
	 * @param white the color of the bishop. <code>true</code> if the bishop is white; <code>false</code> if the bishop is black.
	 */
	public Bishop(boolean white) {
		super(white);
	}

	
	/**
	 *	Prints the color and bishop representation
	 */
	public String toString() {
		if(this.getColor())
			return "wB";
		else
			return "bB";
	}
}