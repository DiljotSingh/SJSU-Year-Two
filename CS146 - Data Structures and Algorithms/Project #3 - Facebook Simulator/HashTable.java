import java.util.*;

/**
 * Models a Hash Table for storing Person objects using chaining
 * 
 * @author Diljot Singh
 *
 */
public class HashTable {

	public static final int TABLE_SIZE = 13; // Max table size

	// An array of linked lists to simulate a hash table with chaining
	private LinkedList<Person>[] table = new LinkedList[TABLE_SIZE];

	/**
	 * Constructs an empty HashTable implementing chaining
	 */
	public HashTable() {
		for (int i = 0; i < TABLE_SIZE; i++) {
			table[i] = new LinkedList<Person>();
		}
	}

	/**
	 * Inserts a Person to the head of list T[h(x.key)]
	 * 
	 * @param newPerson the person to insert
	 */
	public void chainedHashInsert(Person newPerson) {
		table[newPerson.hashCode()].addFirst(newPerson); // Hashes the person to the appropriate position

	}

	/**
	 * Searches to see if the specified Person exists in list T[h(k)]
	 * 
	 * @param searchTarget the Person to search for
	 * @return true if the Person exists in that list, false if not
	 */
	public boolean chainedHashSearch(Person searchTarget) {
		if (searchTarget != null) {
			return table[searchTarget.hashCode()].contains(searchTarget);
		}
		return false;

	}

	/**
	 * Deletes a Person from the list T[h(x.key)]
	 * 
	 * @param newPerson the Person to remove
	 */
	public void chainedHashDelete(Person removePerson) {
		table[removePerson.hashCode()].remove(removePerson);
	}

	/**
	 * Gets the specified Person object from the table given their name, if they
	 * exist
	 * 
	 * @param name the name of the Person to retrieve
	 * @return the Person with the associated name
	 */
	public Person getPerson(String name) {
		Person temp = new Person(name, new ArrayList<Person>()); // Temporary Person for iterating

		// If the list is empty, we return null
		if (table[temp.hashCode()].getFirst() == null) {
			return null;
		}

		// Create a list iterator
		Iterator<Object> iterator = table[temp.hashCode()].getListIterator();
		// Start iterating from beginning of the list with the corresponding hash code
		while (iterator.hasNext()) {
			temp = (Person) iterator.next();

			if (temp.getName().equals(name)) {
				return temp; // return the Person object if found
			}
		}

		return null; // return null if that Person does not exist
	}

	/**
	 * Gets a toString() representation of this hash table
	 */
	public String toString() {

		String outputString = "";
		int index = 0;
		// Prints each list in the table
		for (LinkedList<Person> list : table) {
			outputString = outputString + index + " = " + "[" + list.toString() + "]" + "\n";
			index++;
		}
		return outputString;
	}

}
