
import java.util.LinkedList;
import java.util.Stack;


/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes of the tree. 
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree.
     */
    protected static class Node<T> {
        public T data;

        // up stores a reference to the node's parent
        public Node<T> up;
        // The down array stores references to the node's children:
        // - down[0] is the left child reference of the node,
        // - down[1] is the right child reference of the node.
        @SuppressWarnings("unchecked")
        public Node<T>[] down = (Node<T>[])new Node[2];
        public Node(T data) { this.data = data; }
        
        /**
         * @return true when this node has a parent and is the right child of
         * that parent, otherwise return false
         */
        public boolean isRightChild() {
            return this.up != null && this.up.down[1] == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Inserts a new data value into the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided data argument is null
     */
    @Override
    public boolean insert(T data) throws NullPointerException {
        if (data == null)
            throw new NullPointerException("Cannot insert data value null into the tree.");
        return this.insertHelper(new Node<>(data));
    }

    /**
     * Performs a naive insertion into a binary search tree: adding the new node
     * in a leaf position within the tree. After this insertion, no attempt is made
     * to restructure or balance the tree.
     * @param node the new node to be inserted
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided node is null
     */
    protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
        if(newNode == null) throw new NullPointerException("new node cannot be null");

        if (this.root == null) {
            // add first node to an empty tree
            root = newNode;
            size++;
            return true;
        } else {
            // insert into subtree
            Node<T> current = this.root;
            while (true) {
                int compare = newNode.data.compareTo(current.data);
                if (compare == 0) {
                    return false;
                } else if (compare < 0) {
                    // insert in left subtree
                    if (current.down[0] == null) {
                        // empty space to insert into
                        current.down[0] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[0];
                    }
                } else {
                    // insert in right subtree
                    if (current.down[1] == null) {
                        // empty space to insert into
                        current.down[1] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[1]; 
                    }
                }
            }
        }
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * right child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    protected void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
       //checks if parent's left and right children are the same related ones and throws
       //exception otherwise
        if ((parent.down[1] != child) && (parent.down[0] != child)) {
            throw new IllegalArgumentException("child and parent are not related"); 
        }
        
        if (child.isRightChild()) {
            //right child -> left rotation
            //creates a temporary child variable to change references
            Node<T> temp = child; 
            //sets child's left child equal to parent's right child reference 
            parent.down[1] = temp.down[0]; 
            //if child has another left child, sets that child to the parent 
            if(temp.down[0] != null) {
                temp.down[0].up = parent; 
            }
    
            //makes the child's parent, the old parent's parent 
            temp.up = parent.up; 
           //if there is no parent, the child becomes the root
            if(temp.up == null) {
                root = temp; 
            }
            //if the parent is a left child of the parent's parent, the parent gets 
            //connected as child
            else if (parent == parent.up.down[0]) {
                parent.up.down[0] = temp;
            }
            //if the parent is a right child of the parent's parent, the parent gets 
            //connected as child
            else {
                parent.up.down[1] = temp;
            }
            //left child becomes parent
            temp.down[0] = parent; 
            //parents parent becomes child  
            parent.up = temp; 

        }
        else {
            //creates a temporary child variable to change references
            Node<T> temp = child; 
            //sets child's right child equal to parent's left child reference 
            parent.down[0] = temp.down[1]; 
            //if child has another right child, sets that child to the parent 
            if(temp.down[1] != null) {
                temp.down[1].up = parent; 
            }
    
            //makes the child's parent, the old parent's parent 
            temp.up = parent.up; 
           //if there is no parent, the child becomes the root
            if(temp.up == null) {
                root = temp; 
            }
            //if the parent is a right child of the parent's parent, the parent gets 
            //connected as child
            else if (parent == parent.up.down[1]) {
                parent.up.down[1] = temp;
            }
            //if the parent is a left child of the parent's parent, the parent gets 
            //connected as child
            else {
                parent.up.down[0] = temp;
            }
            //right child becomes parent
            temp.down[1] = parent; 
            //parents parent becomes child  
            parent.up = temp;
            
        }
    
         
    }

    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() returns 0, false if this.size() != 0
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data a comparable for the data value to check for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    public boolean contains(Comparable<T> data) {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This tree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNode(data);
            // return false if the node is null, true otherwise
            return (nodeWithData != null);
        }
    }

    /**
     * Removes all keys from the tree.
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Helper method that will return the node in the tree that contains a specific
     * key. Returns null if there is no node that contains the key.
     * @param data the data value for which we want to find the node that contains it
     * @return the node that contains the data value or null if there is no such node
     */
    protected Node<T> findNode(Comparable<T> data) {
        Node<T> current = this.root;
        while (current != null) {
            int compare = data.compareTo(current.data);
            if (compare == 0) {
                // we found our value
                return current;
            } else if (compare < 0) {
                if (current.down[0] == null) {
                    // we have hit a null node and did not find our node
                    return null;
                }
                // keep looking in the left subtree
                current = current.down[0];
            } else {
                if (current.down[1] == null) {
                    // we have hit a null node and did not find our node
                    return null;
                }
                // keep looking in the right subtree
                current = current.down[1];
            }
        }
        return null;
    }

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    public String toInOrderString() {
        // generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            Stack<Node<T>> nodeStack = new Stack<>();
            Node<T> current = this.root;
            while (!nodeStack.isEmpty() || current != null) {
                if (current == null) {
                    Node<T> popped = nodeStack.pop();
                    sb.append(popped.data.toString());
                    if(!nodeStack.isEmpty() || popped.down[1] != null) sb.append(", ");
                    current = popped.down[1];
                } else {
                    nodeStack.add(current);
                    current = current.down[0];
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the tree. The string
     * representations of each data value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * This method will be helpful as a helper for the debugging and testing
     * of your rotation implementation.
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.down[0] != null) q.add(next.down[0]);
                if(next.down[1] != null) q.add(next.down[1]);
                sb.append(next.data.toString());
                if(!q.isEmpty()) sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }


    /**
     * This method tests if rotations are properly printed out in level order traversal
     *  and in order traversal, when one of the nodes involved in rotating is at the 
     * root of the BST. Left child -> right rotation
     * @return true if the actual output and expected output match, or false otherwise 
     */ 
    public static boolean test1() {
        //creates a bst 
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        //inserts values into the bst to be rotated
        tree.insert(41);
        tree.insert(27);
        tree.insert(68);
        tree.insert(18);
        tree.insert(34);
        tree.insert(52);
        tree.insert(77);
       
        System.out.println(tree.root.down[0].data);
        System.out.println(tree.root.data);
        //rotates the left child and root node
        tree.rotate(tree.root.down[0],tree.root);
       //expected value is the code's to string that prints level order and in order traversal
        String actual = tree.toString(); 
        System.out.println(actual); 
        
        //actual value is the actual code that should be printed out to check if it matchs the expected
        String expected = "level order: " + "[ 27, 18, 41, 34, 68, 52, 77 ]" +
        "\nin order: " + "[ 18, 27, 34, 41, 52, 68, 77 ]";
        
        //if they don't match, return false. otherwise, return true. 
        if (!actual.equals(expected)) {
            return false;
        }
        return true;
    }
    
    /**
     * This method tests if rotations are properly printed out in level order traversal
     *  and in order traversal, when one of the nodes involved in rotating is at the 
     * root of the BST as a right child - left rotation
     * @return true if the actual output and expected output match, or false otherwise 
     */
    public static boolean test2() {
        //creates a bst 
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        //inserts values into the bst to be rotated
        tree.insert(41);
        tree.insert(27);
        tree.insert(68);
        tree.insert(18);
        tree.insert(34);
        tree.insert(52);
        tree.insert(77);
       
        System.out.println(tree.root.down[0].data);
        System.out.println(tree.root.data);
        //rotates the right child and root node
        tree.rotate(tree.root.down[1],tree.root);
       //expected value is the code's to string that prints level order and in order traversal
        String actual = tree.toString(); 
        System.out.println(actual); 
        
        //actual value is the actual code that should be printed out to check if it matchs the expected
        String expected = "level order: " + "[ 68, 41, 77, 27, 52, 18, 34 ]" +
        "\nin order: " + "[ 18, 27, 34, 41, 52, 68, 77 ]";
        
        //if they don't match, return false. otherwise, return true. 
        if (!actual.equals(expected)) {
            return false;
        }
        return true;
    }
    
     /**
     * This method tests if rotations are properly printed out in level order traversal
     *  and in order traversal, when one of the child nodes is not connected to the parent
     * @return true if the actual output and expected output match, or false otherwise 
     */
    public static boolean test3() {
        //creates a bst 
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        //inserts values into the bst to be rotated
        tree.insert(41);
        tree.insert(27);
        tree.insert(68);
        tree.insert(18);
        tree.insert(34);
        tree.insert(52);
        tree.insert(77);

        //catches the exception if parent and child are not related 
        try {
            //rotates the  left child's child and root node
            tree.rotate(tree.root.down[0].down[0],tree.root);
            return false;
        } catch (Exception e) {
            
        }
       
    
        return true;
    }

    /**
     * This method tests if rotations are properly printed out in level order traversal
     *  and in order traversal, when the nodes are not at the root of the tree
     * @return true if the actual output and expected output match, or false otherwise 
     */
    public static boolean test4() {
        //creates a bst 
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        //inserts values into the bst to be rotated
        tree.insert(41);
        tree.insert(27);
        tree.insert(68);
        tree.insert(18);
        tree.insert(34);
        tree.insert(52);
        tree.insert(77);
       
        System.out.println(tree.root.down[0].data);
        System.out.println(tree.root.data);
        //rotates the left child's left child and left child
        tree.rotate(tree.root.down[0].down[0],tree.root.down[0]);
       //expected value is the code's to string that prints level order and in order traversal
        String actual = tree.toString(); 
        System.out.println(actual); 
        
        //actual value is the actual code that should be printed out to check if it matchs the expected
        String expected = "level order: " + "[ 41, 18, 68, 27, 52, 77, 34 ]" +
        "\nin order: " + "[ 18, 27, 34, 41, 52, 68, 77 ]";
        
        //if they don't match, return false. otherwise, return true. 
        if (!actual.equals(expected)) {
            return false;
        }
        return true;
    }
    
    /**
     * Main method to run tests. If you'd like to add additional test methods, add a line for each
     * of them.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Test 1 passed: " + test1());
        System.out.println("Test 2 passed: " + test2());
        System.out.println("Test 3 passed: " + test3());
        System.out.println("Test 4 passed: " + test4());
    }

}
