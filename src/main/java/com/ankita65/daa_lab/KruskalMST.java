package com.ankita65.daa_lab;

/*
 * Q9: Write a program to determine the Minimum Spanning Tree (MST) of a graph
 *
 * - Implement Kruskal's Algorithm to find the MST of a given weighted graph.
 * - The program should take input as an edge list with weights.
 * - The output should display the edges included in the MST and the total minimum cost.
 */

import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // Sorting edges based on their weight (for Kruskal's Algorithm)
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

class Subset {
    int parent, rank;
}

class KGraph {
    private int vertices, edges;
    private Edge[] edgeList;

    // Constructor to initialize graph
    public KGraph(int vertices, int edges) {
        this.vertices = vertices;
        this.edges = edges;
        edgeList = new Edge[edges];
    }

    // Add an edge to the graph
    public void addEdge(int index, int src, int dest, int weight) {
        edgeList[index] = new Edge(src, dest, weight);
    }

    // Find the root of a subset (using path compression)
    private int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    // Union of two subsets (by rank)
    private void union(Subset[] subsets, int x, int y) {
        int rootX = find(subsets, x);
        int rootY = find(subsets, y);

        if (subsets[rootX].rank < subsets[rootY].rank) {
            subsets[rootX].parent = rootY;
        } else if (subsets[rootX].rank > subsets[rootY].rank) {
            subsets[rootY].parent = rootX;
        } else {
            subsets[rootY].parent = rootX;
            subsets[rootX].rank++;
        }
    }

    // Function to construct MST using Kruskal's Algorithm
    public void kruskalMST() {
        Arrays.sort(edgeList); // Sort edges based on weight
        Edge[] result = new Edge[vertices - 1]; // Store MST edges
        Subset[] subsets = new Subset[vertices];

        for (int i = 0; i < vertices; i++) {
            subsets[i] = new Subset();
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }

        int edgeCount = 0, index = 0;
        while (edgeCount < vertices - 1 && index < edges) {
            Edge nextEdge = edgeList[index++];
            int x = find(subsets, nextEdge.src);
            int y = find(subsets, nextEdge.dest);

            if (x != y) { // If no cycle is formed
                result[edgeCount++] = nextEdge;
                union(subsets, x, y);
            }
        }

        // Print the MST
        System.out.println("Minimum Spanning Tree:");
        int minCost = 0;
        for (int i = 0; i < edgeCount; i++) {
            System.out.println(result[i].src + " - " + result[i].dest + " : " + result[i].weight);
            minCost += result[i].weight;
        }
        System.out.println("Total Minimum Cost: " + minCost);
    }
}

// Main class to run the MST algorithm
public class KruskalMST {
    public static void main(String[] args) {
        int vertices = 6; // Number of vertices
        int edges = 9; // Number of edges

        KGraph graph = new KGraph(vertices, edges);

        // Adding edges (src, dest, weight)
        graph.addEdge(0, 0, 1, 4);
        graph.addEdge(1, 0, 2, 4);
        graph.addEdge(2, 1, 2, 2);
        graph.addEdge(3, 1, 3, 5);
        graph.addEdge(4, 2, 3, 5);
        graph.addEdge(5, 3, 4, 7);
        graph.addEdge(6, 4, 5, 9);
        graph.addEdge(7, 3, 5, 6);
        graph.addEdge(8, 2, 5, 8);

        // Compute and display MST
        graph.kruskalMST();
    }
}
