package pieces;

/**
 *  A rook piece in the game of chess
 * 
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class Rook extends Piece {
	/**
	 * Specifies if the rook has moved.
	 * 
	 * <code>true</code> if the rook has moved; <code>false</code> if the rook has not.
	 */
	private boolean moved;
	
	/**
	 * Class constructor specifying the color of the rook
	 * 
	 * @param white	the color of the rook. <code>true</code> if the rook is white; <code>false</code> if the rook is black.
	 */
	public Rook(boolean white) {
		super(white);
	}
	
	/**
	 *	Prints the color and rook representation
	 */
	public String toString() {
		if(this.getColor())
			return "wR";
		else
			return "bR";
	}
	
	/**
	 * Returns if the rook has moved
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
