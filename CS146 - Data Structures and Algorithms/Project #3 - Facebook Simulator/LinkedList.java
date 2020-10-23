import java.util.*;

/**
 * Implements a generic Linked List class (doubly linked) to be used in the chaining hash table
 * 
 * @author Diljot Singh
 */
public class LinkedList<T> {

	private Node head; // Holds a reference to the first node in the list
	private Node tail; // Holds a reference to the last node in the list

	/**
	 * Constructs an empty linked list
	 */
	public LinkedList() {
		head = null;
		tail = null;
	}

	/**
	 * Gets the first element in this list (head)
	 * 
	 * @return first the first Node's data
	 */
	public Object getFirst() {
		if (head == null) {
			return null;
		}
		return head.data;
	}

	/**
	 * Removes the first element in this list (head)
	 * 
	 * @return
	 */
	public Object removeFirst() {
		if (head == null) {
			throw new NoSuchElementException();
		}

		Object element = head.data;
		head = head.next; // updates head pointer
		if (head == null) {
			tail = null;
		} else {
			head.previous = null;
		}
		return element;
	}

	/**
	 * Adds a new node to the beginning of the list
	 * 
	 * @param element
	 */
	public void addFirst(Object element) {
		Node newNode = new Node();
		newNode.data = element;
		newNode.next = head;
		newNode.previous = null;
		if (head == null) {
			tail = newNode;
		} else {
			head.previous = newNode;
		}

		head = newNode;
	}

	/**
	 * Removes the last element in the linked list (tail)
	 * 
	 * @return the removed element
	 */
	public Object removeLast() {
		if (tail == null) {
			throw new NoSuchElementException();
		}
		Object element = tail.data;
		tail = tail.previous;
		if (tail == null) {
			head = null;
		} else {
			tail.next = null;
		}
		return element;
	}

	/**
	 * Checks to see if the list contains the specified element
	 * 
	 * @return true if the element was found, false if it was not
	 */
	public boolean contains(Object element) {
		Node temp = head; // start searching from head
		boolean found = false; // boolean flag

		// Keep searching while we have not reached the end of the list and element is
		// not found
		while ((temp != null) && (!found)) {
			if (temp.data.equals(element)) // If we find the matching element
			{
				found = true; // Set boolean flag to true, element was found
			} else {
				temp = temp.next; // Moves the pointer over
			}
		}
		return found; // Return the boolean flag, will be false if not found
	}

	/**
	 * Removes a specified element from the list
	 * 
	 * @param element the element to remove
	 */
	public void remove(Object element) {
		// If specified element is the head, we remove the head
		if (element.equals(head.data)) {
			removeFirst();
		}

		// If specified element is the tail, we remove the tail
		else if (element.equals(tail.data)) {
			removeLast();
		}

		// Otherwise we search for the node and delete it
		else {
			Node temp = head;

			// While the object is not found and we haven't reached the end of the list, we
			// can still iterate
			while (temp != null && !(temp.data.equals(element))) {
				temp = temp.next; // Moves Node pointer
			}

			// If the node was not found (null), then we do not remove anything
			if (temp == null) {
				return; // node was not found
			}
			// Otherwise, temp now contains a pointer to the specified node and we can
			// manipulate its pointers
			else {

				// Change adjacent pointers to delete that node, removing all links to it
				temp.previous.next = temp.next;
				temp.next.previous = temp.previous;
			}
		}
	}

	/**
	 * Gets a toString representation of this list
	 */
	public String toString() {
		String output = "";
		Iterator<Object> iterator = getListIterator();

		// Iterates through the list, adding each object to the output string
		while (iterator.hasNext()) {
			output = output + iterator.next().toString() + " ";
		}

		// Format the list depending on whether it was empty or not, and return it
		if (output.length() > 0) {
			return output.substring(0, output.length() - 1);
		} else {
			return output;
		}
	}

	// Private inner class Node which stores an element and a reference to the next
	// node
	class Node {
		public Object data;
		public Node next;
		public Node previous;
	}

	/**
	 * Returns an iterator for iterating through this list
	 * 
	 * @return the linked list iterator
	 */
	public Iterator<Object> getListIterator() {
		return new Iterator<Object>() {

			Node position = null; // Keeps track of the current position

			/**
			 * Checks if there are more elements to iterate through
			 * 
			 * @return true if position.next != null (we have more elements)
			 */
			public boolean hasNext() {
				if (position == null) {
					return head != null;
				} else {
					return position.next != null;
				}
			}

			/**
			 * Gets the next element in the iterator
			 * 
			 * @return the next element
			 */
			public Object next() {
				if (!hasNext()) {
					return null;
				}
				if (position == null) {
					position = head;
				} else {
					position = position.next;
				}

				return position.data;
			}

		};
	}

}
