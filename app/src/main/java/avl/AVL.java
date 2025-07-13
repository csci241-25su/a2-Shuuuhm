package avl;

public class AVL {

  public Node root;

  private int size;

  public int getSize() {
    return size;
  }


  /** find w in the tree. return the node containing w or
  * null if not found */
  public Node search(String w) {
    return search(root, w);
  }
  private Node search(Node n, String w) {
    if (n == null) {
      return null;
    }
    if (w.equals(n.word)) {
      return n;
    } else if (w.compareTo(n.word) < 0) {
      return search(n.left, w);
    } else {
      return search(n.right, w);
    }
  }

  /** insert w into the tree as a standard BST, ignoring balance */
  public void bstInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    bstInsert(root, w);
  }

  /* insert w into the tree rooted at n, ignoring balance
   * pre: n is not null */
  private void bstInsert(Node n, String w) {
    // TODO
    if (n == null){
      return;
    }
    if (w.equals(n.word)){ // BST can't have duplicate 
      return;
    }
    if (w.compareTo(n.word) < 0){
      if (n.left != null){
        bstInsert(n.left, w);
        return;
      }else{
        n.left = new Node(w);
        n.left.parent = n;
        size++;
        return;
      }
    }
    if (w.compareTo(n.word) > 0){
      if (n.right != null){
        bstInsert(n.right, w);
        return;
      }else{
        n.right = new Node(w);
        n.right.parent = n;
        size++;
        return;
      }
    }
  }

  /** insert w into the tree, maintaining AVL balance
  *  precondition: the tree is AVL balanced and any prior insertions have been
  *  performed by this method. */
  public void avlInsert(String w) {
    // TODO
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    avlInsert(root, w);
  }

  /* insert w into the tree, maintaining AVL balance
   *  precondition: the tree is AVL balanced and n is not null */
  private void avlInsert(Node n, String w) {
    // TODO
    if (n == null){
      return;
    }
    if (w.equals(n.word)){
      return;
    }
    if (w.compareTo(n.word) < 0){
      if (n.left != null){
        avlInsert(n.left, w);
      }else{
        n.left = new Node(w);
        n.left.height = 0;
        n.left.parent = n;
        size++;
      }
    }
    if (w.compareTo(n.word) > 0){
      if (n.right != null){
        avlInsert(n.right, w);
      }else{
        n.right = new Node(w);
        n.right.height = 0;
        n.right.parent = n;
        size++;
      }
    }
    n.height = getHeight(n);
    rebalance(n);

  }

  /** do a left rotation: rotate on the edge from x to its right child.
  *  precondition: x has a non-null right child */
  public void leftRotate(Node x) {
    // TODO
    if (x.right == null){ 
      return;
    }
    Node thisNode = x;
    Node newRoot = x.right;
    Node newRight = x.right.left;
    if (x.parent == null){
      root = newRoot;
      newRoot.parent = null;
    }else{
      newRoot.parent = x.parent;
      if (newRoot.parent.right == x){
        newRoot.parent.right = newRoot;
      }else{
        newRoot.parent.left = newRoot;
      }
    }
    
    newRoot.left = thisNode;
    thisNode.parent = newRoot;
    thisNode.right = newRight;
    if (newRight != null){
      newRight.parent = thisNode;
    }
    
    newRoot.left.height = getHeight(newRoot.left);
    newRoot.height = getHeight(newRoot);

    return;

  }

  // gets heights of nodes in tree
  private int getHeight(Node n){
    if (n == null){
      return -1;
    }
    if (n.right == null && n.left == null){
      return 0;
    }
    if (n.right == null){
      return n.left.height + 1;
    }
    if (n.left == null){
      return n.right.height + 1;
    }
    int h  = Math.max(n.left.height, n.right.height) + 1;
    return h;
  }

  /** do a right rotation: rotate on the edge from x to its left child.
  *  precondition: y has a non-null left child */
  public void rightRotate(Node y) {
    // TODO
    if (y.left == null){
      return;
    }

    Node thisNode = y;
    Node newRoot = y.left;
    Node newLeft = y.left.right;

    if (y.parent == null){
      root = newRoot;
      newRoot.parent = null;
    }else{
      newRoot.parent = y.parent;
      if (newRoot.parent.right == y){
        newRoot.parent.right = newRoot;
      }else{
        newRoot.parent.left = newRoot;
      }
    }
    newRoot.right = thisNode;
    thisNode.parent = newRoot;
    thisNode.left = newLeft;
    if (newLeft != null){
      newLeft.parent = thisNode;
    }

    newRoot.right.height = getHeight(newRoot.right);
    newRoot.height = getHeight(newRoot);


    return;
  }

  /** rebalance a node N after a potentially AVL-violoting insertion.
  *  precondition: none of n's descendants violates the AVL property */
  public void rebalance(Node n) {
    // TODO
    if (getBal(n) > 1){
      if (getBal(n.right) < 0){
        rightRotate(n.right);
        leftRotate(n);
      }else{
        leftRotate(n);
      }
      }
      if(getBal(n) < -1){
        if (getBal(n.left) > 0){
        leftRotate(n.left);
        rightRotate(n);
      }else{
        rightRotate(n);
      }
      }
  }

  public int getBal(Node n){
    int balance = getHeight(n.right) - getHeight(n.left);
    return balance;
  }

  /** remove the word w from the tree */
  public void remove(String w) {
    remove(root, w);
  }

  /* remove w from the tree rooted at n */
  private void remove(Node n, String w) {
    return; // (enhancement TODO - do the base assignment first)
  }

  /** print a sideways representation of the tree - root at left,
  * right is up, left is down. */
  public void printTree() {
    printSubtree(root, 0);
  }
  private void printSubtree(Node n, int level) {
    if (n == null) {
      return;
    }
    printSubtree(n.right, level + 1);
    for (int i = 0; i < level; i++) {
      System.out.print("        ");
    }
    System.out.println(n);
    // if (n.parent != null){

    //   System.out.print(n.parent.word);
    // }

    printSubtree(n.left, level + 1);
  }

  /** inner class representing a node in the tree. */
  public class Node {
    public String word;
    public Node parent;
    public Node left;
    public Node right;
    public int height;

    public String toString() {
      return word + "(" + height + ")";
    }

    /** constructor: gives default values to all fields */
    public Node() { }

    /** constructor: sets only word */
    public Node(String w) {
      word = w;
    }

    /** constructor: sets word and parent fields */
    public Node(String w, Node p) {
      word = w;
      parent = p;
    }

    /** constructor: sets all fields */
    public Node(String w, Node p, Node l, Node r) {
      word = w;
      parent = p;
      left = l;
      right = r;
    }
  }
}
