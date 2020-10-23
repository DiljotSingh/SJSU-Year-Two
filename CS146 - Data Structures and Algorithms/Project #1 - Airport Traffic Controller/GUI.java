
import java.awt.*;
import javax.swing.*;


/**
 * Simulates an Airport Traffic Control Tower (ATC) through a GUI
 * @author Diljot Singh
 *
 */
public class GUI {

	public static void main(String[] args) {
	
		ATCTester simulator = new ATCTester(); //Create a new ATC object
		simulator.initializeDefaultPlanes(); //Initialize the System (create 30 random Airplanes)

		JFrame frame = new JFrame(); //Frame to store all components
		
		frame.setPreferredSize(new Dimension(1000,700)); //Sets the preferred size of the frame 
		
		JPanel buttonsPanel = new JPanel(); // Buttons panel to store button components
 
		JPanel planesPanel = new JPanel(); //Planes panel to store the text area where we will print the planes

		JTextArea planeTextArea = new JTextArea(35, 35); //Display for all Airplanes in the System
		planeTextArea.setEditable(false); // Sets the text area to be read-only (user cannot edit directly)
		
		
		/**
		 * Button to allow users to view all planes
		 */
		JButton viewPlanesButton = new JButton("VIEW ALL PLANES");
		viewPlanesButton.setBackground(Color.PINK); //sets button color to pink
		viewPlanesButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				planeTextArea.setText(""); // Resets text area to avoid overlap
				int landingNumber = 1; //Position of plane to land (rank)
				// We print our sorted list backward to display planes from highest AC to lowest AC
				for (int i =  simulator.sortedListOfAirplanes.size() - 1; i >= 0; i--) {
					
					//Prints an Airplane object onto the text area on each line
					planeTextArea.append(landingNumber + ". " + simulator.sortedListOfAirplanes.get(i).toString() + "\n");
					landingNumber++;
				}
			}
		});
		
		
		/**
		 * Button to allow users to add an Airplane into the System
		 */
		JButton addPlaneButton = new JButton("ADD PLANE");
		addPlaneButton.setBackground(Color.CYAN); //sets button color to cyan
		addPlaneButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				//Asks for and stores the user's desired flight number
				String flightNumber = JOptionPane.showInputDialog(frame, "What is the flight number?");
				if (flightNumber != null && flightNumber.length() > 0) { // Validates input
					
					// Creates the plane based on the entered flight number
					Airplane plane = new Airplane(flightNumber.toUpperCase());

					//Adds the plane into the sorted list
					simulator.sortedListOfAirplanes.add(plane);
					
					//Adds the plane into the priority queue using maxHeapInsert
					simulator.heapPriorityQueue.maxHeapInsert(plane);

					// Ensures sorted list remains sorted
					HeapSort.heapSort(simulator.sortedListOfAirplanes);

					// Show confirmation and updates text area
					JOptionPane.showMessageDialog(frame, "Added airplane: " + flightNumber.toUpperCase());
					viewPlanesButton.doClick(); //automatically displays newest list
				}
			}
		});

		/**
		 * Button to allow users to declare an emergency landing for an Airplane
		 */
		JButton emergencyLandingButton = new JButton("EMERGENCY LANDING");
		emergencyLandingButton.setBackground(Color.RED);
		emergencyLandingButton.setForeground(Color.WHITE);
		emergencyLandingButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				//Asks for and stores the user's desired flight number
				String flightNumber = JOptionPane.showInputDialog(frame,
						"Enter the flight number of the plane for the emergency landing");
				if (flightNumber != null && flightNumber.length() > 0) {

					int index = -1; //Stores the index of the Airplane
					boolean found = false; //True if the entered flightNumber exists

					// Searches for the plane's index to use heapIncreaseKey on
					for (Airplane a : simulator.heapPriorityQueue.getPriorityQueue()) {
						index++;
						if (a.getFlightNumber().equalsIgnoreCase(flightNumber)) {
							found = true; // This flightNumber exists in the System (valid)
							break;
						}

					}

					//If the flight number was not found, we inform the user
					if (!found) {
						JOptionPane.showMessageDialog(frame,
								"Plane: " + flightNumber.toUpperCase() + " does not exist.");
					} 
					
					//Otherwise we can continue
					else {
						//Asks for and stores the new approach code for the Airplane
						String newApproachCode = JOptionPane.showInputDialog(frame, "Enter the new approach code (maximum 15000)");
						if (newApproachCode != null) {
							
							//Uses heapIncreaseKey to increase the priority of the Airplane
							simulator.heapPriorityQueue.heapIncreaseKey(index, Integer.parseInt(newApproachCode)); 
							
							HeapSort.heapSort(simulator.sortedListOfAirplanes); // Resorts the list of planes (since AC has changed)
							
							// Show confirmation and updates text area
							JOptionPane.showMessageDialog(frame, "Done! The approach code of " + flightNumber.toUpperCase() + " has been set to: " + newApproachCode + " meters");
							viewPlanesButton.doClick(); //automatically displays newest list

						}

					}

				}
			}

		});
		
		/**
		 * Button to allow users to view the first ranked Airplane in the Heap Priority Queue
		 */
		JButton showNextApproachingPlaneButton = new JButton("CHECK QUEUE STATUS");
		showNextApproachingPlaneButton.setBackground(Color.YELLOW); //sets button color to yellow
		showNextApproachingPlaneButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				//Gets the first ranked AC plane using heapExtractMax
				Airplane currentFirst = simulator.heapPriorityQueue.heapExtractMax();
			
				// Also removes that Airplane from our sorted list
				simulator.sortedListOfAirplanes.remove(currentFirst);
				
				// Show confirmation and update text area
				JOptionPane.showMessageDialog(frame, "Next plane: " + currentFirst.toString());
				viewPlanesButton.doClick(); //automatically displays newest list
			}
		});
		
	
		/**
		 * Button to allow users to undo an emergency declaration for an Airplane (restore AC to normal)
		 */
		JButton undoEmergencyButton = new JButton("UNDO EMERGENCY LANDING");
		undoEmergencyButton.setBackground(Color.GREEN); //sets button color to green
		undoEmergencyButton.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				//Asks for and stores the flight number
				String flightNumber = JOptionPane.showInputDialog(frame,
						"Enter the flight number of the plane that no longer requires an emergency landing");
				if (flightNumber != null && flightNumber.length() > 0) {

					int index = -1; //Stores the index of the Airplane object that corresponds to the flight number
					boolean found = false; //True if the flightNumber exists

					// Searches for the plane's index
					for (Airplane a : simulator.heapPriorityQueue.getPriorityQueue()) {
						index++;
						if (a.getFlightNumber().equalsIgnoreCase(flightNumber)) {
							found = true; // This flightNumber exists
							break;
						}

					}

					//If the flight number did not exist, inform the user
					if (!found) {
						JOptionPane.showMessageDialog(frame,
								"Plane: " + flightNumber.toUpperCase() + " does not exist.");
					} 
					
					//Otherwise we can restore the Airplane's AC
					else
					{
						Airplane plane = simulator.heapPriorityQueue.getPriorityQueue().get(index); //Gets the corresponding Airplane object
						
						simulator.heapPriorityQueue.getPriorityQueue().remove(plane); //Removes the Airplane from the heap priority queue (we will reinsert it to correct position)
						
						plane.setApproachCode(plane.calculateApproachCode()); //Recalculates and sets the approach code based off its distance and elevation
						
						simulator.heapPriorityQueue.maxHeapInsert(plane); //Reinserts the plane into the priority queue in the correct position
						
						HeapSort.heapSort(simulator.sortedListOfAirplanes); // Resorts the list of planes
						
						//Show confirmation and update text area
						JOptionPane.showMessageDialog(frame, "Done! The approach code of " + flightNumber.toUpperCase() + " has been restored to: " + plane.getApproachCode() + " meters");
						viewPlanesButton.doClick();
						
					}
				
			}
			}
		});
		

		// <------------Standard closing operations for frame---------------->
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
		//Adds the panel to the frame and the buttons to the panel
		frame.add(buttonsPanel);
		buttonsPanel.add(addPlaneButton); //addPlane button
		buttonsPanel.add(emergencyLandingButton); //emergencyLanding Button
		buttonsPanel.add(showNextApproachingPlaneButton); //nextApproachingPlane button
		buttonsPanel.add(undoEmergencyButton); //undo emergency button
		buttonsPanel.add(viewPlanesButton); //View all planes button
		
		//Adds the panel to the frame and the text area to the panel
		frame.add(planesPanel);
		planesPanel.add(planeTextArea);
		planesPanel.add(Box.createHorizontalStrut(10)); //10 pixel gap between components
		
		//Adds an image of a control tower to the panel
		ImageIcon ctrlTowerIcon = new ImageIcon("C:\\Users\\dsing\\Documents\\SJSU\\CS146\\ATC_Simulator_PA1\\ATC.png");
		Image towerImage = ctrlTowerIcon.getImage().getScaledInstance(300,500, java.awt.Image.SCALE_SMOOTH);
		ctrlTowerIcon = new ImageIcon(towerImage);
		planesPanel.add(new JLabel(ctrlTowerIcon));

		frame.setVisible(true);
		frame.setTitle("Airport Control Tower - Diljot Singh");

	}

}
