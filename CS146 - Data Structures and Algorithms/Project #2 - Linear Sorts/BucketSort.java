import java.util.*;

/**
 * Implementation of the Bucket Sort Algorithm
 * 
 * @author Diljot Singh
 *
 */
public class BucketSort {

	/**
	 * Sorts an array using bucket sort
	 * 
	 * @param array the array to sort
	 * @return sortedArray the sorted array of numbers
	 */
	public static double[] bucketSort(double[] array) {

		// Create a list of linked lists to store all the numbers (each individual
		// bucket is a linked list)
		List<LinkedList<Double>> buckets = new ArrayList<LinkedList<Double>>();

		// Makes each buckets[i] an empty list
		for (int i = 0; i < array.length; i++) {
			buckets.add(new LinkedList<Double>());
		}
		
		// Inserts each array[i] into list buckets[n * array[i]]
		for (int i = 0; i < array.length; i++) {
			
			LinkedList<Double> currentBucket = buckets.get((int) (array.length * array[i])); //Gets buckets[n * array[i]]
			currentBucket.add(array[i]); //Inserts array[i] into buckets[n * array[i]]
			
			//Prints what element was added and where to show that the algorithm is stable
			System.out.println("Inserted " + array[i] + " in bucket " + (int) (array.length * array[i]) + " at index "
					+ currentBucket.lastIndexOf(array[i]));
		}

		// Sort every list bucket[i] using insertion sort
		for (int i = 0; i < array.length; i++) {
			insertionSort(buckets.get(i));
		}

		// Concatenate all the lists in buckets together in order
		double[] sortedArray = new double[array.length];
		int i = 0;
		for (List<Double> list : buckets) { // Loop through the lists
			for (double number : list) { // Loop through the numbers in those lists
				sortedArray[i] = number; // Add the current number into the output array
				i++; // Increment i
			}
		}
		return sortedArray; // Returns the sorted array
	}

	/**
	 * Helper method insertion sort to sort the numbers in each individual bucket
	 * 
	 * @param list the list of numbers to sort
	 */
	public static void insertionSort(List<Double> list) {
		for (int i = 1; i < list.size(); i++) { // Outer loop, pointer i
			int j = i; // pointer j

			// while a[j] < a[j-1], we swap the two elements and decrement j
			// Since insertion sort only swaps when an element is "<" than another, and not
			// "<=", it is stable
			while (((j > 0) && (list.get(j) < list.get(j - 1)))) { // Inner loop

				System.out.println("Insertion sort swapped " + list.get(j-1) + " and " + list.get(j)); //Prints what elements are swapped
				double temp = list.get(j); // temp variable to store a[j]
				list.set(j, list.get(j - 1)); // a[j] = a[j-1]
				list.set(j - 1, temp); // a[j-1] = temp
				j--;
			}
		}
	}

	/**
	 * Helper method to normalize an array (helps ensure 0 <= A[i] < 1)
	 * 
	 * @param array the array to normalize
	 * @return the normalized array
	 */
	public static double[] normalizeArray(int[] array) {
		double[] output = new double[array.length]; // the normalized array to output
		for (int i = 0; i < array.length; i++) {
			int normalizationFactor = (array[i] + "").length(); // calculates what factor of 10 we need to divide the
																// element by
																// Example: A[i] = 12 has length 2, so we divide it by
																// 10^2
																// A[i] = 6 has length 1, so we divide it by 10^1
			output[i] = array[i] / (Math.pow(10, normalizationFactor));
		}

		return output; // return the normalized array
	}

	/**
	 * Helper method to unnormalize an array (restores the elements to their
	 * original ranges)
	 * 
	 * @param array the array to unnormalize
	 * @return the unnormalized array
	 */
	public static int[] unNormalizeArray(double[] array) {
		int[] output = new int[array.length]; // the normalized array to output
		for (int i = 0; i < array.length; i++) {

			// Calculates what factor of 10 we need to multiply the element by
			// Example: A[i] = .12 has length 2, so we multiply it by 10^2
			// A[i] = .6 has length 1, so we multiply it by 10^1
			// We do substring(2) because we want to exclude the "0." portion of the number,
			// only count what's after the decimal
			int normalizationFactor = (array[i] + "").substring(2).length();
			output[i] = (int) (array[i] * (Math.pow(10, normalizationFactor)));
		}

		return output; // return the unnormalized array
	}

	// Tests the bucket sort method
	public static void main(String[] args) {
		int[] inputArray = new int[] { 7, 7, 2, 8, 4, 2, 3, 5, 4, 8, 3, 6, 4, 6, 2 }; // unsorted input array 
		System.out.println("Input array: " + Arrays.toString(inputArray)); //Prints the input array

		double[] normalizedInput = normalizeArray(inputArray); // normalizes the array
		System.out.println("Normalized array: " + Arrays.toString(normalizedInput)); //prints the normalized array
		System.out.println();
		
		normalizedInput = bucketSort(normalizedInput); // calls bucket sort on the normalized array
		System.out.println();
		System.out.println("Normalized array: " + Arrays.toString(normalizedInput)); //prints the sorted normalized array

		inputArray = unNormalizeArray(normalizedInput); // unnormalizes the sorted array
		
		// Prints the result of the unnormalized sorted array
		System.out.println("Sorted array: " + Arrays.toString(inputArray));
	}

}
