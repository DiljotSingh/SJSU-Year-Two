import java.awt.event.*;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Tests the MVC relationship of DataModel, GraphView, and TextView 
 * The DataModel is constructed by reading Integers stored in a File given in
 * args[0] and adding them to the model
 * Changes made to the integers are automatically written back into the file upon exit
 * 
 * @author Diljot Singh
 *
 */
public class Hw2P2 {

	public static void main(String[] args) throws Exception {
		File f = new File(args[0]); //Constructs the file from the name given in args[0]
		BufferedReader br = new BufferedReader(new FileReader(f)); // Create a new buffered reader

		DataModel model = new DataModel(); // Constructs a new DataModel to observe
		TextView textView = new TextView(model); // Constructs a new TextView
		GraphView graphView = new GraphView(model); // Constructs a new GraphView

		model.addObserver(textView); // Adds the TextView to the model as an observer
		model.addObserver(graphView); // Adds the GraphView to the model as an observer

		// Frame for TextView
		JFrame textFrame = new JFrame();
		textFrame.setContentPane(textView);

		// Frame for GraphView
		JFrame graphFrame = new JFrame();
		graphFrame.setContentPane(graphView);

		// Reads all the integers from the file and adds them to the model
		String line;
		while ((line = br.readLine()) != null) {
			Integer value = Integer.parseInt(line); // Gets a line from the text file (a number)
			model.addValue(value); // Adds that number to the model and notifies observers of change

			JTextField newField = new JTextField("" + value); // Creates a new text field initialized with that value
			textView.add(newField); // Adds this text field to TextView

			// Track the editing of each text field using a DocumentListener
			newField.getDocument().addDocumentListener(new DocumentListener() {

				//Gets triggered whenever the attributes of the text field change
				public void changedUpdate(DocumentEvent event) {
					
					// The text field's Y coordinate divided by its height corresponds to an index
					// in the Model
					// Example: The second JTextField component will have coordinates (0,20) and
					// height 20.
					// 40 (The Y coordinate) divided by 20 (the height) = 1.
					// Index 1 in the Model's ArrayList of data corresponds to the second element in
					// the list
					int index = newField.getY() / newField.getHeight();

					// The new value entered in the JTextField
					int newValue = Integer.parseInt(newField.getText());

					// Set that element's value in the model to the new value and notifies the
					// observers (views)
					try {
						model.setValue(index, newValue);

					} catch (NumberFormatException exception) {

					}
				}
				
				//Gets triggered whenever something is inserted into the text field (such as typing a digit)
				public void insertUpdate(DocumentEvent event) {
					
					// The text field's Y coordinate divided by its height corresponds to an index
					// in the Model
					// Example: The second JTextField component will have coordinates (0,20) and
					// height 20.
					// 40 (The Y coordinate) divided by 20 (the height) = 1.
					// Index 1 in the Model's ArrayList of data corresponds to the second element in
					// the list
					int index = newField.getY() / newField.getHeight();

					// The new value entered in the JTextField
					int newValue = Integer.parseInt(newField.getText());

					// Set that element's value in the model to the new value and notifies the
					// observers (views)
					try {
						model.setValue(index, newValue);

					} catch (NumberFormatException exception) {

					}

				}
				
				//Gets triggered whenever something is deleted from the text field (such as backspacing a digit)
				public void removeUpdate(DocumentEvent event) {

					// The text field's Y coordinate divided by its height corresponds to an index
					// in the Model
					// Example: The second JTextField component will have coordinates (0,20) and
					// height 20.
					// 20 (The Y coordinate) divided by 20 (the height) = 1.
					// Index 1 in the Model's ArrayList of data corresponds to the second element in
					// the list
					int index = newField.getY() / newField.getHeight();

					// The new value entered in the JTextField
					int newValue;

					// If the user erases the entire JTextField, the value is set to 0
					if (newField.getText().length() == 0) {
						newValue = 0;
					}
					// Otherwise we parse the text as normal
					else {
						newValue = Integer.parseInt(newField.getText());
					}

					// Set that element's value in the model to the new value and notifies the
					// observers (views)
					try {
						model.setValue(index, newValue);

					}
					// IMPORTANT: We must catch an IllegalStateException in removeUpdate() for when
					// a user erases the entire JTextField
					catch (IllegalStateException exception) {

					}

				}

			});
		}

		// Adds a mouse listener to the GraphView to update the model
		// Clicking anywhere next to a bar moves the bar to the mouse position
		graphView.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {

				// Each bar's height is 20 pixels, so we calculate the corresponding integer's
				// index by dividing the Y coordinate of the bar by its height
				int index = event.getY() / 20;
				model.setValue(index, event.getX()); // Updates the value in the model and notifies observers
			}
		});

		//<------------------------Standard closing operations------------->
		textFrame.pack();
		textFrame.setVisible(true);
		textFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		graphFrame.setVisible(true);
		graphFrame.setBounds(200, 0, textFrame.getWidth(), textFrame.getHeight());
		graphFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// When either frame (TextView or GraphView) is closed, saves the edited data in the file
		textFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				try {
					
					//Writes all the values from the model back into the file
					BufferedWriter bw = new BufferedWriter(new FileWriter(f));
					for (Integer value : model.getModel()) {
						bw.write(String.valueOf(value)); //integer is written as a String
						bw.newLine(); //Format is one integer per line
					}
					bw.close();

				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		});

		// When either frame (TextView or GraphView) is closed, saves the edited data in the file
		graphFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				try {
					
					//Writes all the values from the model back into the file
					BufferedWriter bw = new BufferedWriter(new FileWriter(f));
					for (Integer value : model.getModel()) {
						bw.write(String.valueOf(value)); //integer is written as a String
						bw.newLine(); //Format is one integer per line
					}
					bw.close();

				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		});
	}

}
