import java.util.*;

//Takes user inputs, outputs responses, and prompts appropriate user actions
public class Prompter {

	private static Scanner scan = new Scanner(System.in);
	private User currentUser; // Used to keep track of the current user for database fetch
								// requests
	
	//Restores all previous data to the databases
	public void loadSystem()
	{
		try
		{
			ReservationLoader.loadReservations();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage() + ": Loaded all reservations.");
		}
	}

	// Displays the initial options for a User: Sign Up (U), Sign In(I), or Exit (X)
	public void initialPrompt() {
		System.out.print("Enter 'U' to Sign Up, 'I' to Sign In, or 'X' to Exit: ");
		while (scan.hasNext()) {
			String input = scan.next();
			if (input.equalsIgnoreCase("X")) { // 'X' signifies User wants to exit
				exit(); // Program goes to the exit prompt
				break;

			} else if (input.equalsIgnoreCase("U")) { // 'U' signifies a sign up request
				signUpPrompt(); // Program goes to the sign up prompt
				break;

			} else if (input.equalsIgnoreCase("I")) { // 'I' signifies a sign in request
				signInPrompt(); // Program goes to the sign in prompt
				break;
			}
		}
	}

	// Prompt for a User to sign up
	public void signUpPrompt() {
		System.out.print("Enter a username: "); // Ask for their username
		String username = scan.next();

		System.out.print("Enter a password: "); // Ask for their password
		String password = scan.next();

		currentUser = new User(username, password); // Creates a temporary User object with the entered username & pass
		if (UserManager.addUser(currentUser) == false) // Checks User database; returns false if username already taken
		{
			System.out.println("That username has been taken, please try again.");
			signUpPrompt(); // Prompts user to try signing up again
		} else {
			transactionPrompt(); // Otherwise, the new user's credentials are added to the database
									// We go to transaction mode
		}

	}

	// Prompt for a User to sign in
	public void signInPrompt() {
		System.out.print("Enter your username: "); // Ask for their username
		String username = scan.next();

		System.out.print("Enter your password: "); // Ask for their password
		String password = scan.next();
		currentUser = new User(username, password); // Creates a temporary User object with the entered username & pass
		// Checks to see if user credentials exist
		if (UserManager.authenticateUser(currentUser) == false) // Checks User database; returns false if combination
																// could not be found or did not match
		{
			System.out.println("That combination did not match.");
			System.out.print("Please enter 'T' to try again or 'U' to sign up: "); // Lets user either try entering
																					// combination again or have option
																					// to sign up
			String input = scan.next();
			if (input.equalsIgnoreCase("T")) // 'T' lets the User try reentering their combination
			{
				signInPrompt();
			} else if (input.equalsIgnoreCase("U")) // 'U' lets the User sign up if they do not have an account
			{
				signUpPrompt();
			}
		} else {
			transactionPrompt(); // User has been authenticated, we go to transaction mode
		}
	}

	// Exit prompt, copies all reservations of all users in system to text file
	// 'reservations.txt'
	public void exit() {
		try {
			ReservationWriter.writeReservations(); // Writes the reservations to the file
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Prompt for a User to make a reservation (R), view their reservations (V),
	// cancel a reservation (C), or sign out (O)
	public void transactionPrompt() {
		System.out.println("Enter 'R' to Reserve, 'V' to View, 'C' to Cancel', or 'O' to Sign Out");
		while (scan.hasNext()) {
			String input = scan.next();
			if (input.equalsIgnoreCase("O")) { // 'O' signifies a sign out request
				signOutPrompt(); // We call the sign out prompt
				break;

			} else if (input.equalsIgnoreCase("R")) { // 'R' signifies User wants to make a reservation
				makeReservationPrompt(); // We call the makeReservation prompt
				break;

			} else if (input.equalsIgnoreCase("V")) { // 'V' signifies User wants to view their reservations
				viewReservationPrompt(); // We call the viewReservation prompt
				break;

			} else if (input.equalsIgnoreCase("C")) { // 'C' signifies User wants to cancel a reservation
				cancelReservationPrompt(); // We call the cancelReservation prompt
				break;
			}
		}
	}

	// Prompt to handle User sign out request
	public void signOutPrompt() {

		// Prints every User's Reservation price one by one, then prints a total cost at
		// the end
		int totalPrice = 0;
		for (Reservation res : ReservationManager.getUserReservations(currentUser.getUsername())) {
			System.out.println("Reservation at " + res.getShow().getMonth() + " " + res.getShow().getDate() + ","
					+ res.getShow().getYear() + "; " + res.getShow().getTime() + "PM. " + ": $" + res.getPrice());
			totalPrice = totalPrice + res.getPrice();
		}
		System.out.println("Here is your total price: $" + totalPrice);
		System.out.println("Signed out.");
		initialPrompt();
	}

	// Prompt to handle User 'make reservation' requests
	public void makeReservationPrompt() {
		System.out.println("Valid shows are between December 23, 2020 and January 2, 2021");
		System.out.print("Please enter the desired Show's month, either 'December' or 'January: "); // Prompts User for
																									// a desired month
		String month = scan.next();

		System.out.print("Please enter the desired Show's day: "); // Prompts User for a desired day
		int day = scan.nextInt();

		System.out.print("Please enter the Show year, either '2020' or '2021': "); // Prompts User for a desired year
		String year = scan.next();

		System.out.print("Finally, enter Show's time, either '6:30' or '8:30': "); // Prompts User for a desired time
		String time = scan.next();

		Show desiredShow = null;
		// Checks Show database to see if the User wants to make a Reservation for a
		// valid Show
		if (ShowManager.getSpecificShow(month, day, year, time) == null) {
			System.out.println("We have no shows at that time. Please try again.");
			makeReservationPrompt();
		} else {

			desiredShow = ShowManager.getSpecificShow(month, day, year, time); // Retrieves the Show the User wants
			desiredShow.getAllSeating(); // Prints all the available seats for that Show

			Reservation userRes = null;

			// If a User has a previous Reservation for the Show already, we just keep
			// adding to its list instead of creating a new one
			// Otherwise we have to create a new Reservation

			// Empty Reservation list means we have to create a new one
			if (ReservationManager.getUserReservations(currentUser.getUsername()).size() == 0) {
				userRes = new Reservation(desiredShow, new ArrayList<String>());

			} else {
				boolean found = false;
				// Checks Reservation database to see if User has a previous Reservation that
				// matches current request
				for (Reservation r : ReservationManager.getUserReservations(currentUser.getUsername())) {
					if (r.getShow().equals(desiredShow)) {
						userRes = r;
						// Removes the older Reservation, we will add the updated one back later
						ReservationManager.getUserReservations(currentUser.getUsername()).remove(r);
						found = true;
						break;
					}
				}
				// If the list was not empty and we could not find a previous matching
				// Reservation, then we create a new Reservation
				if (!found) {
					// Otherwise we make a new Reservation for the User
					userRes = new Reservation(desiredShow, new ArrayList<String>());
				}

			}

			// Prompts User for seats to enter
			System.out.print("Please enter a seat you would like to reserve, for example 'M34' or 'W12': ");
			while (scan.hasNext()) {
				String input = scan.next(); // the seat the user wants to reserve
				input = input.toUpperCase(); // Helps user not have to worry about 'm33' vs 'M33' for example; also
												// helps program maintain consistency
				if (input.equalsIgnoreCase("X")) { // 'X' signifies User wants to confirm current session
					break;
				}
				// Attempts to reserve this seat in the System if possible; returns false if
				// failed
				else if (desiredShow.reserveSeat(input.substring(0, 1),
						Integer.parseInt(input.substring(1))) == false) {
					System.out.print("Please try a different seat or enter 'X' to confirm current session: ");
				} else {
					System.out.println("Success! Reserved " + input);
					userRes.addSeat(input); // Adds this seat to the User's list of seats for this Reservation
					System.out.print(" Enter another seat to reserve or enter 'X' to confirm current session: ");
				}
			}
			// Adds this Reservation to the Reservation database; Reservation is added to
			// the User's list of Reservations
			ReservationManager.addUserReservation(currentUser.getUsername(), userRes);
			transactionPrompt();
		}

	}

	// Prompt to handle 'view reservation' requests
	public void viewReservationPrompt() {
		// Sends request to Reservation database to print all Reservations mapped to the
		// current user
		for (Reservation res : ReservationManager.getUserReservations(currentUser.getUsername())) {
			System.out.println(res.getShow().getMonth() + " " + res.getShow().getDate() + ", " + res.getShow().getYear()
					+ "; " + res.getShow().getTime() + "PM. " + "Seats reserved: " + res.getSeats().toString());
		}
		transactionPrompt(); // Takes user back to transaction mode
	}

	// Prompt to handle 'cancel reservation' requests
	public void cancelReservationPrompt() {

		System.out.print("Please enter the Show's month, either 'December' or 'January: "); // Month of desired
																							// Reservation cancellation
		String month = scan.next();

		System.out.print("Please enter the Show date: "); // Day of desired Reservation cancellation
		int day = scan.nextInt(); // Store the user's desired date

		System.out.print("Please enter the Show year: "); // Year of desired Reservation cancellation
		String year = scan.next();

		System.out.print("Finally, enter Show's time: "); // Time of desired Reservation cancellation
		String time = scan.next();

		boolean showFound = false;
		Reservation cancelReservation = null;

		// Tries to find if the User has a Reservation for the specified Show
		for (Reservation res : ReservationManager.getUserReservations(currentUser.getUsername())) {
			if ((res.getShow().equals(new Show(month, day, year, time)))) {
				cancelReservation = res; // Yes, this show was found, and we can start cancelling seats in the
											// Reservation
				// Displays all the seats the user has reserved for this specified show
				System.out
						.println("Here are the seats you have reserved for this Show: " + cancelReservation.getSeats());
				showFound = true;
				break;
			}
		}
		// If we could not find the specified Reservation (if it does not exist), then
		// we inform the user

		if (!showFound) {
			System.out.println("Sorry, you have no reservations for this Show");
			transactionPrompt(); // Takes the user back to Transaction mode
		}
		// Otherwise we can begin to cancel seats
		else {
			System.out.print("Please enter the seat you'd like to cancel or enter 'X' if you are done: "); // Prompts
																											// User to
																											// enter a
																											// seat
																											// they'd
																											// like to
																											// cancel
			while (scan.hasNext()) {
				String input = scan.next();
				input = input.toUpperCase(); // Helps user avoid minor syntax errors
				if (input.equalsIgnoreCase("X")) { // 'X' signifies the User is done
					break;
				} else if (cancelReservation.cancelSeat(input) == true) // Removes that seat from the User's
																		// reservations
				{
					System.out.println("Success! Removed: " + input);
					// Unreserves the cancelled seat in the System
					cancelReservation.getShow().unreserveSeat(input.substring(0, 1),
							Integer.parseInt(input.substring(1)));

					// Shows User their updated Reservation
					System.out.println("Your new seating: " + cancelReservation.getSeats());
					System.out.print("Enter another seat you'd like to cancel or enter 'X' if you are done: ");

					// If the user has removed all seats for a Reservation, we remove that
					// Reservation entirely
					if (cancelReservation.getSeats().isEmpty()) {
						ReservationManager.cancelUserReservation(currentUser.getUsername(), cancelReservation);
						System.out.print("Enter another seat you'd like to cancel or enter 'X' if you are done: ");
					}
				} else {
					// If User entered an invalid seat, we inform them
					System.out.print("Error, could not remove: " + input + ".");
					System.out.print(" Enter another seat you'd like to cancel or enter 'X' if you are done: ");
				}
			}
			transactionPrompt(); // Takes user back to transaction mode

		}
	}
}
