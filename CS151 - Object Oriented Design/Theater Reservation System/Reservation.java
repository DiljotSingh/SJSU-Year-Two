import java.util.*;

//A Reservation is comprised of the seats a User selected for a specified Show, and the Show itself
//Each Reservation also has a price, based on the seating locations and Show date
public class Reservation implements Comparable<Reservation> {
	private Show show;
	private ArrayList<String> seats;
	private int price;

	// Constructs a Reservation that is made up of a Show and a list of seats the
	// User can add to or remove from
	public Reservation(Show show, ArrayList<String> seats) {
		this.show = show;
		this.seats = seats;
		this.price = 0; // Price is initially 0

	}

	// Calculates and returns the current price of this Reservation
	public int getPrice() {
		this.price = Calculator.calculatePrice(show, seats);
		return price;
	}

	// Getter for Show
	public Show getShow() {
		return this.show;
	}

	// Getter for seats
	public ArrayList<String> getSeats() {
		return this.seats;
	}

	// Removes a seat from the User's Reservation (makes sure only something that
	// exists can be removed)
	public boolean cancelSeat(String s) {
		if (this.seats.contains(s)) {
			this.seats.remove(s);
			return true; // we removed the seat

		} else {
			return false; // we could not remove the seat
		}
	}

	// Adds a seat to the User's Reservation (makes sure no duplicate seats)
	public boolean addSeat(String s) {
		if (!this.seats.contains(s)) {
			this.seats.add(s);
			return true; // we added the seat
		} else {
			return false; // we could not add the seat
		}
	}

	// All Reservations are ordered first by date then time
	public int compareTo(Reservation other) {
		String date1 = this.getShow().getMonth() + this.getShow().getDate() + this.getShow().getYear();
		String date2 = other.getShow().getMonth() + other.getShow().getDate() + other.getShow().getYear();
		int value = date1.compareTo(date2);
		if (value == 0) // Dates are the same if this true, so we then compare by time
		{
			value = this.getShow().getTime().compareTo(other.getShow().getTime());
		}
		return value; // Returns 0 if objects have same month, date, year, and time; positive integer
						// if first object should follow second object; negative if first object should
						// precede second object
	}

}
