package com.ankita65.daa_lab;

/*
 * Question 1 (ii): Implement Merge Sort and count the number of comparisons.
 * Test the algorithm on 100 different inputs of sizes varying from 30 to 1000.
 * Count the number of comparisons and draw a graph.
 * Compare it with a graph of n log n.
 */

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.knowm.xchart.*;

public class MergeSortAnalysis {
    private static int comparisons;

    // Merge Sort function
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Recursively divide the array
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }

    // Merge function to merge two sorted sub-arrays
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        // Copy data to temporary arrays
        System.arraycopy(arr, left, leftArr, 0, n1);
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);

        int i = 0, j = 0, k = left;

        // Merge the temporary arrays back into the original array
        while (i < n1 && j < n2) {
            comparisons++; // Counting comparisons
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        // Copy remaining elements of leftArr (if any)
        while (i < n1) {
            arr[k++] = leftArr[i++];
        }

        // Copy remaining elements of rightArr (if any)
        while (j < n2) {
            arr[k++] = rightArr[j++];
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        List<Integer> sizes = new ArrayList<>();
        List<Integer> comparisonsList = new ArrayList<>();
        List<Double> nlogn = new ArrayList<>();

        // Test for input size 30 and display input, output, and comparisons
        int testSize = 30;
        int[] testArr = rand.ints(testSize, 1, 100).toArray(); // Generate random array
        System.out.println("Input Array (Size 30): " + Arrays.toString(testArr));
        comparisons = 0;
        mergeSort(testArr, 0, testSize - 1);
        System.out.println("Sorted Array: " + Arrays.toString(testArr));
        System.out.println("Number of Comparisons: " + comparisons);

        // Loop to test sizes from 30 to 1000, increasing by 10 each time
        for (int size = 30; size <= 1000; size += 10) {
            int totalComparisons = 0;

            // Run 100 tests for each array size
            for (int i = 0; i < 100; i++) {
                int[] arr = rand.ints(size, 1, 10000).toArray();
                comparisons = 0;
                mergeSort(arr, 0, size - 1);
                totalComparisons += comparisons;
            }

            // Store results for plotting
            sizes.add(size);
            int avgComparisons = totalComparisons / 100;
            comparisonsList.add(avgComparisons);
            nlogn.add(size * Math.log(size) / Math.log(2)); // Theoretical n log n complexity
        }

        // Create a graph using XChart library
        XYChart chart = new XYChartBuilder()
                .width(800).height(600)
                .title("Merge Sort vs n log n")
                .xAxisTitle("Array Size")
                .yAxisTitle("Comparisons")
                .build();

        // Add data series for comparisons and theoretical n log n
        chart.addSeries("Merge Sort Comparisons", sizes, comparisonsList);
        chart.addSeries("n log n", sizes, nlogn);

        // Display the chart
        new SwingWrapper<>(chart).displayChart();
    }
}

/*
Output:
Input Array (Size 30): [98, 24, 82, 68, 29, 88, 28, 42, 61, 61, 60, 33, 31, 29, 31, 26, 6, 22, 3, 34, 43, 92, 89, 53, 84, 81, 22, 14, 76, 63]
Sorted Array: [3, 6, 14, 22, 22, 24, 26, 28, 29, 29, 31, 31, 33, 34, 42, 43, 53, 60, 61, 61, 63, 68, 76, 81, 82, 84, 88, 89, 92, 98]
Number of Comparisons: 106
*/