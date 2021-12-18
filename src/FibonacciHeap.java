/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
    HeapNode min;

    public FibonacciHeap() {
        this.min = new HeapNode();
    }

    public FibonacciHeap(HeapNode node) {
        this.min = node;
        node.setKey(node.getKey());
        node.setRank(node.getRank());
        node.setMark(node.getMark());
        node.setChild(null);
        node.setNext(null);
        node.setPrev(null);
        node.setParent(null);
    }

    /**
     * public boolean isEmpty()
     * <p>
     * Returns true if and only if the heap is empty.
     */
    public boolean isEmpty() {
        return this.min.getRank() == -1; //Returns true if the heap is empty
    }

    /**
     * public HeapNode insert(int key)
     * <p>
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * The added key is assumed not to already belong to the heap.
     * <p>
     * Returns the newly created node.
     */
    public HeapNode insert(int key) {
        HeapNode node = new HeapNode(key); //Creating a node (of type HeapNode) which contains the given key
        /*if (this.isEmpty()){
            this.min = node;
        }
        HeapNode curr = this.min;
        while (curr.getPrev() != null){
            curr = curr.getPrev();
        }
        curr.setPrev(node);
        node.setNext(curr);
        if (key > this.min.getKey()){
            this.min = node;
        }8=*/
        return node;
    }

    /**
     * public void deleteMin()
     * <p>
     * Deletes the node containing the minimum key.
     */
    public void deleteMin() {
        if (this.min.getChild().getRank() == -1) { // If min has no child
            this.min.getPrev().setNext(min.getNext());
            this.min.getNext().setPrev(min.getPrev());
        }
        else{ // We want to connect min sons to x.getPrev and x.getNext
        }

    }

    /**
     * public HeapNode findMin()
     * <p>
     * Returns the node of the heap whose key is minimal, or null if the heap is empty.
     */
    public HeapNode findMin() {
        if (this.isEmpty()) { //If the heap is an empty heap
            return null;
        }
        return this.min;
    }

    /**
     * public void meld (FibonacciHeap heap2)
     * <p>
     * Melds heap2 with the current heap.
     */
    public void meld(FibonacciHeap heap2) {
        /*HeapNode node1 = this.min;
        while (node1.getNext() != null){
            node1 = node1.getNext();
        }
        HeapNode node2 = heap2.min;
        while(node2.getPrev() != null){
            node2 = node2.getPrev();
        }
        node1.setNext(node2);
        node2.setPrev(node1);
        if (this.min.getKey() > heap2.min.getKey()){
            heap2.min = null;
        }
        else{
            this.min = heap2.min;
        }*/
    }

    /**
     * public int size()
     * <p>
     * Returns the number of elements in the heap.
     */
    public int size() {
        return -123; // should be replaced by student code
    }

    /**
     * public int[] countersRep()
     * <p>
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * Note: The size of of the array depends on the maximum order of a tree, and an empty heap returns an empty array.
     */
    public int[] countersRep() {
        int[] arr = new int[100];
        return arr; //	 to be replaced by student code
    }

    /**
     * public void delete(HeapNode x)
     * <p>
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     */
    public void delete(HeapNode x) {
        return; // should be replaced by student code
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     * <p>
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {
        if (x.getParent() == null || x.getParent().getKey() < delta) {
            x.setKey(delta);
        } else {
            cascadingCuts(x);
        }
    }

    public void cascadingCuts(HeapNode x) {
        boolean sgn = x.getParent().getMark();
        if (x.getParent().getChild() == x) { // If x is a child
            x.getParent().setChild(x.getNext());
        }
        x.getParent().setRank(x.getParent().getRank() - 1);
        if (x.getParent().getParent() != null){ //We keep the roots mark to be false
            x.getParent().setMark(true);
        }
        x.getNext().setPrev(x.getPrev());
        x.getPrev().setNext(x.getNext());
        x.setNext(null);
        x.setPrev(null);
        x.setParent(null);
        HeapNode node = this.min;
        /*while (node.getPrev() != null) {
            node = node.getPrev();

        node.setPrev(x);
        x.setNext(node);
        if (sgn){
            cascadingCuts(x.getParent());
        }*/
    }


    /**
     * public int potential()
     *
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     *
     * In words: The potential equals to the number of trees in the heap
     * plus twice the number of marked nodes in the heap.
     */
    public int potential()
    {
        return -234; // should be replaced by student code
    }

    /**
     * public static int totalLinks()
     *
     * This static function returns the total number of link operations made during the
     * run-time of the program. A link operation is the operation which gets as input two
     * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
     * tree which has larger value in its root under the other tree.
     */
    public static int totalLinks()
    {
        return -345; // should be replaced by student code
    }

    /**
     * public static int totalCuts()
     *
     * This static function returns the total number of cut operations made during the
     * run-time of the program. A cut operation is the operation which disconnects a subtree
     * from its parent (during decreaseKey/delete methods).
     */
    public static int totalCuts()
    {
        return -456; // should be replaced by student code
    }

    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     *
     * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
     * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
     *
     * ###CRITICAL### : you are NOT allowed to change H.
     */
    public static int[] kMin(FibonacciHeap H, int k)
    {
        int[] arr = new int[100];
        return arr; // should be replaced by student code
    }

    /**
     * public class HeapNode
     *
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in another file.
     *
     */
    public static class HeapNode{

        public int key;
        public int rank;
        public boolean mark;
        public HeapNode child;
        public HeapNode next;
        public HeapNode prev;
        public HeapNode parent;
        public HeapNode() {
            this.key = -1;
            this.rank = -1;
            this.mark = false;
            this.child = null;
            this.next = null;
            this.prev = null;
            this.parent = null;
        }
        public HeapNode(int key) {
            this.key = key;
            this.rank = 0;
            this.mark = false;
            this.child = null;
            this.next = null;
            this.prev = null;
            this.parent = null;
        }

        public int getKey() {
            return this.key;
        }
        public int getRank(){
            return this.rank;
        }
        public boolean getMark(){
            return this.mark;
        }
        public HeapNode getChild(){
            return this.child;
        }
        public HeapNode getNext(){
            return this.next;
        }
        public HeapNode getPrev(){
            return this.prev;
        }
        public HeapNode getParent(){
            return this.parent;
        }
        public void setKey(int key){
            this.key = key;
        }
        public void setRank(int rank){
            this.rank = rank;
        }
        public void setMark(boolean mark){
            this.mark = mark;
        }
        public void setChild(HeapNode child){
            this.child = child;
        }
        public void setNext(HeapNode next){
            this.next = next;
        }
        public void setPrev(HeapNode prev){
            this.prev = prev;
        }
        public void setParent(HeapNode parent){
            this.parent = parent;
        }

    }
}
