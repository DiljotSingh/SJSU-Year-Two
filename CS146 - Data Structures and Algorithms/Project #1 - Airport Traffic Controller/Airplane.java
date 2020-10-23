import java.util.Random;

/**
 * Models a real life Airplane that is approaching an airport 
 * Each Airplane has a Flight Number, a distance from the run way, and an elevation
 * 
 * @author Diljot Singh
 *
 */
public class Airplane implements Comparable<Airplane> {

	private String flightNumber; //The flight number of the Airplane
	private int distanceToRunway; //The distance the Airplane is from the Airport run way
	private int elevation; //The elevation the Airplane is in the air
	private int approachCode; //The approach code (calculated by ATC Tower automatically)

	/**
	 * Constructs an Airplane with a flight number, distance to run way (randomly
	 * generated), and elevation (randomly generated)
	 * The approach code is calculated based off of the distance and the elevation
	 * 
	 * @param flightNumber the flight number for this Airplane, such as 'KL445'
	 */
	public Airplane(String flightNumber) {
		this.flightNumber = flightNumber;
		this.distanceToRunway = generateDirectDistance(); // Randomly generates the distance
		this.elevation = generateElevation(); // Randomly generates the elevation
		setApproachCode(calculateApproachCode()); // Calculates the approach code based on distance and elevation
	}

	/**
	 * Calculates the Approach Code of this Airplane (a larger Approach Code
	 * signifies a higher priority to land for the Airplane)
	 * 
	 * @return return the calculated approach code based on the formula (AC = 15000 - (DISTANCE + ELEVATION)/2)
	 */
	public int calculateApproachCode() {
		return 15000 - ((distanceToRunway + elevation) / 2);
	}

	/**
	 * Getter for the Airplane's Approach Code
	 * 
	 * @return approachCode
	 */
	public int getApproachCode() {
		return approachCode;
	}
	
	/**
	 * Getter for the Airplane's Flight Number
	 * @return flightNumber
	 */
	public String getFlightNumber()
	{
		return flightNumber;
	}

	/**
	 * Setter for the approach code - used in 'heapIncreaseKey' procedure
	 * Note: an approach code cannot be over 15000 (meaning distance and elevation can't be less than 0)
	 * @param value the new value to set the approach code to
	 */
	public void setApproachCode(int value) {
		final int maxApproachCode = 15000;
		if (value > maxApproachCode) { //If the value was greater than our max, we set the value to max
			this.approachCode = maxApproachCode;
		} else {
			this.approachCode = value; //Otherwise we set the approach code to the parameter

		}
	}

	/**
	 * Compares this Airplane to another Airplanes based on Approach Codes
	 * 
	 * @return return 0 if both Airplanes have equal Approach codes, a negative
	 *         number if 'this' Airplane has a smaller Approach Code, or a positive
	 *         number if 'this' Airplane has a larger Approach Code
	 */
	public int compareTo(Airplane other) {
		return Integer.compare(this.getApproachCode(), other.getApproachCode());

	}

	/**
	 * Generates a random elevation between 1000 and 3000 meters
	 * 
	 * @return 1000 + r.nextInt(2001), which is the range [1000,3000]
	 */
	public static int generateElevation() {
		final int maxElevation = 3000; //maximum Elevation is 3000 meters
		final int minElevation = 1000; //minimum Elevation is 1000 meters
		Random randomObject = new Random();
		return minElevation + randomObject.nextInt(maxElevation - minElevation + 1); //Returns a random number between [1000, 3000]
	}

	/**
	 * Generates a random distance to run way between 3000 and 20,000 meters
	 * 
	 * @return 3000 + r.nextInt(17001), which is the range [3000,20000]
	 * 
	 */
	public static int generateDirectDistance() {
		final int maxDistance = 20000; //maximum Distance from runway is 20000 meters
		final int minDistance = 3000; //minimum Distance from runway is 3000 meters
		Random randomObject = new Random();
		return minDistance + randomObject.nextInt(maxDistance - minDistance + 1); //Returns a random number between [3000, 20000]
	}

	/**
	 * Formats an Airplane's information to display
	 * Example: (US120, D:8500 meters, H: 1000 meters) – AC: 10250 
	 */
	public String toString() {
		return "(" + flightNumber + ", D:" + distanceToRunway + " meters, H:" + elevation + " meters)" + " — AC:"
				+ getApproachCode();
	}

}
