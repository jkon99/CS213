package chess;

import pieces.Piece;

/**
 * A single spot of the board
 * 
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class Spot {
	/**
	 *  Color of the spot. <code>true</code> if the spot is white; <code>false</code> if the piece is black
	 */
	private boolean white;
	/**
	 * 	The piece in the spot
	 * 
	 * <code>null</code> if empty
	 */
	private Piece piece;

	/**
	 * Class constructor specifying the color of the spot
	 * 
	 * @param white	the color of the spot. <code>true</code> if the spot is white; <code>false</code> if the spot is black.
	 */
	public Spot(boolean white) {
		this.white = white;
	}

	/**
	 * Returns the color of the spot
	 * 
	 * @return <code>true</code> if the spot is white; <code>false</code> if the spot is black.
	 */
	public boolean getColor() {
		return white;
	}

	/**
	 * Replaces the piece parameter with the specified piece
	 * 
	 * @param piece the piece to be moved into the spot
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	/**
	 * Returns the piece contained in the spot
	 * 
	 * @return piece the piece currently contained
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * Returns whether or not the spot contains a piece
	 * 
	 * @return <code>true</code> if the spot has no piece; <code>false</code> if the spot has a piece.
	 */
	public boolean isEmpty() {
		if (piece == null)
			return true;
		return false;
	}

	/**
	 * Prints the spot representation
	 */
	public String toString() {
		if (this.piece != null)
			return piece.toString();
		if (white)
			return "  "; // white space on board
		else
			return "##"; // black space on board
	}
}
