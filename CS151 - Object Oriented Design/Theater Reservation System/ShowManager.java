import java.util.*;

//Manages and stores all Shows in the System
public class ShowManager {

	private static ArrayList<Show> shows;

	// Constructs a ShowManager with an empty list of Shows
	public ShowManager() {
		shows = new ArrayList<Show>();
	}

	// Adds a Show to the Show database
	public static boolean addShow(Show s) {
		if (shows.contains(s)) // Prevents duplicates
		{
			return false; // Show was not added
		} else {
			shows.add(s);
			return true; // Show was added
		}

	}

	// Getter for all Shows in the database
	public static ArrayList<Show> getAllShows() {
		return shows;
	}

	// Gets a specified Show by its month, day, year, and time
	public static Show getSpecificShow(String month, int day, String year, String time) {
		Show newShow = new Show(month, day, year, time);
		for (Show s : shows) {
			if (s.equals(newShow)) // We found such a Show in the database
			{
				return s; // Return that Show
			}

		}
		return null; // no such Show exists
	}

	// Creates and adds all Shows to the System: 2 shows everyday at 6:30 and 8:30
	// from December 23, 2020 to January 2, 2021
	public void initializeShows() {
		// Creates all the Shows in December
		for (int date = 23; date <= 31; date++) {
			Show A = new Show("December", date, "2020", "6:30");
			ShowManager.addShow(A); // Adds the Show to the System
			Show B = new Show("December", date, "2020", "8:30");
			ShowManager.addShow(B); // Adds the Show to the System
		}
		// Creates all the Shows in January
		for (int date = 1; date <= 2; date++) {
			Show A = new Show("January", date, "2021", "6:30");
			ShowManager.addShow(A); // Adds the Show to the System
			Show B = new Show("January", date, "2021", "8:30");
			ShowManager.addShow(B); // Adds the Show to the System
		}
	}

}
