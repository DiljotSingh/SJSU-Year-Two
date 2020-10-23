import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Simulates the microversion of Facebook through a GUI
 * 
 * @author Diljot Singh
 *
 */
public class GUI {

	// Main method for GUI
	public static void main(String[] args) {

		// <--------------------------------------SET UP------------------------->
		FacebookTester simulator = new FacebookTester(); // Creates the Facebook tester
		FacebookTester.intitializeSystem(simulator); // Initializes the system

		JFrame frame = new JFrame(); // Frame to store all components
		frame.setPreferredSize(new Dimension(1000, 700)); // Sets the preferred size of the frame

		// <----------------------------------------PANELS----------------------------->
		// Creates the facebook bar (bar at top)
		JPanel searchBar = new JPanel(new FlowLayout());
		searchBar.setPreferredSize(new Dimension(1000, 50));
		searchBar.setBackground(new Color(59, 89, 182));

		// Adds an image of the FB logo to the Facebook bar
		ImageIcon fbIcon = new ImageIcon("C:\\Users\\dsing\\Documents\\SJSU\\CS146\\PA3\\facebook_logo.png");
		Image fbImage = fbIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		fbIcon = new ImageIcon(fbImage);

		// Creates the label with the FB icon and adds it to the search bar jpanel
		JLabel fbLabel = new JLabel(fbIcon);
		fbLabel.setBounds(0, 0, 50, 50);
		searchBar.add(fbLabel);

		// Panel for where we will print the names and user actions
		JPanel tablePanel = new JPanel(); // Panel to store the text area where we will print the names
		JTextArea nameTextArea = new JTextArea(50, 55); // Display for all people in the System
		nameTextArea.setEditable(false); // Sets the text area to be read-only (user cannot edit directly)
		tablePanel.add(nameTextArea); // Adds the text area to the panel

		// Panel for all the buttons to allow users to take actions
		JPanel buttonsPanel = new JPanel(new FlowLayout());

		// <---------------------------------BUTTONS------------------------------->
		// Button to print the hash table
		JButton printTableButton = new JButton("Print HashTable");
		printTableButton.addActionListener(new ActionListener() {

			// Prints the hash table to the text area when clicked
			public void actionPerformed(ActionEvent e) {
				nameTextArea.setText(""); // resets the text area
				nameTextArea.append("HASHTABLE: " + "\n");
				nameTextArea.setFont(new Font("Arial", Font.BOLD, 15));
				nameTextArea.append(simulator.facebookDataBase.toString() + "\n"); // Prints the table contents

			}
		});

		// Button to create a new Person in the hash table
		JButton createPersonButton = new JButton("Create Person");
		createPersonButton.addActionListener(new ActionListener() {

			// Asks the user for an input name then creates the Person when clicked
			public void actionPerformed(ActionEvent e) {
				// Asks for and stores the user's desired flight number
				String newPersonName = JOptionPane.showInputDialog(frame, "Please enter the name of the new user:");

				if (newPersonName != null && newPersonName.length() > 0) { // Validates input

					// Creates a new Person with the specified name
					Person newPerson = new Person(newPersonName, new ArrayList<Person>());
					simulator.facebookDataBase.chainedHashInsert(newPerson); // Inserts the new person into the table

					// Show confirmation and updates text area
					JOptionPane.showMessageDialog(frame,
							"Done! " + newPerson.getName() + " has been added to the Hash Table.");
					printTableButton.doClick(); // automatically displays newest list
				}
			}
		});

		// Button to make two people friends
		JButton addFriendButton = new JButton("Add Friend");
		addFriendButton.addActionListener(new ActionListener() {

			// Asks the user for two names then makes those two people friends
			public void actionPerformed(ActionEvent e) {

				String person1 = JOptionPane.showInputDialog(frame,
						"Please enter the name of the Person to add a friend for: ");

				// Validates input
				if (person1 != null && person1.length() > 0) {
					Person firstPerson = simulator.facebookDataBase.getPerson(person1); // Gets that person

					// If the person does not exist, we insert them into the table
					if (firstPerson == null || (!simulator.facebookDataBase.chainedHashSearch(firstPerson))) {
						simulator.facebookDataBase.chainedHashInsert(new Person(person1, new ArrayList<Person>()));
					}

					String person2 = JOptionPane.showInputDialog(frame,
							"Please enter the name of the Person to send a friend request: ");
					// Validates input
					if (person2 != null && person2.length() > 0) {
						Person secondPerson = simulator.facebookDataBase.getPerson(person2); // Gets that person
						// If the person does not exist, we insert them into the table
						if (secondPerson == null || (!simulator.facebookDataBase.chainedHashSearch(secondPerson))) {
							simulator.facebookDataBase.chainedHashInsert(new Person(person2, new ArrayList<Person>()));
						}

						// Gets the two specified people from the table using hashing
						firstPerson = simulator.facebookDataBase.getPerson(person1);
						secondPerson = simulator.facebookDataBase.getPerson(person2);

						// The two people are now friends with eachother
						firstPerson.addFriend(secondPerson);

						// Show confirmation
						JOptionPane.showMessageDialog(frame, "Done! " + firstPerson.getName() + " and "
								+ secondPerson.getName() + " are now friends!");

						printTableButton.doClick(); // prints the table
					}

				}

			}
		});

		// Button to unfriend two people
		JButton unfriendButton = new JButton("Unfriend");
		unfriendButton.addActionListener(new ActionListener() {

			// Asks the user for two names and unfriends those two people
			public void actionPerformed(ActionEvent e) {

				// Asks for first name
				String person1 = JOptionPane.showInputDialog(frame,
						"Please enter the name of the Person to remove a friend for: ");
				// Validates input
				if (person1 != null && person1.length() > 0) {
					Person firstPerson = simulator.facebookDataBase.getPerson(person1); // Gets that person

					// If the person does not exist, indicate so
					if (firstPerson == null || !(simulator.facebookDataBase.chainedHashSearch(firstPerson))) {

						// Show message indicating they do not exist
						JOptionPane.showMessageDialog(frame, person1 + " does not exist.");
					}
					// Otherwise ask for second name
					else {
						String person2 = JOptionPane.showInputDialog(frame,
								"Please enter the name of the Person to unfriend: ");
						// Validates input
						if (person2 != null && person2.length() > 0) {
							Person secondPerson = simulator.facebookDataBase.getPerson(person2); // Gets that person
							// If the person does not exist, indicate so
							if (secondPerson == null || !(simulator.facebookDataBase.chainedHashSearch(secondPerson))) {

								// Show message indicating they do not exist
								JOptionPane.showMessageDialog(frame, person2 + " does not exist.");
							}

							// Otherwise we unfriend the two people
							else {
								firstPerson.removeFriend(secondPerson);
								// Note: We can also delete the object from the entire table if we choose (not
								// chosen in this design):
								// facebookDataBase.chainedHashDelete(secondPerson);

								// Show message indicating these two people are no longer friends
								JOptionPane.showMessageDialog(frame,
										person1 + " and " + person2 + " are no longer friends.");
							}

						}
					}

				}

			}
		});

		// Button to search and print Person's friend list (unsorted)
		JButton searchFriendListButton = new JButton("Search Friend List");
		searchFriendListButton.addActionListener(new ActionListener() {

			// Prints a Person's friend list when clicked
			public void actionPerformed(ActionEvent e) {
				// Asks for first name
				String person = JOptionPane.showInputDialog(frame, "Please enter the name of the Person: ");
				// Validates input
				if (person != null && person.length() > 0) {
					Person firstPerson = simulator.facebookDataBase.getPerson(person); // Gets that person

					// Prints that person's friend list, if they exist
					if (firstPerson != null && simulator.facebookDataBase.chainedHashSearch(firstPerson)) {

						// Show message indicating the friend list
						JOptionPane.showMessageDialog(frame,
								person + "'s friends are: " + (firstPerson.getFriends().toString()));
					}
				}
			}
		});

		// Button to check if two people are mutual friends
		JButton checkMutualFriendsButton = new JButton("Check If Mutual Friends");
		checkMutualFriendsButton.addActionListener(new ActionListener() {

			// Indicates whether two people are friends when clicked
			public void actionPerformed(ActionEvent e) {

				// Asks for first person's name
				String person1 = JOptionPane.showInputDialog(frame, "Please enter the name of the first Person: ");

				// Validates input
				if (person1 != null && person1.length() > 0) {

					// Checks to see if the first person exists
					Person firstPerson = simulator.facebookDataBase.getPerson(person1);
					if (firstPerson == null || !(simulator.facebookDataBase.chainedHashSearch(firstPerson))) {
						// Show message indicating they do not exist
						JOptionPane.showMessageDialog(frame, person1 + " does not exist.");
					}
					// If they do exist, we ask for the second name
					else {

						String person2 = JOptionPane.showInputDialog(frame,
								"Please enter the name of the second Person: ");
						// Validates input
						if (person2 != null && person2.length() > 0) {

							// Checks to see if the second person exists
							Person secondPerson = simulator.facebookDataBase.getPerson(person2);
							if (secondPerson == null || !(simulator.facebookDataBase.chainedHashSearch(secondPerson))) {
								// Show message indicating they do not exist
								JOptionPane.showMessageDialog(frame, person2 + " does not exist.");
							}

							// Otherwise if both people exist in the table, check their friendship staus
							else {

								// If they are friends, indicate so
								if (firstPerson.hasFriend(secondPerson)) {
									// Show message indicating they are friends
									JOptionPane.showMessageDialog(frame,
											"Yes, " + person1 + " and " + person2 + " are friends.");

								}

								// Otherwise show message indicating they are not friends
								else {
									JOptionPane.showMessageDialog(frame,
											person1 + " and " + person2 + " are not friends.");
								}
							}

						}

					}
				}

			}
		});

		// Button to view a Person's friend list in sorted order
		JButton viewSortedFriendsButton = new JButton("View Sorted Friend List");
		viewSortedFriendsButton.addActionListener(new ActionListener() {

			// Prints the specified Person's list of friends in sorted order when clicked
			public void actionPerformed(ActionEvent e) {

				// Asks for first person's name
				String person = JOptionPane.showInputDialog(frame, "Please enter the name of the Person: ");

				// Validates input
				if (person != null && person.length() > 0) {

					// Checks to see if the first person exists
					Person specifiedPerson = simulator.facebookDataBase.getPerson(person);

					// If they do not exist, we indicate so
					if (specifiedPerson == null || !(simulator.facebookDataBase.chainedHashSearch(specifiedPerson))) {
						// Show message indicating they do not exist
						JOptionPane.showMessageDialog(frame, person + " does not exist.");
					}

					// Otherwise we can start sorting and print the list
					else {
						BinarySearchTree tree = new BinarySearchTree(); // Create a BST to help print the sorted list
						// Inserts all those person's friends into the BST
						for (Person friend : specifiedPerson.getFriends()) {
							tree.add(friend.getName());
						}
						// Show the list of friends in alphabetical order by calling the tree's inorder
						// traversal method
						JOptionPane.showMessageDialog(frame,
								person + "'s friends (in alphabetical order) are: " + tree.printGUI());
					}
				}

			}
		});

		// Adds all the buttons to the buttons panel
		buttonsPanel.add(printTableButton);
		buttonsPanel.add(createPersonButton);
		buttonsPanel.add(addFriendButton);
		buttonsPanel.add(unfriendButton);
		buttonsPanel.add(searchFriendListButton);
		buttonsPanel.add(checkMutualFriendsButton);
		buttonsPanel.add(viewSortedFriendsButton);

		// <------------Standard closing operations for frame---------------->
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		// Add the panels to the frame
		frame.add(searchBar);
		frame.add(buttonsPanel);
		frame.add(tablePanel);

		frame.setTitle("Facebook Simulation - Diljot Singh");
	}

}
