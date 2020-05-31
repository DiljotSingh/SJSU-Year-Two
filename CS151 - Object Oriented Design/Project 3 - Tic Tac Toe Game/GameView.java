import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

/**
 * This class serves as the VIEW in the Tic Tac Toe game Presents the board to
 * the user
 * 
 * @author Diljot Singh, Dominic Scalero, Xiyu Chen
 *
 */
public class GameView implements Observer {

	private GameModel model; // The model to observe
	private JPanel container; // A container for all the board squares (JButtons)
	private GameBoardFormatter style; // The user's selected style (such as DarkStyle or LightStyle)

	/**
	 * Constructs a GameView with the game model to observe
	 * 
	 * @param model the game board of inputs
	 */
	public GameView(GameModel model) {
		this.model = model; // model to observe
		container = new JPanel(new GridLayout(4, 3)); // container with a grid layout (game board)
	}

	/**
	 * Initial game menu, asks the user to select a board style by clicking the
	 * corresponding button Based on which style is selected, the game board get
	 * formatted accordingly using the strategy pattern
	 */
	public void styleSelector() {

		// Components
		JButton darkStyleButton = new JButton("Dark Mode"); // Button for dark mode
		JButton lightStyleButton = new JButton("Light Mode"); // Button for light mode
		JLabel promptLabel = new JLabel("Choose your preferred game style below: "); // Label to prompt user

		// Customize design on the prompt label
		promptLabel.setHorizontalAlignment(JLabel.CENTER); // Setting the alignment of the label to center
		promptLabel.setVerticalAlignment(JLabel.CENTER); // Setting the alignment of the label to center
		promptLabel.setFont(new Font("Courier", Font.CENTER_BASELINE, 15)); // Setting the font specifications
		promptLabel.setForeground(Color.BLACK); // Setting the color of the label text

		// Customize design on the dark button
		darkStyleButton.setBackground(new Color(25, 45, 125)); // Background color of the button
		darkStyleButton.setForeground(Color.WHITE); // Text color of the button
		darkStyleButton.setFocusPainted(false);
		darkStyleButton.setFont(new Font("Courier", Font.BOLD, 30)); // Font specifications of the button

		// Customize design on the light button
		lightStyleButton.setBackground(new Color(195, 60, 67)); // Background color of the button
		lightStyleButton.setForeground(Color.WHITE); // Text color of the button
		lightStyleButton.setFocusPainted(false);
		lightStyleButton.setFont(new Font("Courier", Font.BOLD, 30)); // Font specifications of the button

		// Adds the label to the container
		container.add(promptLabel);

		// Add both buttons to the container
		container.add(darkStyleButton);
		container.add(lightStyleButton);

		// Action if dark button is clicked
		darkStyleButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				style = new DarkStyle(); // Style is set to dark
				container.removeAll(); // removes the two buttons from the container
				container.updateUI(); // updates the UI so buttons are no longer shown
				drawBoard(); // draws the board
			}
		});

		// Action if light button is clicked
		lightStyleButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				style = new LightStyle(); // Style is set to light
				container.removeAll(); // removes the two buttons from the container
				container.updateUI(); // updates the UI so buttons are no longer shown
				drawBoard(); // draws the board

			}
		});
	}

	/**
	 * Draws the game board and adds listeners to display the player's marker
	 * whenever a square is clicked
	 */
	public void drawBoard() {
		
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {

				// The current marker at the particular row and column in the board
				char currentMarker = model.getBoard()[row][column];

				// Creates a new square (JButton) for each value in the board
				// This sets the button's text to the corresponding board marker
				JButton square = new JButton(String.valueOf(currentMarker));

				// Adds that square to the container
				container.add(square);

				// Uses strategy pattern to style the square's background and text font
				square.setBackground(style.formatBoardColor());
				square.setFont(style.formatTextFont());

				// Uses strategy pattern to style the square's text color based on its marker
				square.setForeground(style.formatTextColor(currentMarker));

				// <-------------------------CONTROLLER-------------------->
				// Action listener for every square (CONTROLLER), places a mark on the square
				// when it is clicked
				square.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						// Calculates what index to modify in game board based on the button's
						// coordinates
						int row = square.getY() / square.getHeight(); // three buttons per row, calculates which row
						int column = square.getX() / square.getWidth(); // three buttons per column, calculates which
																		// column

						// Updates the game model's board by adding a mark at that specified index
						// In other words, board[row][column] = mark
						model.addPlayerMark(row, column, model.getCurrentMark());                                  
					}
				});

			}
		}
		
		// Creates text fields indicating to the player the current turn
		char currTurn = model.getCurrentMark();
		JTextField turnText = new JTextField("Current turn:");
		JTextField turnInfo = new JTextField(String.valueOf(currTurn));
		
		// Creates a new undo button (JButton) at the bottom of the board
		JButton undoButton = new JButton("Undo!");
		undoButton.setLayout(new BorderLayout());
		
		//Shows how many undoes are remaining for the current player
		JLabel undoesRemaining = new JLabel("Undos remaining: " + model.getUndoesRemaining());
		undoesRemaining.setForeground(Color.BLACK);
		undoButton.add(BorderLayout.SOUTH, undoesRemaining);
		
		// Adds text fields and undo button to the container
		container.add(turnText);
		container.add(turnInfo);
		container.add(undoButton);
		
		// Uses strategy pattern to style the text fields' background and text font
        turnText.setBackground(style.formatBoardColor());
        turnInfo.setBackground(style.formatBoardColor());
        turnText.setFont(style.formatTextFont('T'));
        turnInfo.setFont(style.formatTextFont());
        
        // Uses strategy pattern to style the undo button's text font
        undoButton.setFont(style.formatTextFont('U'));
        
        // Uses strategy pattern to style the components' color
        turnText.setForeground(style.formatTextColor('O'));
        turnInfo.setForeground(style.formatTextColor(currTurn));
        undoButton.setForeground(Color.BLACK);
		
        // Centers text in text fields
        turnText.setHorizontalAlignment(JTextField.CENTER);
        turnInfo.setHorizontalAlignment(JTextField.CENTER);
        
      
        //Action listener for undo button
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            	
                // attempt undo and store success value in boolean
                boolean goodUndo = model.undo();
                undoButton.setEnabled(goodUndo); //Undo button gets disabled after one undo has been made
                 								 //This prevents multiple undos in a row
               
                //If the undo was unsuccessful, then we indicate so to the user
                if (!goodUndo) {
                    showBadUndo();
                }
                
            }
        });
        		
	}
	
	/**
	 * When a tie has been declared by the GameModel, pop up a dialog indicating so
	 */
	public void showTie() {
		JOptionPane.showMessageDialog(this.container, "It's a tie!", "Tie", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * When a winner has been declared by the GameModel, pop up a dialog indicating
	 * so
	 * 
	 * @param winnerMark the winner's marker
	 */
	public void showWinner(char winnerMark) {
		JOptionPane.showMessageDialog(this.container, winnerMark + " won!", "Winner", JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
     * When a undo has failed, pop up a dialog indicating so
     */
    public void showBadUndo() {
        JOptionPane.showMessageDialog(this.container, "Cannot go back more than one turn. \nCan only undo three times per turn.", "Invalid undo", JOptionPane.PLAIN_MESSAGE);
    }

	/**
	 * Getter for the container of buttons
	 * 
	 * @return container
	 */
	public JPanel getContainer() {
		return this.container;
	}

	/**
	 * Whenever GameModel's board data changes, update the view by redrawing all the components
	 * If there is a winner or a tie, then we disable all the components and announce the game result
	 */
	public void update(Observable arg0, Object arg1) {

		container.removeAll(); //Removes all the current components from the container
		drawBoard(); 		   //Redraws them, displaying their new values

		// If there was a winner or a tie, then we disable all the components
		if (model.checkWinner() || model.checkTie()) {

			// Disables all the components so no more squares can be clicked
			for (Component c : container.getComponents()) {

				c.setEnabled(false);
			}
		}

		// If there is a winner, we display the winner
		if (model.checkWinner()) {
			showWinner(model.getCurrentMark());
		}
		// Otherwise if there was a tie, we indicate a tie
		else if (model.checkTie()) {
			showTie();
		}

	}

}
