import java.util.Arrays;

/**
 * Implementation of the Counting Sort Algorithm
 * 
 * @author Diljot Singh
 *
 */
public class CountingSort {

	/**
	 * Sorts an array using counting sort
	 * 
	 * @param arrayA the array to sort
	 * @param arrayB the array to return the sorted result in
	 * @param range  the maximum range of the elements (maximum value)
	 */
	public static int[] countingSort(int[] arrayA, int[] arrayB, int range) {
		int[] auxiliaryStorage = new int[range+1]; // Create an auxiliary array to help with the sorting

		// Fill each index of the auxiliary storage array with 0's
		for (int i = 0; i <= range; i++) {
			auxiliaryStorage[i] = 0;
		}

		// auxiliaryStorage[i] now contains the number of elements equal to i
		for (int i = 0; i < arrayA.length; i++) {
			auxiliaryStorage[arrayA[i]] = auxiliaryStorage[arrayA[i]] + 1;
		}

		// auxiliaryStorage[i] now contains the number of elements less than or equal to i
		for (int i = 1; i <= range; i++) {
			auxiliaryStorage[i] = auxiliaryStorage[i] + auxiliaryStorage[i - 1];
		}

		
		//Prints array B and auxiliary storage array C to display their contents before sorting
		System.out.println("Before sorting, Array B = " + Arrays.toString(arrayB)); 
		System.out.println("Before sorting, Array C = " + Arrays.toString(auxiliaryStorage)); 
		System.out.println();
		
		int loopCounter = 1; //Used for printing intermediate steps
		
		// Places each element from arrayA into the right position in arrayB (sorts)
		for (int j = arrayA.length - 1; j >= 0; j--) {
						
			arrayB[auxiliaryStorage[arrayA[j]] - 1] = arrayA[j]; // Places arrayA[j] into the correct position in arrayB based on counter
			
			//Prints which element is inserted and at what index to show that the algorithm is stable
			System.out.println("Inserted " + arrayA[j] + " to index " + (auxiliaryStorage[arrayA[j]] - 1));
			
			System.out.println(loopCounter + "." + " Array B: " + Arrays.toString(arrayB)); //Prints array B
			
			auxiliaryStorage[arrayA[j]] = auxiliaryStorage[arrayA[j]] - 1; // Decrements corresponding counter in auxiliary array
			System.out.println(loopCounter + "." + " Array C: " + Arrays.toString(auxiliaryStorage)); //Prints array C
			
			loopCounter++; //Increments loop counter
			System.out.println();

		}
		return arrayB; // Returns the sorted array
	}

	//Tests the counting sort method
	public static void main(String[] args) {
		int[] unsortedArray = new int[] { 7, 7, 2, 8, 4, 2, 3, 5, 4, 8, 3, 6, 4, 6, 2 }; //unsorted array A
		int[] sortedArray = new int[unsortedArray.length]; //initially empty output array B

		System.out.println("Input array: " + Arrays.toString(unsortedArray));
		System.out.println();
		countingSort(unsortedArray, sortedArray, 8); //Calls the counting sort algorithm with range k = 8
		System.out.println("Sorted array: " + Arrays.toString(sortedArray)); //Prints the result of sorted output array B
	}

}
