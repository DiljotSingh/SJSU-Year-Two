import java.io.*;
import java.util.*;

//This class loads all previous Users and Reservations back into the System
public class ReservationLoader {
	
	private static File reservations = new File("data/reservations.txt"); // File containing all user info and reservations

	//Reads data from reservations.txt and loads that data into System accordingly
	public static void loadReservations() throws Exception {	
		
		BufferedReader br = new BufferedReader(new FileReader(reservations)); // Create a new buffered writer

		//Temporary holder variables
		String line;
		String userId = null;
		String userPassword = null;
		User returningUser = null;
		
		//Keeps reading until we get to the end of the file
		while (!(line = br.readLine()).equals(null))
		{	
			if (line.equals(""))  //If we run into an empty line, just skip over it
			{
				line = br.readLine(); 
			}
			
			//All lines are formatted as such: 
			//a) User: userId, Password: password
			//b) Month day, year; time PM. Seats reserved: [seat1, seat2, ...., lastSeat]

			else if (line.contains("User: ")) { //Parses credential line
				userId = line.substring(line.indexOf(" ")+ 1, line.indexOf(",")); //Gets the User ID
				userPassword = line.substring(line.lastIndexOf(" ")+1); //Gets the User password
				
				returningUser = new User(userId, userPassword);
				// Adds the User to the database if it does not already exist
				UserManager.addUser(returningUser);
				
			}
			else // Parses Reservation line
			{
				String month = line.substring(0, line.indexOf(" "));				
				int day = Integer.parseInt(line.substring(line.indexOf(" ")+1, line.indexOf(",")));
				String year = line.substring(line.indexOf(", ")+2, line.indexOf((";")));
				String time = line.substring(line.indexOf("; ")+2, line.indexOf("; ")+ 6);
				
				String allSeats = line.substring(line.indexOf("[")+1, line.indexOf("]"));
				ArrayList<String> seats = new ArrayList<>();

				//Gets the corresponding Show from the System
				Show show = ShowManager.getSpecificShow(month, day, year, time);
				
				//Reserves each seat in the System
				for (String seat : allSeats.split(", "))
				{
					seats.add(seat); //Adds this seat to the Reservation's list
					//Reserves the seat for the Show in the System
					show.reserveSeat(seat.substring(0, 1), Integer.parseInt(seat.substring(1))); 
					
				}
				//Creates a new Reservation
				Reservation res = new Reservation(show, seats);
				
				//Adds this Reservation to the User's list of Reservations
				ReservationManager.addUserReservation(returningUser.getUsername(), res);
			}
		}
		
		br.close();
	}
	
}
