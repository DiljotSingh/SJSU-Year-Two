import java.io.*;
import java.util.*;


//This class writes all Reservations of all Users in the System to a file 'reservations.txt'
public class ReservationWriter {

	private static File reservations = new File("data/reservations.txt"); // File location

	// Writes all Reservations to the reservations.txt file
	public static void writeReservations() throws Exception {
		BufferedWriter bw = new BufferedWriter(new FileWriter(reservations)); // Create a new buffered writer

		// We create an iterator to iterate through the Reservation database HashMap
		Iterator hashMapIterator = ReservationManager.getAllReservations().entrySet().iterator();
		while (hashMapIterator.hasNext()) {
			Map.Entry mapElement = (Map.Entry) hashMapIterator.next();

			String userKey = (String) mapElement.getKey();
			bw.write("User: " + userKey); // Prints the username of the current user
			bw.write(", Password: " + UserManager.getUserDataBase().get(userKey));
			bw.newLine();
			// Prints all Reservations of the current user
			for (Reservation res : ReservationManager.getUserReservations(userKey)) {
				bw.write(res.getShow().getMonth() + " " + res.getShow().getDate() + ", " + res.getShow().getYear()
						+ "; " + res.getShow().getTime() + "PM. " + "Seats reserved: " + res.getSeats().toString());
				bw.newLine();

			}
			bw.newLine();
			bw.newLine();
		}

		bw.close();
	}
}
