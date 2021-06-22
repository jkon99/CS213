package pieces;

/**
 *  A pawn piece in the game of chess
 * 
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class Pawn extends Piece {
	
	/**
	 * Specifies if the pawn has enpassiant moved.
	 * 
	 * <code>true</code> if the pawn has moved 2 last turn; <code>false</code> if the pawn has not.
	 */
	private boolean moved;
	
	/**
	 * Class constructor specifying the color of the pawn
	 * 
	 * @param white	the color of the pawn. <code>true</code> if the pawn is white; <code>false</code> if the pawn is black.
	 */
	public Pawn(boolean white) {
		super(white);
	}
	
	/**
	 *	Prints the color and pawn representation
	 */
	public String toString() {
		if(this.getColor())
			return "wp";
		else
			return "bp";
	}

	/**
	 * Returns if the pawn has moved 2 spaces
	 * 
	 * @return <code>true</code> if the piece has moved 2 spaces; <code>false</code> otherwise.
	 */
	public boolean getMoved() {
		return moved;
	}
	
	/**
	 * Replaces the moved parameter with the specified parameter
	 * 
	 * @param move <code>true</code> if the piece has moved 2 spaces; <code>false</code> otherwise.
	 */
	public void setMoved(boolean move) {
		moved = move;
	}

}
