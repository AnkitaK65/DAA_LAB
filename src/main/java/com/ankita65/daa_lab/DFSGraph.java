package com.ankita65.daa_lab;

/*
 * Q8: Implement Depth-First Search (DFS) in a Graph
 *
 * - Implement a program to perform DFS traversal on a graph.
 * - The program should allow users to add edges and specify the starting node.
 * - The output should display the DFS traversal order.
 */

import java.util.*;

class Graph {
    private int vertices; // Number of vertices in the graph
    private LinkedList<Integer>[] adjList; // Adjacency list representation

    // Constructor to initialize the graph
    public Graph(int vertices) {
        this.vertices = vertices;
        adjList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    // Method to add an edge to the graph
    public void addEdge(int src, int dest) {
        adjList[src].add(dest); // Add an edge from src to dest
        adjList[dest].add(src); // Since it's an undirected graph, add reverse edge
    }

    // Helper method for DFS
    private void dfsUtil(int vertex, boolean[] visited) {
        // Mark the current node as visited
        visited[vertex] = true;
        System.out.print(vertex + " ");

        // Recur for all adjacent vertices that are not yet visited
        for (int neighbor : adjList[vertex]) {
            if (!visited[neighbor]) {
                dfsUtil(neighbor, visited);
            }
        }
    }

    // DFS traversal starting from a given node
    public void performDFS(int startVertex) {
        boolean[] visited = new boolean[vertices]; // Track visited nodes
        System.out.print("DFS Traversal: ");
        dfsUtil(startVertex, visited);
        System.out.println();
    }
}

// Main class to run the DFS implementation
public class DFSGraph {
    public static void main(String[] args) {
        // Create a graph with 6 vertices
        Graph graph = new Graph(6);

        // Adding edges
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);

        // Perform DFS starting from node 0
        graph.performDFS(0);
    }
}
/*
Output:
DFS Traversal: 0 1 3 5 4 2
*/