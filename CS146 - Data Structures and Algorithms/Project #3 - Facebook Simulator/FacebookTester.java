import java.util.*;

/**
 * Simulates a microversion of Facebook through the CONSOLE 
 * Tests the implementation of the HashTable and hashing functions
 * 
 * @author Diljot Singh
 *
 */
public class FacebookTester {

	public HashTable facebookDataBase; // The hash table to store all users in
	public Scanner scan; // Scanner used for user inputs and actions
	public Random random; // Random object used to initialize default friendships

	/**
	 * Constructs a FacebookTester, initializing the hashtable database, the scanner
	 * for parsing user inputs, and the Random object for initializing the default
	 * list friendships randomly
	 */
	public FacebookTester() {

		facebookDataBase = new HashTable();
		scan = new Scanner(System.in);
		random = new Random();
	}

	/**
	 * Displays the appropriate actions the User can take, and the responses that
	 * will occur
	 */
	public static void optionsMenu() {
		System.out.println("Options: ");
		System.out.println("	• Enter 'C' to create a new Person."); // Allow users to create a Person
		System.out.println("	• Enter 'A' to add a friend."); // Allow users to record a Person as a new friend
		System.out.println("	• Enter 'U' to unfriend someone."); // Allow users to remove a Person from their friend
																	// list
		System.out.println("	• Enter 'S' to search a Person to list their friend list."); // Search a Person list
																								// his/her friend list
		System.out.println("	• Enter 'F' to check if two people are friends."); // Enter two person's names to check
																					// if they are friends
		System.out.println("	• Enter 'V' to view a sorted list of a Person's friend list."); // Use BST to sort and
																								// list a Person's
																								// friends in
																								// alphabetical order
		System.out.println("	• Enter 'P' to print all the users (prints the hashtable)."); // Prints the entire table
																								// with all the Person
																								// objects
		System.out.println("	• Enter 'X' to exit."); // Allow users to exit the program
		System.out.print("Your input: ");

	}

	/**
	 * Allows users to create a new Person in the hash table using chainedHashInsert
	 */
	public void createNewPersonPrompt() {
		System.out.println();

		System.out.print("Please enter the name of the new user: ");
		String name = scan.next(); // Stores the user name input

		// Creates a new Person with the specified name
		Person newPerson = new Person(name, new ArrayList<Person>());
		facebookDataBase.chainedHashInsert(newPerson); // Inserts the new person into the table
		System.out.print("Added " + newPerson.getName() + " to the hash table.");

		System.out.println();
		System.out.println(facebookDataBase.toString());
		optionsMenu(); // Displays options menu
	}

	/**
	 * Allows users to make two people friends
	 */
	public void addFriendPrompt() {
		System.out.println();

		// Gets the person to add a friend for
		System.out.print("Please enter the name of the Person to add a friend for: ");
		String firstPersonName = scan.next();
		Person firstPerson = facebookDataBase.getPerson(firstPersonName);

		// If the person does not exist, we insert them into the table
		if (firstPerson == null || (!facebookDataBase.chainedHashSearch(firstPerson))) {
			facebookDataBase.chainedHashInsert(new Person(firstPersonName, new ArrayList<Person>()));
		}

		// Gets the person to send a friend request to
		System.out.println();
		System.out.print("Please enter the name of the Person to send a friend request: ");
		String secondPersonName = scan.next();
		Person secondPerson = facebookDataBase.getPerson(secondPersonName);

		// If the person does not exist, we insert them into the table
		if (secondPerson == null || (!facebookDataBase.chainedHashSearch(secondPerson))) {
			facebookDataBase.chainedHashInsert(new Person(secondPersonName, new ArrayList<Person>()));
		}

		// Gets the two specified people from the table using hashing
		firstPerson = facebookDataBase.getPerson(firstPersonName);
		secondPerson = facebookDataBase.getPerson(secondPersonName);

		// The two people are now friends with eachother
		firstPerson.addFriend(secondPerson);
		System.out.println(firstPerson.getName() + " and " + secondPerson.getName() + " are now friends!");

		System.out.println();
		optionsMenu(); // Display options menu

	}

	/**
	 * Allows users to unfriend two people
	 */
	public void unfriendPrompt() {
		System.out.println();

		// Gets the person to remove the friend for (uses hashing)
		System.out.print("Please enter the name of the Person to remove the friend from: ");
		String firstPersonName = scan.next();
		Person firstPerson = facebookDataBase.getPerson(firstPersonName);

		// If the person does not exist, we indicate so
		if (firstPerson == null || !facebookDataBase.chainedHashSearch(firstPerson)) {
			System.out.println("That person does not exist.");
			return;
		}

		// Gets the person to remove (uses hashing)
		System.out.println();
		System.out.print("Please enter the name of the Person to remove: ");
		String secondPersonName = scan.next();
		Person secondPerson = facebookDataBase.getPerson(secondPersonName); //Gets that Person using its name as a hash code

		// If the person does not exist, we indicate so
		if (secondPerson == null || !facebookDataBase.chainedHashSearch(secondPerson)) {
			System.out.println("That person does not exist.");
			return;
		}

		// Otherwise we remove the people as friends (mutual)
		firstPerson.removeFriend(secondPerson); // The two people are now unfriended from eachother

		// Note: We can also delete the object from the ENTIRE table if we choose (not
		// chosen in this design):
		// facebookDataBase.chainedHashDelete(secondPerson);

		System.out.println(firstPerson.getName() + " and " + secondPerson.getName() + " are no longer friends.");

		System.out.println();
		optionsMenu(); // Display options menu

	}

	/**
	 * Allows users to search a Person to list their friends
	 */
	public void searchPersonFriendListPrompt() {
		System.out.println();

		// Gets the person to list friends for
		System.out.print("Please enter the name of the Person: ");
		String firstPersonName = scan.next();
		Person firstPerson = facebookDataBase.getPerson(firstPersonName);

		// Prints that person's friend list, if they exist
		if (firstPerson != null && (facebookDataBase.chainedHashSearch(firstPerson))) {
			System.out.println(firstPerson.getFriends().toString());
		}

		System.out.println();
		optionsMenu(); // Display options menu

	}

	/**
	 * Allows users to check if two people are friends
	 */
	public void checkIfMutualFriendsPrompt() {
		System.out.println();

		// Gets the first person
		System.out.print("Please enter the name of the first Person: ");
		String firstPersonName = scan.next();
		Person firstPerson = facebookDataBase.getPerson(firstPersonName);

		// Gets the second person
		System.out.println();
		System.out.print("Please enter the name of the second Person: ");
		String secondPersonName = scan.next();
		Person secondPerson = facebookDataBase.getPerson(secondPersonName);

		// Display whether or not these two people are friends
		if (firstPerson != null && secondPerson != null) {
			// Yes they are friends, print the confirmation
			if (firstPerson.hasFriend(secondPerson)) {
				System.out
						.println("Yes, " + firstPerson.getName() + " and " + secondPerson.getName() + " are friends.");
			}
			// No these two people are not friends
			else {
				System.out.println(
						"No, " + firstPerson.getName() + " and " + secondPerson.getName() + " are not friends.");
			}
		}

		System.out.println();
		optionsMenu(); // Display options menu

	}

	/**
	 * Allow users to view a Person's friend list in alphabetical order
	 */
	public void viewSortedListOfFriendsPrompt() {
		System.out.println();

		// Gets the person to list the sorted list of friends for
		System.out.print("Please enter the name of the Person: ");
		String firstPersonName = scan.next();
		Person firstPerson = facebookDataBase.getPerson(firstPersonName);

		// If the person exists, we create a BST and then do an inorder walk to print
		// their friends
		if (firstPerson != null) {

			// Create a BST and add all the friends of the Person to it
			BinarySearchTree tree = new BinarySearchTree();
			for (Person friend : firstPerson.getFriends()) {
				tree.add(friend.getName());
			}

			// Prints the sorted list of the friends
			System.out.print(firstPerson.getName() + "'s friends (in alphabetical order) are: ");
			tree.print();
		}

		System.out.println();
		optionsMenu(); // Display the options menu

	}

	/**
	 * Closes the System (stops prompting)
	 */
	public void exitPrompt() {
		System.out.println("System exited.");
		scan.close();
	}

	/**
	 * Initializes the tester by inserting the 50 default names, making friendships,
	 * and printing the hash table
	 */
	public static void intitializeSystem(FacebookTester tester) {
		// At least 50 users must be stored in the table using hash function
		String[] testArray = getTestNames(); // Gets 50 default names

		// Insert default 50 names into the table using chainedHashInsert() and assigns
		// friends randomly to initialize
		int counter = 1; // Counter used for randomly adding friends to the initial list
		for (String name : testArray) {

			// ChainHashInsert each default name into the hash table
			tester.facebookDataBase.chainedHashInsert(new Person(name, new ArrayList<Person>()));

			// Gives this Person at least two friends from the table
			int randomIndex1 = tester.random.nextInt(counter); // Generates a random index based on how many people have
																// added already
			int randomIndex2 = tester.random.nextInt(counter); // Generates a random index based on how many people have
																// been added already

			tester.facebookDataBase.getPerson(name)
					.addFriend(tester.facebookDataBase.getPerson(testArray[randomIndex1])); // Adds a friend for this
																							// Person
			tester.facebookDataBase.getPerson(name)
					.addFriend(tester.facebookDataBase.getPerson(testArray[randomIndex2])); // Adds another friend for
																							// this Person

			counter++; // Increments the counter for the random index generator
		}

	}

	/**
	 * Returns an array with 50 default names added
	 * 
	 */
	public static String[] getTestNames() {

		String[] defaultArray = new String[] { "Liam", "Emma", "Noah", "Olivia", "William", "Ava", "James", "Isabella",
				"Logan", "Sophia", "Benjamin", "Mia", "Mason", "Charlotte", "Elijah", "Amelia", "Oliver", "Evelyn",
				"Jacob", "Abigail", "Lucas", "Harper", "Michael", "Emily", "Alexander", "Elizabeth", "Ethan", "Avery",
				"Daniel", "Sofia", "Matthew", "Ella", "Aiden", "Madison", "Henry", "Scarlett", "Joseph", "Victoria",
				"Jackson", "Aria", "Samuel", "Grace", "Sebastian", "Chloe", "David", "Camila", "Carter", "Penelope",
				"Wyatt", "Riley" };
		return defaultArray;

	}

	/**
	 * Main method (Facebook Simulator) for User to run commands through the console
	 * Creates a hash table and inserts 50 people to it by default, then allows
	 * users to add/remove additional people
	 */
	public static void main(String[] args) {
		FacebookTester tester = new FacebookTester(); // Creates a new FaceBookTester
		intitializeSystem(tester); // Initialize the tester with the 50 default names

		// Print the hash table
		System.out.println("Hashtable: " + "\n" + tester.facebookDataBase.toString());

		// Display the initial prompt
		optionsMenu();

		// Keep prompting for user actions and executing appropriate responses until
		// System exits
		while (tester.scan.hasNext()) {
			String input = tester.scan.next();

			// User wants to exit
			if (input.equalsIgnoreCase("X")) {
				tester.exitPrompt(); // Call the exit prompt
				break; // Stop prompting
			}

			// User wants to create a new Person
			else if (input.equalsIgnoreCase("C")) {
				tester.createNewPersonPrompt();

			}
			// User wants to add a friend
			else if (input.equalsIgnoreCase("A")) {
				tester.addFriendPrompt();

			}
			// User wants to unfriend someone
			else if (input.equalsIgnoreCase("U")) {
				tester.unfriendPrompt();
			}
			// User wants to search a person's friend list
			else if (input.equalsIgnoreCase("S")) {
				tester.searchPersonFriendListPrompt();
			}
			// User wants to check if two people are friends
			else if (input.equalsIgnoreCase("F")) {
				tester.checkIfMutualFriendsPrompt();
			}
			// User wants to view a sorted list of a Person's friends
			else if (input.equalsIgnoreCase("V")) {
				tester.viewSortedListOfFriendsPrompt();
			}

			// Prints the entire Hash Table
			else if (input.equalsIgnoreCase("P")) {
				System.out.println(tester.facebookDataBase.toString());
				optionsMenu();
			}
		}
	}

}
