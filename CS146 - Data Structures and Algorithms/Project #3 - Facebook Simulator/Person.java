import java.util.ArrayList;

/**
 * Models a Person on Facebook with a name and a list of friends
 * 
 * @author Diljot Singh
 *
 */
public class Person {

	private String name; // The name of the Person
	private ArrayList<Person> friends; // The Person's list of friends

	/**
	 * Constructs a Person with a name and a list of friends
	 * 
	 * @param name    the specified name of the Person
	 * @param friends the person's list of friends
	 */
	public Person(String name, ArrayList<Person> friends) {
		this.name = name;
		this.friends = friends;
	}

	/**
	 * Getter for the Person's name
	 * 
	 * @return name this Person's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter for the Person's friends
	 * 
	 * @return friends this Person's list of friends
	 */
	public ArrayList<Person> getFriends() {
		return this.friends;
	}

	/**
	 * Adds a person to this Person's list of friends
	 * 
	 * @param newFriend the new friend to add
	 */
	public void addFriend(Person newFriend) {
		// Makes sure these two aren't already friends before sending a friend request
		if (!(this.hasFriend(newFriend)) && !(this.getName().equals(newFriend.getName()))) {

			this.friends.add(newFriend); // Adds the new friend to this Person's list of friends
			newFriend.getFriends().add(this); // Also adds this Person to the newFriend's list of friends
		}
	}

	/**
	 * Removes a person from this Person's list of friends
	 * 
	 * @param badFriend the friend to unfriend
	 */
	public void removeFriend(Person badFriend) {
		// Makes sure these two are friend before trying to remove them
		if (this.hasFriend(badFriend)) {

			this.friends.remove(badFriend); // Removes badFriend from this Person's list of friends
			badFriend.getFriends().remove(this); // Removes this Person from the badFriend's list of friends as well
		}
	}

	/**
	 * Checks if this Person has the other Person as a friend
	 * 
	 * @param target the other Person to check
	 * @return true if these two people are friends
	 */
	public boolean hasFriend(Person otherPerson) {
		return this.friends.contains(otherPerson);
	}

	/**
	 * Computes the hash code of a Person's name using the division method h(k) = k
	 * mod m, where 'k' is the key calculated from the name and 'm' is the size of
	 * the hash table (13)
	 */
	public int hashCode() {
		int modValue = 13; // prime number - size of hash table

		// ASCII code approach for String name
		int key = 0; // Stores the total String's value as a integer

		// Calculates and stores each character's value into key by doing (value * (128
		// ^ weight))
		for (int i = 0; i < name.length(); i++) {
			char currentCharacter = name.charAt(i); // The current character in the name
			int power = (int) Math.pow(128, name.length() - 1 - i); // The weight of each character's place (128 ^
																	// place)

			key = Math.abs(key + (currentCharacter * power)); // Multiplies the character value by its weight (Example:
																// family -> f = 102 * 128^5)
		}

		// Returns (h(k) = k mod m)
		return key % modValue;

	}

	/**
	 * Allows users to get the toString() representation of this object
	 */
	public String toString() {
		return this.getName();
	}

}
