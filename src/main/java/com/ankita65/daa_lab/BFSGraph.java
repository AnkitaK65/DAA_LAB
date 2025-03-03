package com.ankita65.daa_lab;

/**
 * Q7. Implement Breadth-First Search (BFS) in a graph.
 *
 * The program should:
 * - Represent the graph using an adjacency list.
 * - Perform BFS starting from a given source node.
 * - Print the order of nodes visited during BFS traversal.
 */

import java.util.*;

public class BFSGraph {
    private int vertices; // Number of vertices in the graph
    private LinkedList<Integer>[] adjacencyList; // Adjacency list representation of the graph

    /**
     * Constructor to initialize the graph with a given number of vertices.
     * @param vertices Number of vertices in the graph.
     */
    public BFSGraph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    /**
     * Adds an edge between two vertices (for an undirected graph).
     * @param src Source vertex.
     * @param dest Destination vertex.
     */
    public void addEdge(int src, int dest) {
        adjacencyList[src].add(dest);
        adjacencyList[dest].add(src); // Remove this line for a directed graph
    }

    /**
     * Performs Breadth-First Search (BFS) from a given start node.
     * @param startNode The starting vertex for BFS traversal.
     */
    public void bfs(int startNode) {
        boolean[] visited = new boolean[vertices]; // Array to track visited nodes
        Queue<Integer> queue = new LinkedList<>(); // Queue for BFS traversal

        visited[startNode] = true; // Mark the start node as visited
        queue.add(startNode);

        System.out.print("BFS Traversal: ");

        while (!queue.isEmpty()) {
            int node = queue.poll(); // Dequeue a node
            System.out.print(node + " ");

            // Visit all unvisited adjacent nodes and add them to the queue
            for (int neighbor : adjacencyList[node]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Number of vertices and edges
        System.out.print("Enter number of vertices: ");
        int vertices = scanner.nextInt();
        System.out.print("Enter number of edges: ");
        int edges = scanner.nextInt();

        BFSGraph graph = new BFSGraph(vertices);

        // Input: Edges of the graph
        System.out.println("Enter edges (source destination):");
        for (int i = 0; i < edges; i++) {
            int src = scanner.nextInt();
            int dest = scanner.nextInt();
            graph.addEdge(src, dest);
        }

        // Input: Start node for BFS
        System.out.print("Enter start node for BFS: ");
        int startNode = scanner.nextInt();

        // Perform BFS traversal
        graph.bfs(startNode);

        scanner.close();
    }
}
/*
Output:
Enter number of vertices: 6
Enter number of edges: 7
Enter edges (source destination):
0 1
0 2
1 3
1 4
2 4
3 5
4 5
Enter start node for BFS: 0
BFS Traversal: 0 1 2 3 4 5
*/