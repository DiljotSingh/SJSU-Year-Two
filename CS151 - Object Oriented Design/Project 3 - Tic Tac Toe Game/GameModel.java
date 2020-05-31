import java.util.*; 

/**
 * This class serves as the MODEL in the Tic Tac Toe game 
 * Contains all the data and logic for determining a winner
 * 
 * @author Diljot Singh, Dominic Scalero, Xiyu Chen
 *
 */
public class GameModel extends Observable {

	private static final int ROW_SIZE = 3; // Tic Tac Toe boards have three columns
	private static final int COLUMN_SIZE = 3; // Tic Tac Toe boards have three rows

	private char[][] board; // game board to contain all inputs
	private char currentPlayerMark; // contains current player mark (alternates between 'X' and 'O')
	
	private char[][] lastBoard;     	 // keeps track of previous state of the board; is set to null if an undo has just been made
    private int undos;             		// number of undos made this turn
    private final int MAX_UNDOS = 3;   // max number of undos that can be made in one turn

	/**
	 * Constructs a GameModel with an empty board
	 */
	public GameModel() {
		this.board = new char[ROW_SIZE][COLUMN_SIZE]; // Tic Tac Toe board is 3x3
		currentPlayerMark = 'X'; // first player mark is initialized to 'X'
		undos = 0;
	}

	/**
	 * Adds a player's mark (X or O) at a specified location on the board
	 */
	public void addPlayerMark(int row, int column, char mark) {
		
		// Ensure the current square is empty before modifying it
		if (board[row][column] == 0) {
		    
		    // reset number of undos if a new turn starts
		    if (lastBoard != null) {
		        undos = 0;
		    }
		    
		    // set lastBoard as a deep clone of board
		    lastBoard = new char[3][];
		    for (int i = 0; i < 3; i++) {
		        lastBoard[i] = board[i].clone();
		    }

			board[row][column] = mark; // Sets that square to display the mark

			nextPlayerTurn();      // alternates the player marker (other person's turn)
		}
		
		//Notify the observers (View) of the change so that it can redraw itself
		setChanged();
		notifyObservers();
		
	}
    
    /**
     * Checks that an undo can be made
     */
    public boolean undo() {
        
        // checks if an undo can be made 
        if (lastBoard == null || undos >= MAX_UNDOS) {
            return false;      // tells controller(?) that undo is invalid
        }
        
        // set board to previous state and reset lastBoard
        board = lastBoard;
        lastBoard = null;
        
        undos++;       // record undo made this turn
        nextPlayerTurn();  // alternates the player marker back (other person's turn)
        
        // Notify all Observers (View) so that it can refresh
        setChanged();
        notifyObservers();
        
        return true;   // tells controller(?) that undo was successful
    }
    
    /**
     * Alternates the player marker (other person's turn)
     */
    private void nextPlayerTurn() {
        if (currentPlayerMark == 'X') {
            currentPlayerMark = 'O'; // if current player was 'X', switch to 'O'
        } else {
            currentPlayerMark = 'X'; // otherwise switch to 'X'
        }
    }

	/**
	 * Checks to see if the board has a winner 
	 * A winner constitutes the same mark three consecutive times (diagonally counts too) 
	 * Example: 	X 0 O 
	 * 				O X 0 
	 * 				0 0 X 
	 * X is the winner in this case
	 * 
	 * @return boolean return true if there is a winner in the board, false if there is not
	 */
	public boolean checkWinner() {

		boolean winner = false; // return value

		// Check left corner diagonal
		if (board[0][0] != 0 && board[1][1] == board[0][0] && board[2][2] == board[1][1]) {

			currentPlayerMark = board[0][0]; //Updates the current mark to the winning mark
			winner = true; // we have a winner


		}
		// Check right corner diagonal
		else if (board[0][2] != 0 && board[1][1] == board[0][2] && board[2][0] == board[1][1]) {

			currentPlayerMark = board[0][2]; //Updates the current mark to the winning mark
			winner = true; // we have a winner


		}
		// Check vertical and horizontal rows
		else {
			for (int i = 0; i < 3; i++) {
				if (board[i][0] != 0 && board[i][1] == board[i][0] && board[i][2] == board[i][1]) {
					currentPlayerMark = board[i][0]; //Updates the current mark to the winning mark
					winner = true; // we have a winner

				}
				if (board[0][i] != 0 && board[1][i] == board[0][i] && board[2][i] == board[1][i]) {
					currentPlayerMark = board[0][i]; //Updates the current mark to the winning mark
					winner = true; // we have a winner

				}
			}
					}

		return winner; //Returns the winner
	}
	
	/**
	 * Checks to see if there is a tie in the board
	 * 
	 * @return boolean return true if there is a tie
	 */
	public boolean checkTie()
	{
		
		//If all squares have been filled up and there is no winner, then there is a tie
		for (int row = 0; row < 3; row++)
		{
			for (int column = 0; column < 3; column++) 
			{
				//Checks to see if every square is filled
				if (board[row][column] == 0) //If we encounter an empty square, we return false
				{

					return false; //Return false, there is no tie yet
				}
			}
		}
		return true; //Returns true if all squares were filled
	
	}
	
	/**
	 * Gets this game model's board
	 * @return board the game board with all the inputs
	 */
	public char[][] getBoard()
	{
		return this.board;
	}
	
	
	/**
	 * Gets the current player's mark (either 'X' or 'O')
	 * @return player mark
	 */
	public char getCurrentMark()
	{
		return currentPlayerMark;
	}
	
	/**
	 * Gets the number of undos remaining in the current player turn
	 * @return MAX_UNDOS - undos (3 - how many undos have been does so far)
	 */
	public int getUndoesRemaining()
	{
		return MAX_UNDOS - undos;
	}

	
}
