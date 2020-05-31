import java.awt.*;

/**
 * Serves as the Strategy in the 'strategy pattern,' providing different ways of
 * formatting the same data
 * 
 * @author Diljot Singh, Dominic Scalero, Xiyu Chen
 *
 */
public interface GameBoardFormatter {

	Color formatBoardColor(); //Formats the color of the board (background)

	Font formatTextFont(); //Formats the font of the text for squares
	
	Font formatTextFont(char component); //Formats the font of the text for the given component

	Color formatTextColor(char mark); //Formats the color of the text (foreground)
}

