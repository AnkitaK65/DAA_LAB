package com.ankita65.daa_lab;

/**
 * Red-Black Tree Implementation in Java
 *
 * This program implements insertion in a Red-Black Tree with self-balancing properties.
 * The tree maintains balance by performing rotations and recoloring after insertions.
 * Each node is either Red ('R') or Black ('B').
 *
 * Author: Ankita65
 */
public class RedBlackTreeInsert {

    public Node root;

    public RedBlackTreeInsert() {
        root = null;
    }

    // Node class representing each node in the tree
    class Node {
        int data;
        Node left, right, parent;
        char color; // 'R' for Red, 'B' for Black

        Node(int data) {
            this.data = data;
            this.left = this.right = this.parent = null;
            this.color = 'R'; // New nodes are always Red initially
        }
    }

    // Left rotation
    private Node rotateLeft(Node node) {
        Node x = node.right;
        Node y = x.left;
        x.left = node;
        node.right = y;
        node.parent = x;
        if (y != null)
            y.parent = node;
        return x;
    }

    // Right rotation
    private Node rotateRight(Node node) {
        Node x = node.left;
        Node y = x.right;
        x.right = node;
        node.left = y;
        node.parent = x;
        if (y != null)
            y.parent = node;
        return x;
    }

    // Flags to track rotation cases
    boolean ll = false, rr = false, lr = false, rl = false;

    // Insert helper function
    private Node insertHelper(Node root, int data) {
        boolean conflict = false;

        if (root == null) return new Node(data);

        if (data < root.data) {
            root.left = insertHelper(root.left, data);
            root.left.parent = root;
            if (root.color == 'R' && root.left.color == 'R')
                conflict = true;
        } else {
            root.right = insertHelper(root.right, data);
            root.right.parent = root;
            if (root.color == 'R' && root.right.color == 'R')
                conflict = true;
        }

        // Handling rotations
        if (ll) {
            root = rotateLeft(root);
            root.color = 'B';
            root.left.color = 'R';
            ll = false;
        } else if (rr) {
            root = rotateRight(root);
            root.color = 'B';
            root.right.color = 'R';
            rr = false;
        } else if (rl) {
            root.right = rotateRight(root.right);
            root.right.parent = root;
            root = rotateLeft(root);
            root.color = 'B';
            root.left.color = 'R';
            rl = false;
        } else if (lr) {
            root.left = rotateLeft(root.left);
            root.left.parent = root;
            root = rotateRight(root);
            root.color = 'B';
            root.right.color = 'R';
            lr = false;
        }

        // Handle Red-Red conflict
        if (conflict) {
            if (root.parent.right == root) {
                if (root.parent.left == null || root.parent.left.color == 'B') {
                    if (root.left != null && root.left.color == 'R') rl = true;
                    else if (root.right != null && root.right.color == 'R') ll = true;
                } else {
                    root.parent.left.color = 'B';
                    root.color = 'B';
                    if (root.parent != this.root) root.parent.color = 'R';
                }
            } else {
                if (root.parent.right == null || root.parent.right.color == 'B') {
                    if (root.left != null && root.left.color == 'R') rr = true;
                    else if (root.right != null && root.right.color == 'R') lr = true;
                } else {
                    root.parent.right.color = 'B';
                    root.color = 'B';
                    if (root.parent != this.root) root.parent.color = 'R';
                }
            }
        }
        return root;
    }

    // Insert function
    public void insert(int data) {
        if (root == null) {
            root = new Node(data);
            root.color = 'B';
        } else {
            root = insertHelper(root, data);
        }
    }

    // In-order traversal with node color
    private void inorderTraversalHelper(Node node) {
        if (node != null) {
            inorderTraversalHelper(node.left);
            System.out.printf("%d(%c) ", node.data, node.color);
            inorderTraversalHelper(node.right);
        }
    }

    public void inorderTraversal() {
        inorderTraversalHelper(root);
        System.out.println();
    }

    // Print tree with color
    private void printTreeHelper(Node root, int space) {
        if (root != null) {
            space += 10;
            printTreeHelper(root.right, space);
            System.out.printf("\n");
            for (int i = 10; i < space; i++) {
                System.out.printf(" ");
            }
            System.out.printf("%d(%c)\n", root.data, root.color);
            printTreeHelper(root.left, space);
        }
    }

    public void printTree() {
        printTreeHelper(root, 0);
    }

    // Main function
    public static void main(String[] args) {
        RedBlackTreeInsert t = new RedBlackTreeInsert();
        int[] arr = {1, 4, 6, 3, 5, 7, 8, 2, 9};
        for (int i = 0; i < arr.length; i++) {
            t.insert(arr[i]);
            System.out.println();
            t.inorderTraversal();
        }
        t.printTree();
    }
}
/*
Output:
1(B)

1(B) 4(R)

1(R) 4(B) 6(R)

1(B) 3(R) 4(B) 6(B)

1(B) 3(R) 4(B) 5(R) 6(B)

1(B) 3(R) 4(B) 5(R) 6(B) 7(R)

1(B) 3(R) 4(B) 5(B) 6(R) 7(B) 8(R)

1(R) 2(B) 3(R) 4(B) 5(B) 6(R) 7(B) 8(R)

1(R) 2(B) 3(R) 4(B) 5(B) 6(R) 7(R) 8(B) 9(R)

                              9(R)

                    8(B)

                              7(R)

          6(R)

                    5(B)

4(B)

                    3(R)

          2(B)

                    1(R)
*/