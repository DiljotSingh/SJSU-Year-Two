import java.util.*;
import java.util.stream.Collectors;

/**
 * Simulates an Airport Control Tower (ATC) through the console
 * Tests the implementation of HeapSort and HeapPriorityQueue
 * @author Diljot Singh
 *
 */
public class ATCTester {

	public  HeapPriorityQueue 		heapPriorityQueue; //The heap priority queue
	public 	Scanner 				scan; //Scanner used for User inputs and actions
	public  Random 					random; //Random object to generate 30 random Airplanes
	public  String 					alphabet; //Used to generate 30 random Airplanes
	public  ArrayList<Airplane> 	sortedListOfAirplanes; //Stores the sorted list of all the Airplanes

	/**
	 * Constructs an ATCTester, initializing all the instance variables
	 */
	public ATCTester() {
		heapPriorityQueue = new HeapPriorityQueue();
		scan = new Scanner(System.in); // Scanner to add User's flight numbers
		random = new Random(); // Random object to generate first 30 random flight numbers
		alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Alphabet to choose random letters from
		sortedListOfAirplanes = new ArrayList<Airplane>(); //Stores the sorted list of all the Airplanes
	}

	
	/**
	 * Displays the appropriate actions the User can take, and the responses that will occur
	 */
	public static void optionsMenu() {
		System.out.println("Options: ");
		System.out.println("	• Enter 'A' to add a plane.");
		System.out.println("	• Enter 'E' for an emergency landing.");
		System.out.println("	• Enter 'Q' to view the next Airplane in the queue to land.");
		System.out.println("	• Enter 'U' to undo an emergency landing.");
		System.out.println("	• Enter 'V' to view all planes.");
		System.out.println("	• Enter 'X' to exit.");
		System.out.print("Your input: ");

	}
	
	
	/**
	 * Creates 30 initial Airplanes to add to the System (randomly created)
	 */
	public void initializeDefaultPlanes()
	{
		for (int i = 0; i < 30; i++) {
			
			// Generates two random letters from the alphabet for the first portion of the
			// flight number
			char char1 = (alphabet.charAt(random.nextInt(alphabet.length())));
			char char2 = (alphabet.charAt(random.nextInt(alphabet.length())));

			// Generates a random number between 1 and 100 for the second portion of the
			// flight number
			int number = 1 + random.nextInt(100);

			// Constructs a new Airplane with the generated flight number
			Airplane newPlane = new Airplane(Character.toString(char1) + Character.toString(char2) + number);

			// Adds the plane to the ArrayList that we will sort
			sortedListOfAirplanes.add(newPlane);

			// Adds the plane into the priority queue (we will build max heap on this)
			heapPriorityQueue.getPriorityQueue().add(newPlane);
		}

		// The default list of airplanes is now sorted
		HeapSort.heapSort(sortedListOfAirplanes);
		
		//Builds a max heap of the priority queue
		HeapSort.buildMaxHeap(heapPriorityQueue.getPriorityQueue());
		

	}

	/**
	 * Allows Users to add an Airplane into the System by entering a flight number
	 */
	public void addPlanePrompt() {
		System.out.println();

		// Prompt for a flight number
		System.out.print("Please enter a flight number: ");
		String flightNumber = scan.next(); //Stores the entered flight number

		// Creates and adds this airplane to the sorted list and the heap priority queue
		Airplane plane = new Airplane(flightNumber.toUpperCase());
		
		sortedListOfAirplanes.add(plane); //Adds the newly constructed plane into the sorted list
		heapPriorityQueue.maxHeapInsert(plane); //Uses maxHeapInsert to add the newly constructed plane into the priority queue

		
		HeapSort.heapSort(sortedListOfAirplanes); // Calls heapsort to make sure list remains sorted

		// Continue prompting
		System.out.println("Added airplane: " + flightNumber.toUpperCase());
		System.out.println();
		optionsMenu();
	}

	/**
	 * Allows Users to choose any Airplane stored in the priority queue to increase its approach code value
	 */
	public void emergencyLandingPrompt() {
		System.out.println();

		System.out.print("Enter the flight number of the plane for the emergency landing: ");
		String flightNumber = scan.next(); //Stores the entered flight number

		System.out.print("Enter the new Approach Code for the plane (maximum 15000): ");
		int newApproachCode = scan.nextInt(); //Stores the entered approach code

		int index = -1; //Stores the index of the plane corresponding to the flight number
		boolean found = false; //False if the flight number was not found
		
		//Searches for the plane's index to use heapIncreaseKey on
		for (Airplane a : heapPriorityQueue.getPriorityQueue()) {
			index++;
			if (a.getFlightNumber().equalsIgnoreCase(flightNumber)) { //If we found a matching flight number, we have the index
				found = true; //This flightNumber exists
				break;
			}

		}
		// If the entered flight number was not found in the priority queue, throw an
		// exception
		if (!found) {
			scan.close();
			throw new IllegalArgumentException("That flight number does not exist.");
		}
		
		// Otherwise we can increase the priority for that Airplane based on the User's entered approach code
		else {
			heapPriorityQueue.heapIncreaseKey(index, newApproachCode); //Use heapIncreaseKey to increase the priority for the specified index
			HeapSort.heapSort(sortedListOfAirplanes); // Resorts the list of planes 

			// Continue prompting
			optionsMenu();
		}

	}

	/**
	 * Allows Users to view the first ranked AC Airplane by using heapExtractMax
	 */
	public  void queuePlaneLandingPrompt() {
		System.out.println();
		System.out.print("Next plane landing: ");

		// Get and removes the maximum element in the heap priority queue
		Airplane currentFirst = heapPriorityQueue.heapExtractMax();

		// Removes that Airplane from our sorted list as well
		sortedListOfAirplanes.remove(currentFirst);

		// Continue prompting
		System.out.println(currentFirst.toString());
		System.out.println();
		optionsMenu();
	}
	
	/**
	 * Restores a plane's emergency landing status to normal (nullifies/undoes emergency)
	 */
	public  void undoEmergencyPrompt()
	{
		System.out.println();

		System.out.print("Enter the flight number of the plane that no longer requires an emergency landing: ");
		String flightNumber = scan.next(); //Stores the entered flight number

		int index = -1; //Stores the index of the plane corresponding to the flight number
		boolean found = false; //False if the flight number was not found
		
		//Searches for the plane's index
		for (Airplane a : heapPriorityQueue.getPriorityQueue()) {
			index++;
			if (a.getFlightNumber().equalsIgnoreCase(flightNumber)) {
				found = true; //This flightNumber exists
				break;
			}

		}
		// If the entered flight number was not found in the priority queue, throw an
		// exception
		if (!found) {
			scan.close();
			throw new IllegalArgumentException("That flight number does not exist.");
		}
		
		// Otherwise we can restore the previous priority for the Airplane
		else {
			Airplane p = heapPriorityQueue.getPriorityQueue().get(index); //Gets the corresponding Airplane object
			
			heapPriorityQueue.getPriorityQueue().remove(p); //Removes the Airplane from the heap priority queue (we will reinsert it to correct position)
			
			p.setApproachCode(p.calculateApproachCode()); //Recalculates and sets the approach code based off its distance and elevation
			
			heapPriorityQueue.maxHeapInsert(p); //Reinserts the plane into the priority queue in the correct position
			
			HeapSort.heapSort(sortedListOfAirplanes); // Resorts the list of planes 

			// Continue prompting
			optionsMenu();
		}
	}

	/**
	 * Allows the User to display the sorted list of all Airplanes
	 */
	public void viewAllPlanesPrompt() {
		// Display all airplanes in order (from highest AC to lowest AC)
		System.out.println();
		System.out.println("Airplanes Landing Sequence: ");
		
		int landingNumber = 1; //Position of plane to land (rank)
		// We print our sorted list backward to display planes from highest AC to lowest
		// AC
		for (int i = sortedListOfAirplanes.size() - 1; i >= 0; i--) {
			System.out.println(landingNumber + "." + sortedListOfAirplanes.get(i).toString()); //Prints the plane info
			landingNumber++;
		}

		// Continue prompting
		System.out.println();
		optionsMenu();
	}

	/**
	 * Closes the System (stops prompting)
	 */
	public void exitPrompt() {
		System.out.println("System exited.");
		scan.close();
	}
	
	/**
	 * Main method (ATC Simulator) for User to run commands through the console
	 */
	
	public static void main(String[] args) {
		ATCTester atc = new ATCTester(); //Create a new ATC object
		
		atc.initializeDefaultPlanes(); //Create 30 random planes and add them into the system
		
		//Display the initial prompt
		optionsMenu();

		//Keep prompting for user actions and executing appropriate responses until System exits
		while (atc.scan.hasNext()) {
			String input = atc.scan.next();
			
			// User wants to exit
			if (input.equalsIgnoreCase("X")) {
				atc.exitPrompt(); //Call the exit prompt
				break; //Stop prompting
			}

			// User wants to add an airplane
			else if (input.equalsIgnoreCase("A")) {
				atc.addPlanePrompt(); //Call the add plane prompt

			}
			// User is indicating an emergency landing
			else if (input.equalsIgnoreCase("E")) {
				atc.emergencyLandingPrompt(); //Call the emergency landing prompt

			}
			// User wants to view first ranked AC airplane that will land
			else if (input.equalsIgnoreCase("Q")) {
				atc.queuePlaneLandingPrompt(); //Call the queue plane landing prompt

			}
			//User wants to undo an emergency landing
			else if (input.equalsIgnoreCase("U"))
			{
				atc.undoEmergencyPrompt(); //Call the undo emergency prompt
			}
			// User wants to view all planes in order
			else if (input.equalsIgnoreCase("V")) {
				atc.viewAllPlanesPrompt(); //Call the view all planes prompt
			}

		}

	}
	
}
