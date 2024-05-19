import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.assertEquals;
import static  org.junit.jupiter.api.Assertions.assertTrue;
import static  org.junit.jupiter.api.Assertions.assertFalse;


public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T>{
    /**
     * makes an RBT node and makes references to its node surroundings
     * // - down[0] is the left child reference of the node,
        // - down[1] is the right child reference of the node.
   */ 
  protected static class RBTNode<T> extends Node<T> {
    public int blackHeight = 0;
    public RBTNode(T data) { super(data); }
    public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
    public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
    public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
}
  
  /** fixes RBT so no violations 
   */ 
  protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> insertedNode) {
     
    //checks if the inserted node is the root node and sets to black 
    if(insertedNode == this.root) { 
      insertedNode.blackHeight = 1;
      return;
    }
    
    //checks if parent is to the left of the grandparent
    boolean left = false; 
    //checks if parent is to the right of the grandparent
    boolean right = false;

    //initializing different nodes of family for easier access
    RBTNode<T> child = insertedNode;
    RBTNode<T> parent = insertedNode.getUp();
    RBTNode<T> sibling = null;
    RBTNode<T> grandparent = null;
    if(parent.getUp() != null) {
      grandparent = parent.getUp();
    }

    if(grandparent != null && parent == grandparent.getDownLeft()){
        sibling = grandparent.getDownRight();
        left = true;
    }
    else if (grandparent != null && parent == grandparent.getDownRight()){
        sibling = grandparent.getDownLeft();
        right = true;
    }
    
  //case 1: Parent is red and is grandparent's left child, child is red and is parent's left child, and sibling is black
    if(parent.blackHeight == 0 && child == parent.getDownLeft() && left && (sibling == null || sibling.blackHeight == 1)){
        grandparent.blackHeight = 0;
        parent.blackHeight = 1;
        rotate(parent, grandparent);
    }

    //case 1: Parent is red and is grandparent's right child, child is red and is parent's right child, and sibling is black
    if(parent.blackHeight == 0 && child == parent.getDownRight() && right && (sibling == null || sibling.blackHeight == 1)){
        grandparent.blackHeight = 0;
        parent.blackHeight = 1;
        rotate(parent, grandparent);
    }

    //case 2: Parent is red and is grandparent's left child, child is red and is parent's right child, sibling is black
    if(parent.blackHeight == 0 && child == parent.getDownRight() && left && (sibling == null ||sibling.blackHeight == 1)){
        rotate(child, parent);
        grandparent.blackHeight = 0;
        child.blackHeight = 1;
        rotate(child, grandparent);
    }

  //case 2: Parent is red and is grandparent's right child, child is red and is parent's left child, sibling is black
    if(parent.blackHeight == 0 && child == parent.getDownLeft() && right && (sibling == null || sibling.blackHeight == 1)){
        rotate(child, parent);
        grandparent.blackHeight = 0;
        child.blackHeight = 1;
        rotate(child, grandparent);
    }

    //Case 3: Parent is red, sibling is red, child is same side as parent is to grandparent
    if(parent.blackHeight == 0 && child == parent.getDownLeft() && left && sibling.blackHeight == 0){
        sibling.blackHeight = 1;
        parent.blackHeight = 1;
        grandparent.blackHeight = 0; //could cause a red parent child violation, recursive call required.
        enforceRBTreePropertiesAfterInsert(grandparent);
    }

    //Case 3: Parent is red, sibling is red, child is same side as parent is to grandparent
    if(parent.blackHeight == 0 && child == parent.getDownRight() && right && sibling.blackHeight == 0){
        sibling.blackHeight = 1;
        parent.blackHeight = 1;
        grandparent.blackHeight = 0; //could cause a red parent child violation, recursive call required.
        enforceRBTreePropertiesAfterInsert(grandparent);
    }

    //Case 3: Parent is red, sibling is red, child is opposite side as parent is to grandparent
    if(parent.blackHeight == 0 && child == parent.getDownRight() && left && sibling.blackHeight == 0){
        // rotate(child, parent);
        sibling.blackHeight = 1;
        parent.blackHeight = 1;
        grandparent.blackHeight = 0; //could cause a red parent child violation, recursive call required.
        enforceRBTreePropertiesAfterInsert(grandparent);
    }

       //Case 3: Parent is red, sibling is red, child is opposite side as parent is to grandparent
    if(parent.blackHeight == 0 && child == parent.getDownLeft() && right && sibling.blackHeight == 0){
        // rotate(child, parent);
        sibling.blackHeight = 1;
        parent.blackHeight = 1;
        grandparent.blackHeight = 0; 
       //recursive call in case of future violations
        enforceRBTreePropertiesAfterInsert(grandparent);
    }

    //changes root node to black 
    ((RBTNode<T>)root).blackHeight = 1;  
  }
  
  /** inserts nodes into tree
   * throws NullPointerException if data is null 
   * returns true or false if insertion successful
   */ 
  public boolean insert(T data) throws NullPointerException {
    //checks if data is null
    if(data == null) {
      throw new NullPointerException("node is null");
    } 
      //makes new insrted node for RBT
    RBTNode<T> insertedNode = new RBTNode<T>(data); 
      
    //checks if successful by referencing insert helper method
    boolean insertionSuccessful = super.insertHelper(insertedNode);  
      
    //if successful, enforces fixing property method and sets root node to black
      if(insertionSuccessful) {
        enforceRBTreePropertiesAfterInsert(insertedNode); 
        ((RBTNode<T>)root).blackHeight = 1; 
    }
      return insertionSuccessful; 
    
    }
  
  /** Tests an empty tree insertion 
   * returns true if insertion works properly, otherwise false
   */ 
  @Test
  public void testEmptyTreeInsertion() {
    RedBlackTree<Integer> RBTree = new RedBlackTree<>(); 
    RBTree.insert(7); 
    RBTree.insert(14); 
    //       7 (black) 
  //          \
    //          14 (red)     
    assertEquals("[ 7, 14 ]", RBTree.toLevelOrderString(), "The initial state of the tree is incorrect");
  }
  
  /** Tests an insertion where the inserted node's aunt is red to see if proper 
   * color swap and rotations occur
   * returns true if insertion works properly, otherwise false
   */ 
  @Test
  public void testRedAuntTreeInsertion() {
    RedBlackTree<Integer> RBTree = new RedBlackTree<>();
    RBTree.insert(7);
    RBTree.insert(14);
    RBTree.insert(18); 
    RBTree.insert(23); 
    //            14 (black)
    //         /       \
    //    (black) 7    18(black) 
    //                    \
    //                     23 (red) 
      
    assertEquals("[ 14, 7, 18, 23 ]", RBTree.toLevelOrderString(), "The initial state of the tree is incorrect");
  
    
  }
  
  /** Tests an insertion where the inserted node's aunt is black to see if proper 
   * color swap and rotations occur
   * returns true if insertion works properly, otherwise false
   */ 
  @Test
  public void testBlackAuntTreeInsertion() {
    RedBlackTree<Integer> RBTree = new RedBlackTree<>();
    RBTree.insert(7);
    RBTree.insert(14);
    RBTree.insert(18); 
    RBTree.insert(23); 
    RBTree.insert(1); 
    RBTree.insert(11); 
    RBTree.insert(20);
    //            14 (B)
    //         /         \
    //    (B)7          20(B) 
    //        / \       /     \
    //    (r)1   (r)11   18(r)  23 (r) 
    assertEquals("[ 14, 7, 20, 1, 11, 18, 23 ]", RBTree.toLevelOrderString(), "The initial state of the tree is incorrect");
    
  }
  
  //try catch statement to catch exceptions
  
  
  
  
}
