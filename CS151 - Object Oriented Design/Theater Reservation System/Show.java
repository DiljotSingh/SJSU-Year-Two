import java.util.*;

//A Show has a date, a time, and sections
public class Show {

	private String month; // the month the show is in
	private int day; // the day the show is on
	private String year; // the year the show is in
	private String time; // the time the show is at

	private HashMap<String, boolean[]> allSections; // maps each section name to a boolean array
	private boolean[] mainSeating = new boolean[150]; // main section has 150 seats
	private boolean[] southSeating = new boolean[50]; // south section has 50 seats
	private boolean[] westSeating = new boolean[100]; // west section has 100 seats
	private boolean[] eastSeating = new boolean[100]; // east section has 100 seats

	//Constructs a Show with a month, day, year, and time
	public Show(String month, int day, String year, String time) {
		this.month = month;
		this.day = day;
		this.year = year;
		this.time = time;

		// Adds all the sections to the Show's list of sections
		allSections = new HashMap<String, boolean[]>();
		allSections.put("M", mainSeating); //'M' is mapped to the main section
		allSections.put("S", southSeating); //'S' is mapped to the south section
		allSections.put("W", westSeating); //'W' is mapped to the west section
		allSections.put("E", eastSeating); //'E' is mapped to the east section

	}

	// Getter for Show day
	public int getDate() {
		return this.day;
	}

	// Getter for Show month
	public String getMonth() {
		return this.month;
	}

	// Getter for Show time
	public String getTime() {
		return this.time;
	}

	// Getter for Show year
	public String getYear() {
		return this.year;
	}

	// Getter for all sections
	public HashMap<String, boolean[]> getAllSections() {
		return allSections;
	}

	// Prints the available seating of all sections
	public void getAllSeating() {
		System.out.println("Here are all the available seats.");
		System.out.println("Main floor: ");
		getMainSeating();
		System.out.println();
		
		System.out.println("South floor: ");
		getSouthSeating();
		System.out.println();

		System.out.println("West floor: ");
		getWestSeating();
		System.out.println();

		System.out.println("East floor: ");
		getEastSeating();
		System.out.println();

	}

	// Prints all main floor seats that are available
	public void getMainSeating() {
		int i = 1;
		for (boolean seatValue : allSections.get("M")) {
			if (seatValue == false) { // a seat value of 'false' signifies the seat is not yet reserved
				System.out.print("M" + i + " ");
				if (i % 10 == 0) { // Prints ten seats per line
					System.out.println();
				}
			}
			i++;
		}
	}

	// Prints all South balcony seats that are available
	public void getSouthSeating() {
		int i = 1;
		for (boolean seatValue : allSections.get("S")) {
			if (seatValue == false) { // a seat value of 'false' signifies the seat is not yet reserved
				System.out.print("S" + i + " ");
				if (i % 10 == 0) { // Prints ten seats per line
					System.out.println();
				}

			}
			i++;
		}
	}

	// Prints all West balcony seats that are available
	public void getWestSeating() {
		int i = 1;
		for (boolean seatValue : allSections.get("W")) {
			if (seatValue == false) { // a seat value of 'false' signifies the seat is not yet reserved
				System.out.print("W" + i + " ");
				if (i % 10 == 0) { // Prints ten seats per line
					System.out.println();
				}

			}
			i++;
		}
	}

	// Prints all East balcony seats that are available
	public void getEastSeating() {
		int i = 1;
		for (boolean seatValue : allSections.get("E")) {
			if (seatValue == false) { // a seat value of 'false' signifies the seat is not yet reserved
				System.out.print("E" + i + " ");
				if (i % 10 == 0) { // Prints ten seats per line
					System.out.println();
				}
			}
			i++;
		}
	}

	// Reserves a specified seat in a specified section
	public boolean reserveSeat(String section, int seatNumber) {
		boolean[] selectedSection = allSections.get(section);
		// We do seatNumber - 1 because indices start at 0 but seats start at 1
		if (selectedSection[seatNumber - 1] == false) // a value of false signifies this seat is open
		{
			selectedSection[seatNumber - 1] = true; //We set this seat's value to true, signifying it is reserved
			return true; // We successfully reserved this seat
		} else {
			System.out.println();
			System.out.print("Seat " + seatNumber + " in " + section + " is taken, sorry. ");
			return false; // Seat was not reserved
		}
	}

	// Unreserves a specified seat in a specified section
	public boolean unreserveSeat(String section, int seatNumber) {
		boolean[] selectedSection = allSections.get(section);
		
		// We do seatNumber - 1 because indices start at 0 but seats start at 1
		if (selectedSection[seatNumber - 1] == true) // a value of true signifies this seat is taken
		{
			selectedSection[seatNumber - 1] = false; // we set this seat's value to false, signifying it is now available
			return true; // We successfully unreserved this seat
		} else {
			return false; // Seat was not reserved
		}
	}

	// Checks to see if two Shows are equal by comparing their month, date, year,
	// and time
	// and year
	public boolean equals(Object o) {
		Show other = (Show) o;
		if ((this.getMonth().equalsIgnoreCase(other.getMonth())) && (this.getDate() == other.getDate())
				&& (this.getTime().equalsIgnoreCase(other.getTime()))
				&& (this.getYear().equalsIgnoreCase(other.getYear()))) {
			return true; // These two shows are equal

		} else {
			return false; // These two shows are not equal
		}
	}

}
