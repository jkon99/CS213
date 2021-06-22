package pieces;

/**
 *  A king piece in the game of chess
 * 
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 */
public class King extends Piece {
	
	/**
	 * Specifies if the king has moved.
	 * 
	 * <code>true</code> if the king has moved; <code>false</code> if the king has not.
	 */
	private boolean moved;
	
	/**
	 * Class constructor specifying the color of the king
	 * 
	 * @param white	the color of the king. <code>true</code> if the king is white; <code>false</code> if the king is black.
	 */
	public King(boolean white) {
		super(white);
	}
	
	/**
	 *	Prints the color and king representation
	 */
	public String toString() {
		if(this.getColor())
			return "wK";
		else
			return "bK";
	}
	
	/**
	 * Returns if the king has moved
	 * 
	 * @return <code>true</code> if the piece has moved; <code>false</code> otherwise.
	 */
	public boolean getMoved() {
		return moved;
	}
	
	/**
	 * Replaces the moved parameter with the specified parameter
	 * 
	 * @param move <code>true</code> if the piece has moved; <code>false</code> otherwise.
	 */
	public void setMoved(boolean move) {
		moved = move;
	}
}
