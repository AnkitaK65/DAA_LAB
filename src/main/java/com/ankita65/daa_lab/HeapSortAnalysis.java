package com.ankita65.daa_lab;

/*
 * Question 2: Implement Heap Sort and count the number of comparisons.
 * Test the algorithm on 100 different inputs of sizes varying from 30 to 1000.
 * Count the number of comparisons and draw a graph.
 * Compare it with a graph of n log n.
 */

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.knowm.xchart.*;

public class HeapSortAnalysis {
    private static int comparisons;

    // Heap Sort function
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Move the largest element (root) to the end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Heapify the reduced heap
            heapify(arr, i, 0);
        }
    }

    // Heapify a subtree rooted at index i in an array of size n
    private static void heapify(int[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // Left child
        int right = 2 * i + 2; // Right child

        // Compare left child with root
        if (left < n) {
            comparisons++;
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }

        // Compare right child with current largest
        if (right < n) {
            comparisons++;
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }

        // Swap and continue heapifying if needed
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
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
        heapSort(testArr);
        System.out.println("Sorted Array: " + Arrays.toString(testArr));
        System.out.println("Number of Comparisons: " + comparisons);

        // Loop to test sizes from 30 to 1000, increasing by 10 each time
        for (int size = 30; size <= 1000; size += 10) {
            int totalComparisons = 0;

            // Run 100 tests for each array size
            for (int i = 0; i < 100; i++) {
                int[] arr = rand.ints(size, 1, 10000).toArray();
                comparisons = 0;
                heapSort(arr);
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
                .title("Heap Sort vs n log n")
                .xAxisTitle("Array Size")
                .yAxisTitle("Comparisons")
                .build();

        // Add data series for comparisons and theoretical n log n
        chart.addSeries("Heap Sort Comparisons", sizes, comparisonsList);
        chart.addSeries("n log n", sizes, nlogn);

        // Display the chart
        new SwingWrapper<>(chart).displayChart();
    }
}
/*
Output:
Input Array (Size 30): [68, 78, 16, 41, 11, 47, 19, 13, 11, 69, 42, 39, 57, 24, 74, 55, 39, 58, 2, 38, 3, 92, 68, 2, 73, 40, 14, 1, 87, 45]
Sorted Array: [1, 2, 2, 3, 11, 11, 13, 14, 16, 19, 24, 38, 39, 39, 40, 41, 42, 45, 47, 55, 57, 58, 68, 68, 69, 73, 74, 78, 87, 92]
Number of Comparisons: 207
*/