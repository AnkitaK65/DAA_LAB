package com.ankita65.daa_lab;

/*
 * Question 4: Implement Radix Sort and show the sorting steps.
 * Test the algorithm on different inputs.
 * Display each step of the sorting process.
 */

import java.util.*;

public class RadixSort {

    // Function to get the maximum value in the array
    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    // Function to perform counting sort based on digit place value
    private static void countingSort(int[] arr, int place) {
        int n = arr.length;
        int[] output = new int[n]; // Output array
        int[] count = new int[10]; // Count array for digits 0-9

        // Count occurrences of each digit
        for (int num : arr) {
            int digit = (num / place) % 10;
            count[digit]++;
        }

        // Update count array to store the position of the digits
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / place) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        // Copy the sorted values back to the original array
        System.arraycopy(output, 0, arr, 0, n);

        // Print the step-by-step sorting process
        System.out.println("Sorted by place value " + place + ": " + Arrays.toString(arr));
    }

    // Function to perform Radix Sort
    public static void radixSort(int[] arr) {
        int max = getMax(arr); // Find the maximum number to determine the number of digits

        // Perform counting sort for each digit place (1s, 10s, 100s, etc.)
        for (int place = 1; max / place > 0; place *= 10) {
            countingSort(arr, place);
        }
    }

    public static void main(String[] args) {
        // Example array
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66};

        System.out.println("Original Array: " + Arrays.toString(arr));

        // Perform Radix Sort and show steps
        radixSort(arr);

        System.out.println("Final Sorted Array: " + Arrays.toString(arr));
    }
}
/*
Output:
Original Array: [170, 45, 75, 90, 802, 24, 2, 66]
Sorted by place value 1: [170, 90, 802, 2, 24, 45, 75, 66]
Sorted by place value 10: [802, 2, 24, 45, 66, 170, 75, 90]
Sorted by place value 100: [2, 24, 45, 66, 75, 90, 170, 802]
Final Sorted Array: [2, 24, 45, 66, 75, 90, 170, 802]
*/