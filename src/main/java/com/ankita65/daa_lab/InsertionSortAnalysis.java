package com.ankita65.daa_lab;

/*
 * Question 1 (i): Implement Insertion Sort and count the number of comparisons.
 * Test the algorithm on 100 different inputs of sizes varying from 30 to 1000.
 * Count the number of comparisons and draw a graph.
 * Compare it with a graph of n log n.
 */

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.knowm.xchart.*;

public class InsertionSortAnalysis {

    // Method to perform Insertion Sort and count comparisons
    public static int insertionSort(int[] arr) {
        int comparisons = 0;
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            // Move elements that are greater than key to one position ahead
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
                comparisons++; // Count each comparison inside the loop
            }
            arr[j + 1] = key;

            // Count last comparison when exiting loop (if any comparison was made)
            if (j >= 0) comparisons++;
        }
        return comparisons;
    }

    public static void main(String[] args) {
        Random rand = new Random();
        List<Integer> sizes = new ArrayList<>();
        List<Integer> comparisons = new ArrayList<>();
        List<Double> nlogn = new ArrayList<>();

        // Test for input size 30 and display input, output, and comparisons
        int testSize = 30;
        int[] testArr = rand.ints(testSize, 1, 100).toArray(); // Generate random array
        System.out.println("Input Array (Size 30): " + Arrays.toString(testArr));
        int comparisonCount = insertionSort(testArr);
        System.out.println("Sorted Array: " + Arrays.toString(testArr));
        System.out.println("Number of Comparisons: " + comparisonCount);

        // Loop to test sizes from 30 to 1000, increasing by 10 each time
        for (int size = 30; size <= 1000; size += 10) {
            int totalComparisons = 0;

            // Run 100 tests for each array size
            for (int i = 0; i < 100; i++) {
                int[] arr = rand.ints(size, 1, 10000).toArray();
                totalComparisons += insertionSort(arr);
            }

            // Store results for plotting
            sizes.add(size);
            int avgComparisons = totalComparisons / 100;
            comparisons.add(avgComparisons);
            nlogn.add(size * Math.log(size) / Math.log(2)); // Theoretical n log n complexity
        }

        // Create a graph using XChart library
        XYChart chart = new XYChartBuilder()
                .width(800).height(600)
                .title("Insertion Sort vs n log n")
                .xAxisTitle("Array Size")
                .yAxisTitle("Comparisons")
                .build();

        // Add data series for comparisons and theoretical n log n
        chart.addSeries("Insertion Sort Comparisons", sizes, comparisons);
        chart.addSeries("n log n", sizes, nlogn);

        // Display the chart
        new SwingWrapper<>(chart).displayChart();
    }
}

/*
Output:
Input Array (Size 30): [68, 64, 40, 47, 74, 81, 51, 97, 8, 93, 4, 56, 5, 34, 36, 71, 89, 46, 23, 80, 57, 23, 35, 30, 47, 40, 78, 76, 93, 1]
Sorted Array: [1, 4, 5, 8, 23, 23, 30, 34, 35, 36, 40, 40, 46, 47, 47, 51, 56, 57, 64, 68, 71, 74, 76, 78, 80, 81, 89, 93, 93, 97]
Number of Comparisons: 253

And the Graph is displayed.
*/