package com.ankita65.daa_lab;

/*
 * Q6. Write a program to determine the Longest Common Subsequence (LCS) of two given sequences.
 *
 * The program should:
 * - Take two sequences as input.
 * - Compute the length of the LCS.
 * - Display the LCS.
 * - Use dynamic programming for efficient computation.
 */

import java.util.Scanner;

public class LongestCommonSubsequence {

    /**
     * Function to find the Longest Common Subsequence (LCS) of two given sequences.
     * Uses dynamic programming to efficiently compute the LCS.
     *
     * @param X First sequence as a string.
     * @param Y Second sequence as a string.
     * @return The LCS as a string.
     */

    /**
    LCS is the longest subsequence that appears in both sequences in the same order, but not necessarily consecutively.
     Example:
    X = "ABCBDAB"
    Y = "BDCAB"
    LCS = "BCAB"
    */

    public static String findLCS(String X, String Y) {
        int m = X.length();
        int n = Y.length();
        int[][] dp = new int[m + 1][n + 1];

        // Fill dp table using bottom-up approach
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Reconstruct the LCS from the dp table
        int i = m, j = n;
        StringBuilder lcs = new StringBuilder();
        while (i > 0 && j > 0) {
            if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                lcs.append(X.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.reverse().toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take input sequences from user
        System.out.print("Enter first sequence: ");
        String X = scanner.nextLine();

        System.out.print("Enter second sequence: ");
        String Y = scanner.nextLine();

        // Compute LCS
        String lcs = findLCS(X, Y);

        // Display results
        System.out.println("Longest Common Subsequence: " + lcs);

        scanner.close();
    }
}
/*
Output:
Enter first sequence: AGGTAB
Enter second sequence: GXTXAYB
Longest Common Subsequence: GTAB

Enter first sequence: ABC
Enter second sequence: ACD
Longest Common Subsequence: AC

Enter first sequence: ABC
Enter second sequence: CBA
Longest Common Subsequence: C
*/