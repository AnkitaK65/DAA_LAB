package com.ankita65.daa_lab;

class RedBlackTreeDelete {
    private class Node {
        int data;
        Node parent, left, right;
        boolean isRed;

        public Node(int data) {
            this.data = data;
            this.isRed = true; // New nodes are always red initially
            left = right = parent = null;
        }
    }

    private Node root;
    private Node TNULL; // Null leaf node

    public RedBlackTreeDelete() {
        TNULL = new Node(0);
        TNULL.isRed = false;
        root = TNULL;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != TNULL) {
            x.right.parent = y;
        }
        x.parent = y.parent;
        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }
        x.right = y;
        y.parent = x;
    }

    private void insertFixup(Node node) {
        while (node.parent != null && node.parent.isRed) {
            if (node.parent == node.parent.parent.left) {
                Node uncle = node.parent.parent.right;
                if (uncle.isRed) {
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    rightRotate(node.parent.parent);
                }
            } else {
                Node uncle = node.parent.parent.left;
                if (uncle.isRed) {
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    leftRotate(node.parent.parent);
                }
            }
        }
        root.isRed = false;
    }

    public void insert(int key) {
        Node newNode = new Node(key);
        Node y = null;
        Node x = root;

        while (x != TNULL) {
            y = x;
            if (newNode.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        newNode.parent = y;
        if (y == null) {
            root = newNode;
        } else if (newNode.data < y.data) {
            y.left = newNode;
        } else {
            y.right = newNode;
        }

        newNode.left = TNULL;
        newNode.right = TNULL;
        newNode.isRed = true;

        insertFixup(newNode);
    }

    private void transplant(Node u, Node v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private void deleteFixup(Node x) {
        while (x != root && !x.isRed) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.isRed) {
                    w.isRed = false;
                    x.parent.isRed = true;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (!w.left.isRed && !w.right.isRed) {
                    w.isRed = true;
                    x = x.parent;
                } else {
                    if (!w.right.isRed) {
                        w.left.isRed = false;
                        w.isRed = true;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.isRed = x.parent.isRed;
                    x.parent.isRed = false;
                    w.right.isRed = false;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;
                if (w.isRed) {
                    w.isRed = false;
                    x.parent.isRed = true;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (!w.right.isRed && !w.left.isRed) {
                    w.isRed = true;
                    x = x.parent;
                } else {
                    if (!w.left.isRed) {
                        w.right.isRed = false;
                        w.isRed = true;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.isRed = x.parent.isRed;
                    x.parent.isRed = false;
                    w.left.isRed = false;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.isRed = false;
    }

    private Node minimum(Node node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    public void delete(int key) {
        Node z = root;
        while (z != TNULL) {
            if (z.data == key) break;
            z = (key < z.data) ? z.left : z.right;
        }

        if (z == TNULL) return;

        Node y = z;
        Node x;
        boolean yOriginalColor = y.isRed;

        if (z.left == TNULL) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.isRed;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.isRed = z.isRed;
        }

        if (!yOriginalColor) {
            deleteFixup(x);
        }
    }

    public void displayTree(Node node, String indent, boolean last) {
        if (node != TNULL) {
            System.out.println(indent + (last ? "└── " : "├── ") + node.data + (node.isRed ? " (Red)" : " (Black)"));
            displayTree(node.left, indent + (last ? "    " : "│   "), false);
            displayTree(node.right, indent + (last ? "    " : "│   "), true);
        }
    }

    public void printTree() {
        displayTree(root, "", true);
    }

    public static void main(String[] args) {
        RedBlackTreeDelete tree = new RedBlackTreeDelete();
        tree.insert(20);
        tree.insert(15);
        tree.insert(25);
        tree.insert(10);
        tree.insert(5);
        System.out.println("Tree before deletion:");
        tree.printTree();

        tree.delete(15);
        System.out.println("\nTree after deleting 15:");
        tree.printTree();
    }
}
