package chess;

import pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* IMPORTANT NOTES
NEED JAVADOCS implementation for fields, methods, etc. Include names with @author tags
*/

/**
 * The Board of the game that handles all of the Logic
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class Board {
	
	/**
	 * Represents whether a draw has been offered
	 */
	private boolean draw;

	/**
	 * Contains the board
	 */
	private Spot[][] board;
	/**
	 * Holds all possible moves for the side
	 */
	private List<String> possibleMoves;

	/**
	 * Generates a new board instance
	 */
	public Board() {

		possibleMoves = new ArrayList<String>();
		board = new Spot[8][8];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if ((i + j) % 2 == 0)
					board[i][j] = new Spot(false);
				else
					board[i][j] = new Spot(true);
			}
		}
		// board pieces are initialized to false , set along the board
		// The figure immediately below should make it clear which rank and file
		// combinations belong to which squares.
		// The white pieces always intially occupy ranks 1 and 2. The black pieces
		// always initially occupy ranks 7 and 8. The queen always starts on the d file.

		/*
		board[0][7].setPiece(new Rook(true)); 
		board[7][5].setPiece(new Rook(false));
		board[0][4].setPiece(new King(true));
		 */
		board[0][0].setPiece(new Rook(true));
		board[0][1].setPiece(new Knight(true));
		board[0][2].setPiece(new Bishop(true));
		board[0][3].setPiece(new Queen(true));
		board[0][4].setPiece(new King(true));
		board[0][5].setPiece(new Bishop(true));
		board[0][6].setPiece(new Knight(true));
		board[0][7].setPiece(new Rook(true));

		for (int i = 0; i < board.length; i++) {
			board[1][i].setPiece(new Pawn(true));
			board[6][i].setPiece(new Pawn(false));
		}

		board[7][0].setPiece(new Rook(false));
		board[7][1].setPiece(new Knight(false));
		board[7][2].setPiece(new Bishop(false));
		board[7][3].setPiece(new Queen(false));
		board[7][4].setPiece(new King(false));
		board[7][5].setPiece(new Bishop(false));
		board[7][6].setPiece(new Knight(false));
		board[7][7].setPiece(new Rook(false)); 

	}
	/**
	 * Generates a new board instance with the specified array copied.
	 * 
	 * @param temp the array to be copied
	 */
	private Board(Spot[][] temp) {
		board = new Spot[8][8];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if ((i + j) % 2 == 0)
					board[i][j] = new Spot(false);
				else
					board[i][j] = new Spot(true);
			}
		}

		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp.length; j++) {
				if (temp[i][j].getPiece() instanceof Pawn)
					board[i][j].setPiece(new Pawn(temp[i][j].getPiece().getColor()));
				if (temp[i][j].getPiece() instanceof Knight)
					board[i][j].setPiece(new Knight(temp[i][j].getPiece().getColor()));
				if (temp[i][j].getPiece() instanceof Bishop)
					board[i][j].setPiece(new Bishop(temp[i][j].getPiece().getColor()));
				if (temp[i][j].getPiece() instanceof Queen)
					board[i][j].setPiece(new Queen(temp[i][j].getPiece().getColor()));
				if (temp[i][j].getPiece() instanceof Rook)
					board[i][j].setPiece(new Rook(temp[i][j].getPiece().getColor()));
				if (temp[i][j].getPiece() instanceof King)
					board[i][j].setPiece(new King(temp[i][j].getPiece().getColor()));

			}
		}
	}

	/**
	 * Prints the chess board out with piece representation
	 */
	public String toString() { // prints out strings to represent position on board
		String result = "";
		for (int i = board.length - 1; i >= 0; i--) {
			for (int j = 0; j < board[0].length; j++) {
				result = result + board[i][j].toString() + " ";
			}
			result = result + Integer.toString(i + 1) + "\n";
		}
		result = result + " a  b  c  d  e  f  g  h" + "\n";
		return result;
	}

	/**
	 * Starts the game with the board given
	 */
	public void prompt() {
		// white moves first
		// resign move by typing "resign" no quotes
		// over a draw by appending "draw?" to a normal move. other player must accept. can only draw if offered by other player
		// implement castling and promotion
		// type your movement as "piece coordinate" to "piece destination", ie "e2 e4"
		int gameOver = 0;
		String input;
		Scanner in = new Scanner(System.in);
		int subTurn = 0;
		int Pcheck;
		while (gameOver == 0) {

			// generation of possibleMoves
			possibleMoves = generateMoves(subTurn % 2);
			/*for(String s:possibleMoves) {
				System.out.println(s);
			} */
			
			boolean stalemate = true;
			for(String s:possibleMoves) {
				if(checkPiece(s, (subTurn) % 2) == 0) {
					stalemate = false;
					break;
				}
			} 
			
			List<String> check = generateMoves((subTurn+1)%2);
			boolean color = (subTurn % 2) == 0 ? true : false;
			String kingposition = "";
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					if (board[i][j].isEmpty() == false) {
						if (board[i][j].getPiece() instanceof King
								&& board[i][j].getPiece().getColor() == color) {
							kingposition = convertToChessNotation(i, j);
						}
					}
				}
			}
			boolean checkb = false;
			
			for(String s:check) {
				if(s.substring(3).equals(kingposition))
					checkb = true;
			}
			
			if(checkb&&stalemate) {
				System.out.println("Checkmate");
				if(subTurn % 2 == 0)
					System.out.println("Black wins");
				else
					System.out.println("White wins");
				break;
			}
			
			if(stalemate) {
				System.out.println("Stalemate");
				break;
			}
			
			if(checkb) {
				System.out.println("check");
			}
			

			// restrictions on space they can access as well and check the type of piece for
			// movememnt options
			// if illegal movie is made, print "illegal move, try again" followed by same
			// prompt, dont skip turn
			
			if (subTurn % 2 == 0) { // white's move if even turn
				Pcheck = 0;
				while (Pcheck == 0) {
					
					
					input = in.nextLine(); // accept appropriate input format, do checks
				
					if (input.equals("resign")) {  //first check if player resigns 
						System.out.println("Black wins");
						gameOver = 1;
						break;
					}
					
					if (input.equals("draw")&& draw) {  //if other player offered a draw
						//System.out.println("");
						gameOver = 1;
						break;
					}
					
					if(draw) {
						draw = false;
					}

					if (checkPiece(input, subTurn % 2) == 1) {
						System.out.println("Illegal move, try again");
					} else if (checkPiece(input, subTurn % 2) == 0) {
						System.out.println("White's move: " + input + "\n");
						

						String startposition = convertFromChessNotation(input.substring(0, 2));
						String endposition = convertFromChessNotation(input.substring(3, 5));
						
						String promotePiece = "";   
						if (input.length() > 6) {
							promotePiece = input.substring(6);
						}
						
						int startNum = Integer.parseInt(startposition.substring(0, 1));
						int startLetter = Integer.parseInt(startposition.substring(1));
						int endNum = Integer.parseInt(endposition.substring(0, 1));
						int endLetter = Integer.parseInt(endposition.substring(1));
						
						
						// DO PROMOTION IN THIS AREA
						//if at end of board for promotion
						//swap piece with choice of bishop, knight, rook, or queen of same color
						if (endNum == 8) { //if white piece in row 8
							board[startNum][startLetter].setPiece(null);
							switch(promotePiece) {
							//set old pawn piece to null?
							
							case "R":
								board[endNum][endLetter].setPiece(new Rook(true));
								break;
							case "N":
								board[endNum][endLetter].setPiece(new Knight(true));
								break;
							case "B":
								board[endNum][endLetter].setPiece(new Bishop(true));
								break;
							default:
								board[endNum][endLetter].setPiece(new Queen(true));
								break;
							}
							System.out.println(this.toString());
							subTurn++;
							Pcheck = 1;
							continue;
							
						}
						
						
						if(board[Integer.parseInt(startposition.substring(0, 1))][Integer.parseInt(startposition.substring(1))].getPiece() instanceof Pawn)
							if(board[Integer.parseInt(endposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))].isEmpty())
								if(Math.abs(Integer.parseInt(endposition.substring(1))-Integer.parseInt(startposition.substring(1)))==1)
									board[Integer.parseInt(startposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))].setPiece(null);
						
						// DO CASTLING IN THIS AREA
						//A castling move is indicated by specifying where the king begins and ends. So, white castling king's side would be "e1 g1". 
						//do castling on both black and white ends for queen and king sides
						
						if (board[startNum][startLetter].getPiece() instanceof King) {
						if (((King)board[startNum][startLetter].getPiece()).getMoved()!=true) {
							if ((endLetter == startLetter-2 && startNum==endNum) || (endLetter == startLetter + 2 && startNum==endNum)) {
								if (checkb) {
									System.out.println("Illegal move, try again");
									continue;
								}
								//white queen side
								// move Rook to d1, king to c1
								if (endLetter == startLetter-2) {
									//if check for empty pieces in between
									if (checkPiece(convertToChessNotation(startNum, startLetter) + " " + convertToChessNotation(startNum, startLetter -1), subTurn % 2) == 1) {
										System.out.println("Illegal move, try again");
										continue;
									}
									if(board[startNum][startLetter-4].getPiece() instanceof Rook) {
										if((((Rook)board[startNum][startLetter-4].getPiece()).getMoved()!=true) && (((King)board[startNum][startLetter].getPiece()).getMoved()!=true)){
											
											
												if (board[startNum][startLetter-3].isEmpty() && board[startNum][startLetter-2].isEmpty() && board[startNum][startLetter-1].isEmpty()) {
													//if check for king being in check or into check or through check
								
													board[startNum][startLetter-4].setPiece(null);
													board[startNum][startLetter-1].setPiece(new Rook(true));
													
													board[startNum][startLetter].setPiece(null);
													board[startNum][startLetter-2].setPiece(new King(true));
													
													((Rook) board[startNum][startLetter-1].getPiece()).setMoved(true);
													((King) board[endNum][endLetter].getPiece()).setMoved(true);
													//tesdt
												
												}
											} 
										}
								} else if (endLetter == startLetter +2) {
									
									if (checkPiece(convertToChessNotation(startNum, startLetter) + " " + convertToChessNotation(startNum, startLetter +1), subTurn % 2) == 1) {
										System.out.println("Illegal move, try again");
										continue;
									}
									
									//white king side
									//move king to g1, rook to f1
									if(board[startNum][startLetter+3].getPiece() instanceof Rook) {
										if((((Rook)board[startNum][startLetter+3].getPiece()).getMoved()!=true) && (((King)board[startNum][startLetter].getPiece()).getMoved()!=true)){
											
											//if check for empty pieces in between
												if (board[startNum][startLetter+1].isEmpty() && board[startNum][startLetter+2].isEmpty()) {
													
													board[startNum][startLetter+3].setPiece(null);
													board[startNum][startLetter+1].setPiece(new Rook(true));
													
													board[startNum][startLetter].setPiece(null);
													board[startNum][startLetter+2].setPiece(new King(true));
													
													((Rook) board[startNum][startLetter+1].getPiece()).setMoved(true);
													((King) board[endNum][endLetter].getPiece()).setMoved(true);
					
													
												}
										}
									}
									
								}
							}
							System.out.println(this.toString());
							subTurn++;
							Pcheck = 1;
							continue;
							
						}
						}

						
						board[Integer.parseInt(endposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))]
								.setPiece(board[Integer.parseInt(startposition.substring(0, 1))][Integer
										.parseInt(startposition.substring(1))].getPiece());
						board[Integer.parseInt(startposition.substring(0, 1))][Integer
								.parseInt(startposition.substring(1))].setPiece(null);
						
						if(board[Integer.parseInt(endposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))].getPiece() instanceof Pawn)
							if(Math.abs(Integer.parseInt(endposition.substring(1))-Integer.parseInt(startposition.substring(1)))==2)
								((Pawn) board[Integer.parseInt(endposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))].getPiece()).setMoved(true);
						
						if(input.length() > 5) {
							if(input.contains("draw")) {
								draw = true;
							}
						}

						System.out.println(this.toString());
						subTurn++;
						Pcheck = 1;
					}
				}

			} else { // black's move if odd turn
				Pcheck = 0;
				while (Pcheck == 0) {
					
					input = in.nextLine();
					
					if (input.equals("resign")) {  //first check if player resigns 
						System.out.println("White wins");
						gameOver = 1;
						break;
					}
					
					if (input.equals("draw")&& draw) {  //if other player offered a draw
						//System.out.println("");
						gameOver = 1;
						break;
					}
					
					if(draw) {
						draw = false;
					}
					
					
					if (checkPiece(input, subTurn) == 1) {
						System.out.println("Illegal move, try again");
					} else if (checkPiece(input, subTurn) == 0) {
						System.out.println("Black's move: " + input + "\n");

						String startposition = convertFromChessNotation(input.substring(0, 2));
						String endposition = convertFromChessNotation(input.substring(3, 5));
						
						String promotePiece = "";
						
						if (input.length() > 6) {
							promotePiece = input.substring(6);
						}
						
						int startNum = Integer.parseInt(startposition.substring(0, 1));
						int startLetter = Integer.parseInt(startposition.substring(1));
						int endNum= Integer.parseInt(endposition.substring(0, 1));
						int endLetter = Integer.parseInt(endposition.substring(1));
						
						
						// DO PROMOTION IN THIS AREA
						//if at end of board for promotion
						//swap piece with choice of bishop, knight, rook, or queen of same color
						if (endNum == 1) { //if white piece in row 8
							board[startNum][startLetter].setPiece(null);
							switch(promotePiece) {
							//set old pawn piece to null?
							//board[endLetter][endNum].setPiece(null);
							case "R":
								board[endNum][endLetter].setPiece(new Rook(true));
								break;
							case "N":
								board[endNum][endLetter].setPiece(new Knight(true));
								break;
							case "B":
								board[endNum][endLetter].setPiece(new Bishop(true));
								break;
							default:
								board[endNum][endLetter].setPiece(new Queen(true));
								break;
							}

							System.out.println(this.toString());
							subTurn++;
							Pcheck = 1;
							continue;
							
						}
						
						
						// DO CASTLING IN THIS AREA
						//A castling move is indicated by specifying where the king begins and ends. So, white castling king's side would be "e1 g1". 
						//do castling on both black and white ends for queen and king sides
						if (board[startNum][startLetter].getPiece() instanceof King) {
						if (((King)board[startNum][startLetter].getPiece()).getMoved()!=true) {
							if ((endLetter == startLetter-2 && startNum==endNum) || (endLetter == startLetter + 2 && startNum==endNum)) {
								if (checkb) {
									System.out.println("Illegal move, try again");
									continue;
								}
								//white queen side
								// move Rook to d1, king to c1
								if (endLetter == startLetter-2) {
									//if check for empty pieces in between
									if (checkPiece(convertToChessNotation(startNum, startLetter) + " " + convertToChessNotation(startNum, startLetter -1), subTurn % 2) == 1) {
										System.out.println("Illegal move, try again");
										continue;
									}
									if(board[startNum][startLetter-4].getPiece() instanceof Rook) {
										if((((Rook)board[startNum][startLetter-4].getPiece()).getMoved()!=true) && (((King)board[startNum][startLetter].getPiece()).getMoved()!=true)){
											
											
												if (board[startNum][startLetter-3].isEmpty() && board[startNum][startLetter-2].isEmpty() && board[startNum][startLetter-1].isEmpty()) {
													//if check for king being in check or into check or through check
								
													board[startNum][startLetter-4].setPiece(null);
													board[startNum][startLetter-1].setPiece(new Rook(true));
													
													board[startNum][startLetter].setPiece(null);
													board[startNum][startLetter-2].setPiece(new King(true));
													
													((Rook) board[startNum][startLetter-1].getPiece()).setMoved(true);
													((King) board[endNum][endLetter].getPiece()).setMoved(true);
													//tesdt
												
												}
											} 
										}
								} else if (endLetter == startLetter +2) {
									
									if (checkPiece(convertToChessNotation(startNum, startLetter) + " " + convertToChessNotation(startNum, startLetter +1), subTurn % 2) == 1) {
										System.out.println("Illegal move, try again");
										continue;
									}
									
									//white king side
									//move king to g1, rook to f1
									if(board[startNum][startLetter+3].getPiece() instanceof Rook) {
										if((((Rook)board[startNum][startLetter+3].getPiece()).getMoved()!=true) && (((King)board[startNum][startLetter].getPiece()).getMoved()!=true)){
											
											//if check for empty pieces in between
												if (board[startNum][startLetter+1].isEmpty() && board[startNum][startLetter+2].isEmpty()) {
													
													board[startNum][startLetter+3].setPiece(null);
													board[startNum][startLetter+1].setPiece(new Rook(true));
													
													board[startNum][startLetter].setPiece(null);
													board[startNum][startLetter+2].setPiece(new King(true));
													
													((Rook) board[startNum][startLetter+1].getPiece()).setMoved(true);
													((King) board[endNum][endLetter].getPiece()).setMoved(true);
					
													
												}
											
										}
									}
									
								}
							}
							System.out.println(this.toString());
							subTurn++;
							Pcheck = 1;
							continue;
							
						}
						}
						

						if(board[Integer.parseInt(startposition.substring(0, 1))][Integer.parseInt(startposition.substring(1))].getPiece() instanceof Pawn)
							if(board[Integer.parseInt(endposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))].isEmpty())
								if(Math.abs(Integer.parseInt(endposition.substring(1))-Integer.parseInt(startposition.substring(1)))==1)
									board[Integer.parseInt(startposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))].setPiece(null);
						
						
						board[Integer.parseInt(endposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))]
								.setPiece(board[Integer.parseInt(startposition.substring(0, 1))][Integer
										.parseInt(startposition.substring(1))].getPiece());
						board[Integer.parseInt(startposition.substring(0, 1))][Integer
								.parseInt(startposition.substring(1))].setPiece(null);
						
						if(board[Integer.parseInt(endposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))].getPiece() instanceof Pawn)
							if(Math.abs(Integer.parseInt(endposition.substring(1))-Integer.parseInt(startposition.substring(1)))==2)
								((Pawn) board[Integer.parseInt(endposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))].getPiece()).setMoved(true);
						
						if(input.length() > 5) {
							if(input.contains("draw")) {
								draw = true;
							}
						}

						System.out.println(this.toString());
						subTurn++;
						Pcheck = 1;
					}
				}

			}
			
			// if gameOver is 1 due to draw or checkmate or resignation, the while loop ends
			// and the program can terminate
		}
		in.close();
	}

	// method to check piece. if the input string is an illegal move for that piece,
	// return 1, otherwise, return 0
	/**
	 * Checks to see if the moves is legal (King remains in check or moves into check)
	 * 
	 * 
	 * @param in the move to be made
	 * @param white the current player
	 * @return 0 if the move is legal 1 if not
	 */
	public int checkPiece(String in, int white) {

		String startposition = convertFromChessNotation(in.substring(0, 2));
		String endposition = convertFromChessNotation(in.substring(3, 5));
		
		int startLetter = Integer.parseInt(startposition.substring(0, 1));
		int startNum = Integer.parseInt(startposition.substring(1));
		int endLetter = Integer.parseInt(endposition.substring(0, 1));
		int endNum = Integer.parseInt(endposition.substring(1));
		

		Board temp = new Board(board);
		
		if(temp.board[Integer.parseInt(startposition.substring(0, 1))][Integer.parseInt(startposition.substring(1))].getPiece() instanceof Pawn)
			if(temp.board[Integer.parseInt(endposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))].isEmpty())
				if(Math.abs(Integer.parseInt(endposition.substring(1))-Integer.parseInt(startposition.substring(1)))==1)
					temp.board[Integer.parseInt(startposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))].setPiece(null);

		temp.board[Integer.parseInt(endposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))]
				.setPiece(temp.board[Integer.parseInt(startposition.substring(0, 1))][Integer
						.parseInt(startposition.substring(1))].getPiece());
		temp.board[Integer.parseInt(startposition.substring(0, 1))][Integer.parseInt(startposition.substring(1))]
				.setPiece(null);
		
		
		if(temp.board[Integer.parseInt(endposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))].getPiece() instanceof Pawn)
			((Pawn) temp.board[Integer.parseInt(endposition.substring(0, 1))][Integer.parseInt(endposition.substring(1))].getPiece()).setMoved(true);
		
		if (temp.board[startLetter][startNum].getPiece() instanceof King) {
			if (((King)temp.board[startLetter][startNum].getPiece()).getMoved()!=true) {
				if ((endLetter == startLetter-4 && startNum==endNum) || (endLetter == startLetter + 3 && startNum==endNum)) {
					//white queen side
					// move Rook to d1, king to c1
					if(temp.board[startLetter][startNum-4].getPiece() instanceof Rook) {
						if((((Rook)temp.board[startLetter][startNum-4].getPiece()).getMoved()!=true) && (((King)temp.board[startLetter][startNum].getPiece()).getMoved()!=true)){
							
							//if check for empty pieces in between
								if (temp.board[startLetter][startNum-3].isEmpty() && temp.board[startLetter][startNum-2].isEmpty() && temp.board[startLetter][startNum-1].isEmpty()) {
									//if check for king being in check or into check or through check
				
									temp.board[startLetter][startNum-4].setPiece(null);
									temp.board[startLetter][startNum-1].setPiece(new Rook(true));
									
									temp.board[startLetter][startNum].setPiece(null);
									temp.board[startLetter][startNum-2].setPiece(new King(true));
									
									((Rook) temp.board[startLetter][startNum-1].getPiece()).setMoved(true);
									((King) temp.board[endLetter][endNum].getPiece()).setMoved(true);
									
								}
							} 
						}
					
					//white king side
					//move king to g1, rook to f1
						if(temp.board[startLetter][startNum+3].getPiece() instanceof Rook) {
							if((((Rook)temp.board[startLetter][startNum+3].getPiece()).getMoved()!=true) && (((King)temp.board[startLetter][startNum].getPiece()).getMoved()!=true)){
								
								//if check for empty pieces in between
									if (temp.board[startLetter][startNum+1].isEmpty() && temp.board[startLetter][startNum+2].isEmpty()) {
										
										temp.board[startLetter][startNum+3].setPiece(null);
										temp.board[startLetter][startNum+1].setPiece(new Rook(true));
										
										temp.board[startLetter][startNum].setPiece(null);
										temp.board[startLetter][startNum+2].setPiece(new King(true));
										
										((Rook) temp.board[startLetter][startNum+1].getPiece()).setMoved(true);
										((King) temp.board[endLetter][endNum].getPiece()).setMoved(true);
			
									} 
							}
						}
				}
			}
		}

		boolean color = (white % 2) == 0 ? true : false;
		String kingposition = "";

		for (int i = 0; i < temp.board.length; i++) {
			for (int j = 0; j < temp.board[0].length; j++) {
				if (temp.board[i][j].isEmpty() == false) {
					if (temp.board[i][j].getPiece() instanceof King
							&& temp.board[i][j].getPiece().getColor() == color) {
						kingposition = convertToChessNotation(i, j);
					}
				}
			}
		}
		// System.out.println(kingposition);
		List<String> counterMove = temp.generateMoves((white + 1) % 2);
		for (String s : counterMove) {
			// System.out.println(s);
			if (s.substring(3, 5).equals(kingposition))
				return 1;
		}

		boolean check = false;

		for (String s : possibleMoves) {
			if (s.equals(in.substring(0,5))) {
				check = true;
			}
		}
		// check for each piece here

		/*
		 * if (move is illegal) return 1;
		 */
		if (check)
			return 0;
		return 1;
	}

	/**
	 * Returns the board as a 2d array
	 * 
	 * @return the array of the board
	 */
	public Spot[][] getBoard() {
		return board;
	}

	// method to generate all possible moves
	/**
	 * Generate list of moves without consideration of enemy checks
	 * 
	 * @param k the current player
	 * @return a list of a possible moves given the pieces
	 */
	public List<String> generateMoves(int k) {
		List<String> temp = new ArrayList<String>();
		boolean white = k == 0 ? true : false;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				Piece piece = board[i][j].getPiece();

				if (piece instanceof Pawn) {

					// whitepawns
					if (white == true) {
						if (piece.getColor() == true) {

							// oneStepforward
							if (i + 1 < 8) {
								if (board[i + 1][j].isEmpty()) {
									temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i + 1, j));

									// twoStepsfoward
									if (i == 1) {
										if (!((Pawn) piece).getMoved()) {
											if (board[i + 2][j].isEmpty()) {
												temp.add(convertToChessNotation(i, j) + " "
														+ convertToChessNotation(i + 2, j));
											}
										}
									}
								}
								
								// diagonal take upperLeft
								if (j - 1 > 0) {
									
									if(board[i][j-1].getPiece() instanceof Pawn) {
										if(board[i][j-1].getPiece().getColor()!= piece.getColor()) {
											if(((Pawn) board[i][j-1].getPiece()).getMoved()==true) {
												((Pawn) board[i][j-1].getPiece()).setMoved(false);
												temp.add(convertToChessNotation(i, j) + " "
														+ convertToChessNotation(i + 1, j - 1));
											}
										}
									}
									
									if (!board[i + 1][j - 1].isEmpty())
										if (board[i + 1][j - 1].getPiece().getColor() != piece.getColor())
											temp.add(convertToChessNotation(i, j) + " "
													+ convertToChessNotation(i + 1, j - 1));
								}
								// diagonal take upperRight
								if (j + 1 < 8) {
									
									if(board[i][j+1].getPiece() instanceof Pawn) {
										if(board[i][j+1].getPiece().getColor()!= piece.getColor()) {
											if(((Pawn) board[i][j+1].getPiece()).getMoved()==true) {
												((Pawn) board[i][j+1].getPiece()).setMoved(false);
												temp.add(convertToChessNotation(i, j) + " "
														+ convertToChessNotation(i + 1, j + 1));
											}
										}
									}
									
									
									if (!board[i + 1][j + 1].isEmpty())
										if (board[i + 1][j + 1].getPiece().getColor() != piece.getColor())
											temp.add(convertToChessNotation(i, j) + " "
													+ convertToChessNotation(i + 1, j + 1));
								}
							}
						}
					}

					// blackpawns
					else {
						if (piece.getColor() == false) {

							// oneStepforward
							if (i - 1 > -1) {
								if (board[i - 1][j].isEmpty()) {
									temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i - 1, j));

									// twoStepsfoward
									if (i == 6) {
										if (!((Pawn) piece).getMoved()) {
											if (board[i - 2][j].isEmpty()) {
												temp.add(convertToChessNotation(i, j) + " "
														+ convertToChessNotation(i - 2, j));
											}
										}
									}
								}
							
								// diagonal take upperLeft
								if (j - 1 > 0) {
									
									if(board[i][j-1].getPiece() instanceof Pawn) {
										if(board[i][j-1].getPiece().getColor()!= piece.getColor()) {
											if(((Pawn) board[i][j-1].getPiece()).getMoved()==true) {
												((Pawn) board[i][j-1].getPiece()).setMoved(false);
												temp.add(convertToChessNotation(i, j) + " "
														+ convertToChessNotation(i - 1, j - 1));
											}
										}
									}
									
									if (!board[i - 1][j - 1].isEmpty())
										if (board[i - 1][j - 1].getPiece().getColor() != piece.getColor())
											temp.add(convertToChessNotation(i, j) + " "
													+ convertToChessNotation(i - 1, j - 1));
								}
								// diagonal take upperRight
								if (j + 1 < 8) {
									
									if(board[i][j+1].getPiece() instanceof Pawn) {
										if(board[i][j+1].getPiece().getColor()!= piece.getColor()) {
											if(((Pawn) board[i][j-1].getPiece()).getMoved()==true) {
												((Pawn) board[i][j-1].getPiece()).setMoved(false);
												temp.add(convertToChessNotation(i, j) + " "
														+ convertToChessNotation(i - 1, j + 1));
											}
										}
									}
									
									
									if (!board[i - 1][j + 1].isEmpty())
										if (board[i - 1][j + 1].getPiece().getColor() != piece.getColor())
											temp.add(convertToChessNotation(i, j) + " "
													+ convertToChessNotation(i - 1, j + 1));
								}
							}
						}
					}
				}

				else if (piece instanceof Bishop) {
					int distance = 1;
					if (piece.getColor() == white) {

						while (i + distance < 8 && j + distance < 8) {
							if (board[i + distance][j + distance].isEmpty())
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i + distance, j + distance));
							else if (board[i + distance][j + distance].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i + distance, j + distance));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;

						while (i + distance < 8 && j - distance > -1) {
							if (board[i + distance][j - distance].isEmpty())
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i + distance, j - distance));
							else if (board[i + distance][j - distance].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i + distance, j - distance));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;

						while (i - distance > -1 && j + distance < 8) {
							if (board[i - distance][j + distance].isEmpty())
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i - distance, j + distance));
							else if (board[i - distance][j + distance].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i - distance, j + distance));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;

						while (i - distance > -1 && j - distance > -1) {
							if (board[i - distance][j - distance].isEmpty())
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i - distance, j - distance));
							else if (board[i - distance][j - distance].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i - distance, j - distance));
								break;
							} else
								break;
							distance++;
						}
					}

				}

				else if (piece instanceof Rook) {
					int distance = 1;
					if (piece.getColor() == white) {
						while (i - distance > -1) {
							if (board[i - distance][j].isEmpty())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i - distance, j));
							else if (board[i - distance][j].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i - distance, j));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;

						while (i + distance < 8) {
							if (board[i + distance][j].isEmpty())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i + distance, j));
							else if (board[i + distance][j].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i + distance, j));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;

						while (j - distance > -1) {
							if (board[i][j - distance].isEmpty())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i, j - distance));
							else if (board[i][j - distance].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i, j - distance));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;

						while (j + distance < 8) {
							if (board[i][j + distance].isEmpty())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i, j + distance));
							else if (board[i][j + distance].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i, j + distance));
								break;
							} else
								break;
							distance++;
						}
					}

				}

				else if (piece instanceof Knight) {
					if (piece.getColor() == white) {
						if (i + 2 < 8 && j + 1 < 8)
							if (board[i + 2][j + 1].isEmpty()
									|| board[i + 2][j + 1].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i + 2, j + 1));
						if (i + 1 < 8 && j + 2 < 8)
							if (board[i + 1][j + 2].isEmpty()
									|| board[i + 1][j + 2].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i + 1, j + 2));
						if (i + 2 < 8 && j - 1 > -1)
							if (board[i + 2][j - 1].isEmpty()
									|| board[i + 2][j - 1].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i + 2, j - 1));
						if (i + 1 < 8 && j - 2 > -1)
							if (board[i + 1][j - 2].isEmpty()
									|| board[i + 1][j - 2].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i + 1, j - 2));
						if (i - 1 > -1 && j + 2 < 8)
							if (board[i - 1][j + 2].isEmpty()
									|| board[i - 1][j + 2].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i - 1, j + 2));
						if (i - 2 > -1 && j + 1 < 8)
							if (board[i - 2][j + 1].isEmpty()
									|| board[i - 2][j + 1].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i - 2, j + 1));
						if (i - 1 > -1 && j - 2 > -1)
							if (board[i - 1][j - 2].isEmpty()
									|| board[i - 1][j - 2].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i - 1, j - 2));
						if (i - 2 > -1 && j - 1 > -1)
							if (board[i - 2][j - 1].isEmpty()
									|| board[i - 2][j - 1].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i - 2, j - 1));
					}
				}

				else if (piece instanceof Queen) {
					int distance = 1;
					if (piece.getColor() == white) {
						while (i - distance > -1) {
							if (board[i - distance][j].isEmpty())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i - distance, j));
							else if (board[i - distance][j].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i - distance, j));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;

						while (i + distance < 8) {
							if (board[i + distance][j].isEmpty())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i + distance, j));
							else if (board[i + distance][j].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i + distance, j));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;

						while (j - distance > -1) {
							if (board[i][j - distance].isEmpty())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i, j - distance));
							else if (board[i][j - distance].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i, j - distance));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;

						while (j + distance < 8) {
							if (board[i][j + distance].isEmpty())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i, j + distance));
							else if (board[i][j + distance].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i, j + distance));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;
						while (i + distance < 8 && j + distance < 8) {
							if (board[i + distance][j + distance].isEmpty())
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i + distance, j + distance));
							else if (board[i + distance][j + distance].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i + distance, j + distance));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;

						while (i + distance < 8 && j - distance > -1) {
							if (board[i + distance][j - distance].isEmpty())
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i + distance, j - distance));
							else if (board[i + distance][j - distance].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i + distance, j - distance));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;

						while (i - distance > -1 && j + distance < 8) {
							if (board[i - distance][j + distance].isEmpty())
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i - distance, j + distance));
							else if (board[i - distance][j + distance].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i - distance, j + distance));
								break;
							} else
								break;
							distance++;
						}
						distance = 1;

						while (i - distance > -1 && j - distance > -1) {
							if (board[i - distance][j - distance].isEmpty())
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i - distance, j - distance));
							else if (board[i - distance][j - distance].getPiece().getColor() != piece.getColor()) {
								temp.add(convertToChessNotation(i, j) + " "
										+ convertToChessNotation(i - distance, j - distance));
								break;
							} else
								break;
							distance++;
						}
					}
				}

				else if (piece instanceof King) {
					if (piece.getColor() == white) {
						if (i + 1 < 8 && j + 1 < 8)
							if (board[i + 1][j + 1].isEmpty()
									|| board[i + 1][j + 1].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i + 1, j + 1));
						if (i + 1 < 8)
							if (board[i + 1][j].isEmpty() || board[i + 1][j].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i + 1, j));
						if (i + 1 < 8 && j - 1 > -1)
							if (board[i + 1][j - 1].isEmpty()
									|| board[i + 1][j - 1].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i + 1, j - 1));
						if (j + 1 < 8)
							if (board[i][j + 1].isEmpty() || board[i][j + 1].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i, j + 1));
						if (i - 1 > -1 && j + 1 < 8)
							if (board[i - 1][j + 1].isEmpty()
									|| board[i - 1][j + 1].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i - 1, j + 1));
						if (i - 1 > -1)
							if (board[i - 1][j].isEmpty() || board[i - 1][j].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i - 1, j));
						if (i - 1 > -1 && j - 1 > -1)
							if (board[i - 1][j - 1].isEmpty()
									|| board[i - 1][j - 1].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i - 1, j - 1));
						if (j - 1 > -1)
							if (board[i][j - 1].isEmpty() || board[i][j - 1].getPiece().getColor() != piece.getColor())
								temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i, j - 1));
						
						
						//check if castle move is appropriate
						if(((King) piece).getMoved() == false) {
							if(j+3<8) {
								if(board[i][j+3].getPiece() instanceof Rook && ((Rook) board[i][j+3].getPiece()).getMoved()==false) {
									if(board[i][j+1].isEmpty()&&board[i][j+2].isEmpty()) {
										temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i, j + 2));
									}
								}
							}
							if(j-4>-1) {
								if(board[i][j-4].getPiece() instanceof Rook && ((Rook) board[i][j-4].getPiece()).getMoved()==false) {
									if(board[i][j-1].isEmpty()&&board[i][j-2].isEmpty()&&board[i][j-3].isEmpty()) {
										temp.add(convertToChessNotation(i, j) + " " + convertToChessNotation(i, j - 2));
									}
								}
							}	
						}
						
						
						
					}
 
				}
			}
		}
		
		for (int i = 0; i < board.length; i++) 
			for (int j = 0; j < board.length; j++) 
				if(board[i][j].getPiece() instanceof Pawn)
					if(((Pawn) board[i][j].getPiece()).getMoved()==true)
						((Pawn) board[i][j].getPiece()).setMoved(false);
			
		
		
		return temp;
	}

	/**
	 * Converts array indexes to chess board algebraic notation
	 * 
	 * @param i	the number of the board
	 * @param j the column of the board
	 * @return the chess notation for a position on the board
	 */
	private String convertToChessNotation(int i, int j) {
		String string = "";
		i = i + 1;
		switch (j) {
		case 0:
			string = string + "a" + i;
			break;
		case 1:
			string = string + "b" + i;
			break;
		case 2:
			string = string + "c" + i;
			break;
		case 3:
			string = string + "d" + i;
			break;
		case 4:
			string = string + "e" + i;
			break;
		case 5:
			string = string + "f" + i;
			break;
		case 6:
			string = string + "g" + i;
			break;
		case 7:
			string = string + "h" + i;
			break;
		}
		return string;
	}

	/**
	 * Converts chess notation to the board indexes
	 * 
	 * 
	 * @param s the Chess notation
	 * @return the array index of the board
	 */
	private String convertFromChessNotation(String s) {
		String result = "";
		char letter = s.charAt(0);
		switch (letter) {
		case 'a':
			result = result + "0";
			break;
		case 'b':
			result = result + "1";
			break;
		case 'c':
			result = result + "2";
			break;
		case 'd':
			result = result + "3";
			break;
		case 'e':
			result = result + "4";
			break;
		case 'f':
			result = result + "5";
			break;
		case 'g':
			result = result + "6";
			break;
		case 'h':
			result = result + "7";
			break;
		}
		result = (Integer.parseInt(s.substring(1)) - 1) + result;
		return result;
	}
}

