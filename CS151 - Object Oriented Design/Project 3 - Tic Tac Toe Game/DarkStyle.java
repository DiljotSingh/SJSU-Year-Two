import java.awt.Color;
import java.awt.Font;

/**
 * A concrete strategy in the 'strategy pattern'
 *  Provides a "dark theme" for the tic tac toe board
 * 
 * @author Diljot Singh, Dominic Scalero, Xiyu Chen
 *
 */

public class DarkStyle implements GameBoardFormatter {

	// Board color (background) for dark style is black
	public Color formatBoardColor() {
		return Color.BLACK;
	}

	// Text font for squares in dark style
	public Font formatTextFont() {
	    return formatTextFont('S');
	}
	
	// Text font for each component in dark style
	public Font formatTextFont(char component) {
        switch (Character.toString(component)) {
        case ("S"): return new Font("Verdana", Font.BOLD, 60); //Font for board buttons (squares)
        case ("U"): return new Font("Verdana", Font.BOLD, 20); //Font for Undo Button
        case ("T"): return new Font("Verdana", Font.BOLD, 20); //Font for TextFields
        }
        return null;
    }

	// Text color (foreground) alternates depending on the marker
	public Color formatTextColor(char mark) {

		Color color = null;
		
		// If the current marker is 'X', then the text color will be orange
		if (mark == 'X') {
			color = Color.ORANGE;
		}

		// Otherwise if the current marker was 'O', then the text color will be white
		else if (mark == 'O') {
			color = Color.WHITE;
		}

		return color; // Returns the appropriate marker color
	}

}
