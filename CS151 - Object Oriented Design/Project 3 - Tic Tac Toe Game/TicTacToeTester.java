import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;


/**
 * Tester for the Tic Tac Toe game
 * 
 * @author Diljot Singh, Dominic Scalero, Xiyu Chen
 *
 */
public class TicTacToeTester {

	public static void main(String[] args) {

		JFrame gameFrame = new JFrame(); // Creates a frame for the game
		GameModel model = new GameModel(); // Create the GameModel
		GameView view = new GameView(model); // Create the GameView and pass in the model to its constructor

		model.addObserver(view); // Adds the view as an observer to the model

		
		//Centers the game frame when the program is ran
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		gameFrame.setLocation((screenSize.width)/3, (screenSize.height)/8);
		
		
		gameFrame.setSize(500, 500); // Set the size of the frame to be 500x500
		gameFrame.add(view.getContainer()); // Adds the JPanel from the view to the frame
		gameFrame.setTitle("Tic Tac Toe"); //Sets the title of the frame to be "Tic Tac Toe"

		// Prompts the user to select a style for the game board by clicking one of two options
		view.styleSelector(); // Once the style has been selected, the board is automatically drawn

		
		
		// <----------------------Standard closing operations----------------->

		gameFrame.setVisible(true); //Sets the frame to be visible
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exits the frame when it is closed

	}

}
