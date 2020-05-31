import java.awt.Color;
import java.awt.Font;

/**
 * A concrete strategy in the 'strategy pattern' Provides a "light theme" for
 * the tic tac toe board
 * 
 * @author Diljot Singh, Dominic Scalero, Xiyu Chen
 *
 */

public class LightStyle implements GameBoardFormatter {

	// Board color (background) for light style is white
	public Color formatBoardColor() {
		return Color.WHITE;
	}

	// Text font for squares in light style
	public Font formatTextFont() {
	    return formatTextFont('S');
	}
	
	// Text font for each component in light style
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
		// If the current marker is 'X', then the text color will be black

		if (mark == 'X') {
			color = Color.BLACK;
		}

		// Otherwise if the current marker is 'O', then the text color will be red
		else if (mark == 'O') {
			color = Color.RED;
		}

		return color; // Returns the appropriate marker color
	}

}
