 // --== CS400 Fall 2023 File Header Information ==--
// Name: Diya Gopinath
// Email: dgopinath2@wisc.edu
// Group: A09
// TA: Connor Bailey
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class IterableMultiKeyRBT<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>> implements IterableMultiKeySortedCollectionInterface<T>{

    //stores iteration's start point
    private Comparable<T> startPoint; 
    //stores number of keys   
    private int numKeys; 

    /**
     * Inserts value into tree that can store multiple objects per key by keeping
     * lists of objects in each node of the tree.
     * @param key object to insert
     * @return true if a new node was inserted, false if the key was added into an existing node
     */
    @Override
    public boolean insertSingleKey(T key) {
    //creates a KeyList with the new key
        KeyListInterface<T> keyList = new KeyList<T>(key);
        keyList.addKey(key);

    //searches tree for duplicate nodes 
    Node<KeyListInterface<T>> node = findNode(keyList);

    if (node != null) {
        // adds key of duplicate node
        node.data.addKey(key);
        return false;
    } 
    else {
        //inserts new keylist into node if no duplicates
        insert(keyList);
        numKeys++; // Increment the count of keys
        return true;
    }
    }

    /**
     * @return the number of values in the tree.
     */
    public int numKeys(){
        return numKeys; 
    }

    /**
     * Protected helper method that returns an instance of stack containing nodes
     * after initialization
     */
    protected Stack<Node<KeyListInterface<T>>> getStartStack(){
        Stack<Node<KeyListInterface<T>>> stack = new Stack<>();
        Node<KeyListInterface<T>> currNode = root; 
        if(startPoint == null) {
            //stack is initialized with nodes on the path from root node
            //to and including node with smallest key in the tree
            while(currNode != null) {
                stack.push(currNode); 
                currNode = currNode.down[0]; 
            }
        }
        else {
            //stack is initialized with all the nodes with keys equl or larger
            //than the start point along the path of search for start point
            while (currNode != null) {
                int compareToVal = startPoint.compareTo(currNode.data.iterator().next());
                if (compareToVal < 0) {
                    currNode = currNode.down[0];
                } else {
                    stack.push(currNode);
                    currNode = currNode.down[1];
                }
            }
        }
        return stack;
    }

    

    // /**
    //  * Returns an iterator that does an in-order iterator over the tree.
    //  */
   
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Stack<Node<KeyListInterface<T>>> stack = getStartStack();
            private T nextNode = null;

         @Override
         public boolean hasNext() {
            while (nextNode == null && !stack.isEmpty()) {
                Node<KeyListInterface<T>> currentNode = stack.pop();
                KeyListInterface<T> keyList = currentNode.data;

                if (keyList.iterator().hasNext()) {
                    nextNode = keyList.iterator().next();
                }

                // Push the right subtree for in-order traversal
                Node<KeyListInterface<T>> rightSubtree = currentNode.down[1];
                while (rightSubtree != null) {
                    stack.push(rightSubtree);
                    rightSubtree = rightSubtree.down[0];
                }
            }
            return nextNode != null;
        }

        @Override
        public T next() throws NoSuchElementException{
            if (!hasNext()) {
                throw new NoSuchElementException("there is no next node");
            }
            T element = nextNode;
            nextNode = null;
            return element;
        }
    };
}

    /**
     * Sets the starting point for iterators. Future iterators will start at the
     * starting point or the key closest to it in the tree. This setting is remembered
     * until it is reset. Passing in null disables the starting point.
     * @param startPoint the start point to set for iterators
     */

     @Override
    public void setIterationStartPoint(Comparable<T> startPoint) {
        this.startPoint = startPoint; 
    }

    @Override
    public void clear(){
        super.clear(); 
        numKeys = 0; 
    }


    /**
     * This test method checks whether the iterator iterates in the correct order
     * returns true if done properly, returns false otherwise
     */
    @Test
    public void testInsertSingleKey() {
        IterableMultiKeyRBT<Integer> iteratingRBT = new IterableMultiKeyRBT<>();
        iteratingRBT.insertSingleKey(4);
        iteratingRBT.insertSingleKey(7);
        iteratingRBT.insertSingleKey(1);

        Iterator<Integer> iterator = iteratingRBT.iterator();

        Assertions.assertEquals(1, iterator.next());
        Assertions.assertEquals(4, iterator.next());
        Assertions.assertEquals(7, iterator.next());
        Assertions.assertFalse(iterator.hasNext());
    }

    /**
     * This test method tests if number of keys is properly returned
     * returns true if done properly, returns false otherwise
     */
    @Test
    public void testNumKeys(){
        IterableMultiKeyRBT<Integer> iteratingRBT = new IterableMultiKeyRBT<>();
        iteratingRBT.insertSingleKey(1);
        iteratingRBT.insertSingleKey(4);
        iteratingRBT.insertSingleKey(7);
        
        if (iteratingRBT.numKeys() != 3){
            Assertions.fail();
        }
    }

    /**
     * This test method checks whether the iterator start point is set properly
     * returns true if done properly, returns false otherwise
     */
    @Test
    public void testSetIteratorStartPoint() {
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();
        tree.insertSingleKey(1);
        tree.insertSingleKey(4);
        tree.insertSingleKey(7);

        tree.setIterationStartPoint(4);
        Iterator<Integer> iterator = tree.iterator();

        //start point should start at 4 and do 4 and then 7
        Assertions.assertEquals(4, iterator.next());
        Assertions.assertEquals(7, iterator.next());
        Assertions.assertFalse(iterator.hasNext());

    }

    /**
     * This test method checks whether the iterator start point is set properly when its null
     * returns true if done properly, returns false otherwise
     */
    @Test
    public void testSetIteratorStartPoint2() {
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();
        tree.insertSingleKey(1);
        tree.insertSingleKey(4);
        tree.insertSingleKey(7);
        Iterator<Integer> iterator = tree.iterator();
        //start point should start at null and do 1 and then 4 and then 7
        tree.setIterationStartPoint(null);
        Assertions.assertEquals(1,iterator.next());
        Assertions.assertEquals(4,iterator.next());
        Assertions.assertEquals(7,iterator.next());
        Assertions.assertFalse(iterator.hasNext());

        // Attempting to get the next element should throw a NoSuchElementException
        try {
            iterator.next();
            Assertions.fail("Expected NoSuchElementException but no exception was thrown");
         } catch (NoSuchElementException e) {
        // Expected NoSuchElementException
        }
    }
    

    /**
     * This test method checks it iterator works properly with the exception thrown
     * returns true if done properly, returns false otherwise
     */
    @Test
    public void testIteratorWithNoSuchElementException() {
     IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();
        tree.insertSingleKey(1);
        tree.insertSingleKey(4); 
    Iterator<Integer> iterator = tree.iterator();

    //is expected to iterate thourhg 1 and 4 properly
    Assertions.assertTrue(iterator.hasNext());
    Assertions.assertEquals(1, iterator.next());

    Assertions.assertTrue(iterator.hasNext());
    Assertions.assertEquals(4, iterator.next());

    Assertions.assertFalse(iterator.hasNext());

     //there should be no elements after 5 now so should throw exception
    try {
        iterator.next();
        Assertions.fail("Expected NoSuchElementException but no exception was thrown");
    } catch (NoSuchElementException e) {
        // fail("didn't work"); 
    }
}



}
