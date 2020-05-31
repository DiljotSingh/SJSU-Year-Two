import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 * A graphical view to represent the data in DataModel; this is an Observer
 * Depicts each integer as a horizontal bar with a length corresponding to the
 * integer's value
 * 
 * @author Diljot Singh
 *
 */
public class GraphView extends JPanel implements Observer {

	private static final int HEIGHT = 20; // Each bar's height is 20 pixels
	private DataModel model; // The model to observe

	/**
	 * Constructs a GraphView with the data model to observe
	 * 
	 * @param model the list of integers
	 */
	public GraphView(DataModel model) {
		this.model = model;
	}

	/**
	 * Paints all the Integers in the DataModel as rectangular bars
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		int x = 0; // X coordinate of the rectangle
		int y = 0; // Y coordinate of the rectangle

		// Loop through all the Integers in the model's list
		for (Integer value : model.getModel()) {
			// Create and draw a Rectangle (integer value is the width of the bar and constant HEIGHT is the
			// height)
			Rectangle r = new Rectangle(x, y, value, HEIGHT);
			g2.draw(r);

			// Increments the Y coordinate by the height so each rectangle is drawn below
			// the previous
			y += HEIGHT;
		}
	}

	/**
	 * Whenever Model's list of data changes, update the view by repainting the
	 * Rectangles
	 */
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	
	}

}
