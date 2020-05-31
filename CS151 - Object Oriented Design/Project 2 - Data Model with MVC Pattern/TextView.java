import java.awt.Component;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * A text view to represent the data in DataModel; this is an Observer 
 * Displays each Integer in a JTextField component that can be edited 
 * 
 * @author Diljot Singh
 *
 */
public class TextView extends Box implements Observer {

	private DataModel model; // The model to observe

	/**
	 * Constructs a TextView with the data model to observe
	 * 
	 * @param model the list of integers
	 */
	public TextView(DataModel model) {
		super(BoxLayout.Y_AXIS); //Sets the format of this BoxLayout to be vertical
		this.model = model;
	}

	/**
	 * Whenever Model's list of data changes, update the view by displaying each new
	 * Integer in the corresponding JTextField
	 */
	public void update(Observable obs, Object o) {

		// Ensure that the components exist before updating them (avoids a
		// dividing by zero error where you're trying to access something that doesn't exist)
		if (model.getModel().size() <= this.getComponentCount()) {

			for (Component c : this.getComponents()) { // Loop through all the components in this TextView

				if (c instanceof JTextField) { // If the component is a JTextField, then we will update the text it
												// displays if possible

					// Each index in the ArrayList (0, 1, 2, 3, ...) corresponds to a JTextField
					// Component
					// By dividing each JTextField's Y coordinate by its height, we get the index of
					// the corresponding element in the model
					//Example: index 0 in model corresponds to JTextField 0/20 = 0
					//		   index 1 in model corresponds to JTextField 20/20 = 1
					//		   index 2 in model corresponds to JTextField 40/20 = 2
					// 		   ..etc
					
				
					int index = c.getY() / c.getHeight(); //calculate the index
					Integer value = model.getModel().get(index); //Get the current element's value in the model


					// If what the JTextField is currently displaying does not match the
					// corresponding value in the Model, we update it to display the new value
					if (!((JTextField) c).getText().equals("" + value)) {
						((JTextField) c).setText("" + value);
					}

				}

			}
		}

	}

}
