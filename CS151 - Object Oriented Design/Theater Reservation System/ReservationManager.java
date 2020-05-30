import java.util.*;

//Manages and stores all Reservations in the System
public class ReservationManager {

	// Maps every User id to a list of their Reservations
	private static HashMap<String, ArrayList<Reservation>> allReservations;

	//Constructs a Reservation Manager
	public ReservationManager() {
		allReservations = new HashMap<String, ArrayList<Reservation>>();
	}

	// Adds a valid User to the Reservation database (only done when a User signs
	// up)
	public static void addUser(String userId) {
		allReservations.put(userId, new ArrayList<Reservation>()); // Each user has a list of their reservations

	}

	// Adds a Reservation to a User's list
	public static void addUserReservation(String userId, Reservation reservation) {
		allReservations.get(userId).add(reservation); // Adds the reservation to their list
	}

	// Cancels a Reservation from a User's list
	public static void cancelUserReservation(String userId, Reservation reservation) {
		allReservations.get(userId).remove(reservation); // Removes the reservation from the user's list
	}

	// Gets a list of the User's Reservations (sorted by date, then time)
	public static ArrayList<Reservation> getUserReservations(String userId) {

		Collections.sort(allReservations.get(userId));
		return allReservations.get(userId);
	}

	// Gets all Reservations of all Users (used to write to reservations.txt in
	// 'Exit' prompt)
	public static HashMap<String, ArrayList<Reservation>> getAllReservations() {
		return allReservations;
	}

}
