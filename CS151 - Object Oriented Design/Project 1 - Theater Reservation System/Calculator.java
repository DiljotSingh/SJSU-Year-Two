import java.util.*;

//This class can calculate the price of a particular Reservation
//It can also print a receipt of all Reservations for a particular User
public class Calculator {

	public static final int SMALL_GROUP_DISCOUNT = 2; // Small groups get $2 off per ticket
	public static final int LARGE_GROUP_DISCOUNT = 5; // Large groups get $5 off per ticket
	public static final int DISCOUNT_NIGHT_SEAT_PRICE = 20; // All seats are $20 on discount nights (Dec. 26 and Dec.
															// 27)
	public static final int MAIN_MIDDLE_SEAT_PRICE = 45; // Center of main floor costs $45 per seat
	public static final int MAIN_SIDES_SEAT_PRICE = 35; // Sides of main floor costs $35 per seat
	public static final int SOUTH_BACK_SEAT_PRICE = 50; // Back of South balcony costs $50 per seat
	public static final int SOUTH_FRONT_SEAT_PRICE = 55; // Front of South balcony costs $55 per seat
	public static final int WEST_BALCONY_SEAT_PRICE = 40; // West balcony seats cost $40
	public static final int EAST_BALCONY_SEAT_PRICE = 40; // East balcony seats cost $40
	
	// Calculates the price of a Reservation
	public static int calculatePrice(Show s, ArrayList<String> seats) {
		int price = 0;
		boolean smallGroup = false; // Small group is 5-10 persons
		boolean largeGroup = false; // Large group is 11-20 persons

		if (seats.size() >= 5 && seats.size() <= 10) {
			smallGroup = true; // Small group discount activated
		} else if (seats.size() >= 11 && seats.size() <= 20) {
			largeGroup = true; // Large group discount activated
		}

		for (String seat : seats) {
			// If the date is December 26 or 27, every seat is $20 and group discounts do
			// not apply
			if ((s.getDate() == 26 || s.getDate() == 27) && (s.getMonth().equalsIgnoreCase("December"))) {
				price = price + DISCOUNT_NIGHT_SEAT_PRICE; // Any seat is $20 on a discount night, and group discounts
															// don't count
			} else {
				if (seat.substring(0, 1).equalsIgnoreCase("M")) // Seat on main floor
				{
					if (Integer.parseInt(seat.substring(1)) <= 100) {
						price = price + MAIN_SIDES_SEAT_PRICE; // Main floor sides (m1-m100) price is $35
					} else {
						price = price + MAIN_MIDDLE_SEAT_PRICE; // Main floor middle (m101 - m150) price is $45
					}
					if (smallGroup == true) {
						price = price - SMALL_GROUP_DISCOUNT; // Groups of 5 to 10 gets $2 discount per seat
					} else if (largeGroup == true) {
						price = price - LARGE_GROUP_DISCOUNT; // Groups of 11 to 20 get $5 discount per seat
					}

				} else if (seat.substring(0, 1).equalsIgnoreCase("S")) { // Seat on south balcony
					if (Integer.parseInt(seat.substring(1)) <= 25) {
						price = price + SOUTH_BACK_SEAT_PRICE; // South balcony back (s1 - s25) price is $50
					} else {
						price = price + SOUTH_FRONT_SEAT_PRICE; // South balc front (s26 - s50) price is $55
					}
					if (smallGroup == true) {
						price = price - SMALL_GROUP_DISCOUNT; // Group of 5 to 10 gets $2 disc. per seat
					} else if (largeGroup == true) {
						price = price - LARGE_GROUP_DISCOUNT; // Group of 11 to 20 gets $5 disc. per seat
					}

				} else if (seat.substring(0, 1).equalsIgnoreCase("W")) { // Seat on west balcony
					price = price + WEST_BALCONY_SEAT_PRICE; // All west balcony seats are $40
					if (smallGroup == true) {
						price = price - SMALL_GROUP_DISCOUNT; // Group of 5 to 10 gets $2 disc. per seat
					} else if (largeGroup == true) {
						price = price - LARGE_GROUP_DISCOUNT; // Group of 11 to 20 gets $5 disc. per seat
					}

				} else if (seat.substring(0, 1).equalsIgnoreCase("E")) { // Seat on east balcony
					price = price + EAST_BALCONY_SEAT_PRICE; // All east balcony seats are $40
					if (smallGroup == true) {
						price = price - SMALL_GROUP_DISCOUNT; // Group of 5 to 10 gets $2 disc. per seat
					} else if (largeGroup == true) {
						price = price - LARGE_GROUP_DISCOUNT; // Group of 11 to 20 gets $5 disc. per seat
					}
				} else {
					price = price + 0; // Not a valid seat, does nothing
				}
			}

		}
		return price; // Returns the price of the Reservation after accounting for seating and
						// discounts
	}
}
