import java.util.*;

/**
 * Implements the HeapSort algorithm through a Max Heap 
 * 
 * @author Diljot Singh
 *
 */
public class HeapSort {

	/**
	 * Helper method to gets the parent index of the specified element
	 * 
	 * @param index the index of the specified element
	 * @return (index/2), which is the index of the parent element
	 */
	private static int getParent(int index) {
		return index / 2;
	}

	/**
	 * Helper method to get the left child's index of the specified element
	 * 
	 * @param index the index of the specified Node
	 * @return (2*index) + 1, which is the index of the left element
	 */
	private static int getLeftChild(int index) {
		return (2 * index) + 1;
	}

	/**
	 * Helper method to get the right child's index of the specified element
	 * 
	 * @param index the index of the specified Node
	 * @return (2*index) + 2, which is the index of the right element
	 */
	private static int getRightChild(int index) {
		return (2 * index) + 2;
	}

	/**
	 * Maintains the max heap property for a specific element
	 * 
	 * @param airplanes the ArrayList the element is in
	 * @param heapSize  the current size of the heap
	 * @param index     the index of the element to turn into a max heap
	 */
	public static void maxHeapify(ArrayList<Airplane> airplanes, int heapSize, int index) {
		int largest;
		int left = getLeftChild(index); // Get the left child's index of the current element
		int right = getRightChild(index); // Get the right child's index of the current element

		//If the left element is bigger than the parent, we set largest = left
		if ((left < heapSize) && (airplanes.get(left).compareTo(airplanes.get(index)) > 0)) {
	
			largest = left;
			
		} else {
			largest = index; //Otherwise largest is currently the parent
		}

		//If the right element is bigger than the current largest, we set largest = right
		if ((right < heapSize) && (airplanes.get(right).compareTo(airplanes.get(largest)) > 0)) {
		
			largest = right;
		}
		
		//If the largest of the three elements was not the parent, we swap the positions of the parent and largest
		if (largest != index) {

			//Swap A[index] and A[largest]
			Airplane temp = airplanes.get(index); // Stores the Airplane at index
			airplanes.set(index, airplanes.get(largest)); //Sets the Airplane at index to the Airplane at largest
			airplanes.set(largest, temp); //Sets the Airplane at largest to Airplane previously at index
			
			maxHeapify(airplanes, heapSize, largest); // Maintain max heap property
		}
	}

	/**
	 * Builds a max heap for an ArrayList by calling maxHeapify on each element in a
	 * bottom up manner
	 * 
	 * @param airplanes the ArrayList to transform into a max heap
	 */
	public static void buildMaxHeap(ArrayList<Airplane> airplanes) {
		for (int i = airplanes.size()/2 - 1; i >= 0; i--) { 
			maxHeapify(airplanes, airplanes.size(), i);
		}
	}

	/**
	 * Sorts an ArrayList of airplanes using the heapsort algorithm
	 * 
	 * @param airplanes the ArrayList of airplanes
	 */
	public static void heapSort(ArrayList<Airplane> airplanes) {
		buildMaxHeap(airplanes); // Turns the list into a max heap
		
		for (int i = airplanes.size()-1; i > 0; i--) { // Heap size (i) is decreasing with each iteration
			
			//Swaps A[0] and A[i]
			Airplane temp = airplanes.get(0);
			airplanes.set(0, airplanes.get(i));
			airplanes.set(i, temp);
			maxHeapify(airplanes, i, 0); // Maintains the max heap property for this current list
		}
	}
	

}
