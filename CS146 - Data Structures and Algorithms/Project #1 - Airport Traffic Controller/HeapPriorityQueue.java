import java.util.*;

/**
 * Implements a Heap Priority Queue using an ArrayList of Airplanes
 * 
 * @author Diljot Singh
 *
 */
public class HeapPriorityQueue {

	private ArrayList<Airplane> priorityQueue; // The priority queue of Airplanes

	/**
	 * Constructs a Heap Priority Queue with an initially empty list of Airplanes
	 */
	public HeapPriorityQueue() {
		this.priorityQueue = new ArrayList<Airplane>();
	}

	/**
	 * Getter for this priority queue
	 * @return priorityQueue
	 */
	public ArrayList<Airplane> getPriorityQueue() {
		return this.priorityQueue;
	}

	/**
	 * Gets the current maximum element of this priority queue
	 * 
	 * @return priorityQueue.get(0) (the first element in the ArrayList)
	 */
	public Airplane heapMaximum() {
		return priorityQueue.get(0);
	}

	/**
	 * Gets and removes the current maximum element of this priority queue
	 * 
	 * @return the first element in the priority queue ArrayList
	 */
	public Airplane heapExtractMax() {
		if (priorityQueue.size() < 1) {
			throw new RuntimeException("Heap underflow"); //If the list is empty, we throw an exception
		} 
		else {
			Airplane max = this.heapMaximum(); //We set the max to the first element in the priority queue
			priorityQueue.set(0, priorityQueue.get(priorityQueue.size() - 1)); // Overwrites first element with last element's value

			priorityQueue.remove(priorityQueue.size() - 1); // Removes the last element in the heap (since we now have a duplicate)

			HeapSort.maxHeapify(priorityQueue, priorityQueue.size(), 0); // Maintains the max heap property for the list
			return max; // Return the maximum element that we extracted
		}
	}

	/**
	 * Increases the priority for a specified Airplane in the priority queue
	 * 
	 * @param index 			the index of the specified Airplane
	 * @param newApproachCode   the new Approach Code to increase the Airplane's AC to
	 */
	public void heapIncreaseKey(int index, int newApproachCode) {
		if (newApproachCode < priorityQueue.get(index).getApproachCode()) {
			throw new IllegalArgumentException("New AC is smaller than current AC"); //If the new key is smaller, we throw an exception
		}

		else {
			priorityQueue.get(index).setApproachCode(newApproachCode); //We set the Airplane's AC to the parameter key
			
			while ((index > 0) && (priorityQueue.get(index / 2).compareTo(priorityQueue.get(index)) < 0)) { 
				
				//Keeps swapping A[i] and A[parent(i)] while the parent is smaller
				Airplane temp = priorityQueue.get(index);
				priorityQueue.set(index, priorityQueue.get(index / 2));
				priorityQueue.set(index / 2, temp);
				index = index / 2;
			}

		}
	}

	/**
	 * Inserts an Airplane into this priority queue
	 * 
	 * @param a the Airplane to insert
	 */
	public void maxHeapInsert(Airplane a) {
		priorityQueue.add(a); // Adds the new plane at the end of the list
		heapIncreaseKey(priorityQueue.size() - 1, a.getApproachCode()); // Moves the Airplane into the correct position
	}

}
