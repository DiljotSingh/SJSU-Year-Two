import java.util.Random;

/**
 * Implementation of the Radix Sort Algorithm
 * 
 * @author Diljot Singh
 *
 */
public class RadixSort {

	/**
	 * Generates a random 5 digit hex number 
	 * Range for 5 digit hex numbers is [0x10000, 0xFFFFF]
	 * 
	 * @return the generated number
	 */
	public static int generateFiveDigitHexNumber() {
		Random randomObject = new Random(); // Random object to generate random numbers

		final int MAX_VALUE = 0xFFFFF; // Largest five digit hex number is 0xFFFFF
		final int MIN_VALUE = 0x10000; // Smallest nonzero five digit hex number is 0x10000

		// Return a random hex number in the range [0x10000, 0xFFFFF]
		return MIN_VALUE + randomObject.nextInt(MAX_VALUE - MIN_VALUE + 1);
	}

	
	/**
	 * Sorts an array using radix sort
	 * 
	 * @param array the array to sort
	 * @param digits the maximum number of digits a value can have
	 * @return array after it has been sorted
	 */
	public static int[] radixSort(int[] array, int digits) {

		//Call counting sort to sort the array on each digit 
		for (int i = 0; i <= digits; i++) {
			
			int[] arrayB = new int[array.length]; // array B used in counting sort
			
			//We pass 16^i to counting sort to sort the correct hexadecimal place in each iteration (starting from LSD)
			array = countingSort(array, arrayB, (int) Math.pow(16, i)); //Calls counting sort on the array for that hexadecimal place
		}
		
		return array; //Return the fully sorted array (all passes complete)

	}

	// Uses counting sort to sort an array at a specific digit place
	public static int[] countingSort(int[] arrayA, int[] arrayB, int hexadecimalPlace) {
		
		final int RANGE = 16; // Digits can range from 0 to F in hexadecimal notation, which signifies 16 possible values
		int[] auxiliaryStorage = new int[RANGE+1]; // Create an auxiliary array to help with the sorting

		// Fill each index of the auxiliary storage array with 0's
		for (int i = 0; i <= RANGE; i++) {
			auxiliaryStorage[i] = 0;
		}

		// auxiliaryStorage[i] now contains the number of elements equal to i
		for (int i = 0; i < arrayA.length; i++) {
			int currentDigit = (arrayA[i] / hexadecimalPlace) % RANGE; //Calculates the current digit based on the hexadecimal place
			auxiliaryStorage[currentDigit] = auxiliaryStorage[currentDigit] + 1;
		}

		// auxiliaryStorage[i] now contains the number of elements less than or equal to i
		for (int i = 1; i <= RANGE; i++) {
			auxiliaryStorage[i] = auxiliaryStorage[i] + auxiliaryStorage[i - 1];
		}
		
		// Places each element from arrayA into the right position in arrayB (sorts)
		for (int j = arrayA.length - 1; j >= 0; j--) {
			
			int currentDigit = (arrayA[j] / hexadecimalPlace) % RANGE; //Calculates the current digit based on the hexadecimal place
			arrayB[auxiliaryStorage[currentDigit] - 1 ] = arrayA[j]; // Places arrayA[j] into the correct position in arrayB based on the counter											
			auxiliaryStorage[currentDigit] = auxiliaryStorage[currentDigit] - 1; // Decrements counter in auxiliary array
		}
		
		return arrayB; //Returns the sorted array for this hexadecimal place (output array B)

	}

	// Tests the radix sort method
	public static void main(String[] args) {

		// Fill an array with 30 randomly generated five digit hex numbers
		int[] testArray = new int[30];
		for (int i = 0; i < 30; i++) {
			testArray[i] = generateFiveDigitHexNumber(); //Generates random hex number and places it into the array
		}

		//Prints the array's contents before sorting
		System.out.println("Before sorting: ");
		for (int number : testArray)
		{
			System.out.println(Integer.toHexString(number)); //Number is printed in hexadecimal representation
			
		}
		
		
		 // Call radix sort on the array
		 // Digit parameter is '5' because each generated hexadecimal number has five digits
		testArray = radixSort(testArray, 5); //Radix sort call
		
		System.out.println(); 
		
		//Prints the arrays contents after sorting
		System.out.println("After sorting: ");
		for (int number : testArray)
		{
			System.out.println(Integer.toHexString(number)); //Number is printed in hexadecimal representation
		}

	}

}
