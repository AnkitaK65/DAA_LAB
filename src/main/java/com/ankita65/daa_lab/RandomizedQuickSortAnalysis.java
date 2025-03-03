package com.ankita65.daa_lab;

/*
 * Question 3: Implement Randomized Quick Sort and count the number of comparisons.
 * Test the algorithm on 100 different inputs of sizes varying from 30 to 1000.
 * Count the number of comparisons and draw a graph.
 * Compare it with a graph of n log n.
 */

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.knowm.xchart.*;

public class RandomizedQuickSortAnalysis {
    private static int comparisons;
    private static Random rand = new Random();

    // Randomized Quick Sort function
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Get the partition index after random pivot selection
            int pi = randomizedPartition(arr, low, high);

            // Recursively sort elements before and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    // Partition function with random pivot selection
    private static int randomizedPartition(int[] arr, int low, int high) {
        int randomPivotIndex = low + rand.nextInt(high - low + 1);
        swap(arr, randomPivotIndex, high); // Swap random pivot with last element
        return partition(arr, low, high);
    }

    // Standard partition function (Lomuto partition scheme)
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            comparisons++;
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // Swap function
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
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
        quickSort(testArr, 0, testArr.length - 1);
        System.out.println("Sorted Array: " + Arrays.toString(testArr));
        System.out.println("Number of Comparisons: " + comparisons);

        // Loop to test sizes from 30 to 1000, increasing by 10 each time
        for (int size = 30; size <= 1000; size += 10) {
            int totalComparisons = 0;

            // Run 100 tests for each array size
            for (int i = 0; i < 100; i++) {
                int[] arr = rand.ints(size, 1, 10000).toArray();
                comparisons = 0;
                quickSort(arr, 0, arr.length - 1);
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
                .title("Randomized Quick Sort vs n log n")
                .xAxisTitle("Array Size")
                .yAxisTitle("Comparisons")
                .build();

        // Add data series for comparisons and theoretical n log n
        chart.addSeries("Quick Sort Comparisons", sizes, comparisonsList);
        chart.addSeries("n log n", sizes, nlogn);

        // Display the chart
        new SwingWrapper<>(chart).displayChart();
    }
}
/*
Output:
Input Array (Size 30): [6, 75, 80, 99, 88, 44, 30, 43, 67, 76, 39, 82, 96, 4, 74, 61, 38, 40, 99, 27, 35, 70, 50, 62, 30, 99, 93, 47, 58, 5]
Sorted Array: [4, 5, 6, 27, 30, 30, 35, 38, 39, 40, 43, 44, 47, 50, 58, 61, 62, 67, 70, 74, 75, 76, 80, 82, 88, 93, 96, 99, 99, 99]
Number of Comparisons: 113
*/