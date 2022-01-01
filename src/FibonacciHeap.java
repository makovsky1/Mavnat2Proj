

/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
    private HeapNode min;// pointer for minimun
    private HeapNode first;// pointer of start of tree
    private HeapNode last;// pointer for end of tree
    private int size;
    private static int totalCuts=0;
    private static int totalLinks=0;
    private int trees;
    private int marked;
    public FibonacciHeap() {// default builder
        this.min = null;
        this.first=null;
        this.last=null;
        this.size=0;
        this.trees=0;
        this.marked=0;
    }

    public FibonacciHeap(HeapNode node) {// builder with a node
        this.min = node;
        node.setKey(node.getKey());
        node.setRank(node.getRank());
        node.setMark(node.getMark());
        node.setChild(null);
        node.setNext(null);
        node.setPrev(null);
        node.setParent(null);
        this.first=node;
        this.last=node;
        this.size=1;
        this.trees=1;
        this.marked=0;
    }

    /**
     * public boolean isEmpty()
     * <p>
     * Returns true if and only if the heap is empty.
     */
    public boolean isEmpty() {
        return this.size()==0;// if size=o than empty
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
        return insertNode(node);

    }
    public HeapNode insertNode(HeapNode node) // deal with insertion of nodes
    {

        if (this.isEmpty()|| this.min==null){// tree is empty
            this.min=node;
            this.last=node;
            this.first=node;
            node.setNext(node);
            node.setPrev(node);
            this.size++;
            this.trees++;
            return node;

        }
        if (node.getKey()<this.min.getKey())// update minimum if necessary
        {
            this.min=node;
        }
        this.first.setPrev(node);
        node.setNext(this.first);
        node.setPrev(this.last);
        this.last.setNext(node);
        this.first=node;
        this.size++;
        this.trees++;
        return node;

    }
    /**
     * public void deleteMin()
     * <p>
     * Deletes the node containing the minimum key.
     */
    public void deleteMin() {
        this.size--; //update size
        this.trees=this.trees+this.min.getRank()-1; // update number of trees before linking
        if (this.min.getRank() == 0) { // If min has no child
            if (this.min==this.first && this.min==this.last)// heapNode  is empty
            {
                this.min = null;
                this.first=null;
                this.last=null;
                this.size=0;
                return;
            }
            else if (this.min==this.first)// if min is first
            {
                this.first=min.getNext();
                this.first.setPrev(this.last);
                this.last.setNext(this.first);

            }
            else if (this.min==this.last)// if min is last
            {
                this.last=min.getPrev();
                this.last.setNext(this.first);
                this.first.setPrev(this.last);

            }
            else {
                this.min.getPrev().setNext(min.getNext());
                this.min.getNext().setPrev(min.getPrev());
            }
            }
        else{ // We want to connect min sons to x.getPrev and x.getNext
            HeapNode child=this.min.getChild();
            HeapNode secondChild=child;
            child.setParent(null);
            do {// remove marks of child of min
                if (secondChild.getMark()==true)
                {
                    secondChild.setMark(false);
                    this.marked--;
                }
                secondChild = secondChild.getNext();

            }
            while(child!=secondChild);
            if (this.min!=this.last && this.min!=this.first) {// normal case
                child.getPrev().setNext(min.getNext());
                min.getNext().setPrev(child.getPrev());
                child.setPrev(min.getPrev());
                child.getPrev().setNext(child);

            }
            else if (this.min==this.last && this.first==this.min)// only tree is min tree
            {
                child.setParent(null);
                this.last=child.getPrev();
                this.first=child;
            }
            else if(this.min==this.first)// first tree is min
            {
                child.setParent(null);
                this.first=child;
                this.last.setNext(child);
                child.getPrev().setNext(min.getNext());
                min.getNext().setPrev(child.getPrev());
                this.first.setPrev(this.last);
            }

            else if (this.min==this.last)// last tree is min tree
            {
                child.setParent(null);
                this.last.getNext().setPrev(child.getPrev());
                this.last=child.getPrev();
                this.last.setNext(this.first);
                child.setPrev(this.min.getPrev());
                child.getPrev().setNext(child);
            }
        }

        this.succesiveLinking();// start the linking of the tree

    }
    private void succesiveLinking()
    {
        int arrSize = (int)(Math.log(this.size) / Math.log(1.5)+3);// size of the array

        HeapNode [] bucket= new HeapNode[arrSize];// initialize buckets
        for (int i = 0; i <bucket.length; i++) {
            bucket[i]=null;

        }
        this.toBuckets(bucket);
        this.fromBuckets(bucket);
    }

    private HeapNode link(HeapNode node1, HeapNode node2) {// needs to be the same rank
        this.trees--;// update trees
        totalLinks++;// update links
        node1.setNext(node1);// initialzing pointers
        node1.setPrev(node1);
        node2.setNext(node2);
        node2.setPrev(node2);
        if (node1.getRank()==0)// if node doesn't have children
        {
            if (node1.getKey()<node2.getKey())// node 1 is the root
            {
                node2.setParent(node1);
                node1.setChild(node2);
                node2.setNext(node2);
                node2.setPrev(node2);
                node1.setRank(node1.getRank()+1);
                return node1;

            }

            else // node 2 is the root
            {
                node1.setParent(node2);
                node2.setChild(node1);
                node1.setNext(node1);
                node1.setPrev(node1);
                node2.setRank(node2.getRank()+1);
                return node2;

            }
        }
         else {
            if (node1.getKey() < node2.getKey()) { // node 1 has a child and is the root
                node2.setParent(node1);
                node2.setNext(node1.getChild());
                node2.setPrev(node1.getChild().getPrev());
                node1.getChild().getPrev().setNext(node2);
                node1.getChild().setPrev(node2);
                node1.setChild(node2);
                node1.setRank(node1.getRank() + 1);
                return node1;
            }


            else// node 2 is the root
            {
                node1.setParent(node2);
                node1.setNext(node2.getChild());
                node1.setPrev(node2.getChild().getPrev());
                node2.getChild().getPrev().setNext(node1);
                node2.getChild().setPrev(node1);
                node2.setChild(node1);
                node2.setRank(node2.getRank() + 1);
                return node2;


            }

        }
        }
    private void toBuckets(HeapNode [] bucket)
    {
        HeapNode starter=this.first;
        starter.getPrev().setNext(null);// not to get stuck in a loop
        while (starter!=null)
        {

            HeapNode y=starter;
            starter=starter.getNext();
            if (starter==this.first)// not to get stuck in a loop
            {
                break;
            }
            while (bucket[y.getRank()]!=null)
            {
                y=link(y,bucket[y.getRank()]);// linking trees of the same rank

                bucket[y.getRank()-1]=null;
            }
            y.setPrev(y);// initalize pointers
            y.setNext(y);
            y.setParent(null);
            bucket[y.getRank()]=y;
        }
    }
    private void fromBuckets(HeapNode [] bucket)
    {
        this.min=null;// initalize the heap
        this.first=null;
        this.last=null;
        this.trees=0;
        for (int i = 0; i < bucket.length; i++) { // setting the heap
            if (bucket[i]!=null)
            {
                this.trees++;// update trees
                if (this.min==null)// if this is the first tree
                {
                    this.min=bucket[i];
                    this.first=bucket[i];
                    this.last=bucket[i];
                    this.first.setNext(bucket[i]);
                    this.first.setPrev(bucket[i]);
                }
                else// heap is not empty
                {
                    if (bucket[i].getKey()<this.min.getKey())
                    {
                        this.min=bucket[i];
                    }
                    bucket[i].setNext(this.first);
                    this.first.setPrev(bucket[i]);
                    bucket[i].setPrev(this.last);
                    this.last.setNext(bucket[i]);
                    this.last=bucket[i];

                }
            }

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

        if (heap2.isEmpty())// if heap2 is empty no need to do anything
        {
            return;
        }
        if (this.isEmpty())
        {
            this.first=heap2.first;
            this.min=heap2.min;
            this.last=heap2.last;
            this.size=heap2.size;
            this.trees=heap2.trees;
            this.marked= heap2.marked;
              return;
        }
        this.last.setNext(heap2.first);
        this.first.setPrev(heap2.last);
        heap2.first.setPrev(this.last);
        heap2.last.setNext(this.first);
        this.last=heap2.last;
        this.size=this.size+heap2.size;// update size
        this.trees=this.trees+heap2.trees;// update trees
        this.marked=this.marked+heap2.marked;// update marked
        if (this.min.getKey() > heap2.min.getKey()) {// update min if necessary
            this.min=heap2.min;
        }
    }


    /**
     * public int size()
     * <p>
     * Returns the number of elements in the heap.
     */
    public int size() {
        return this.size; // size is updated in all places
    }

    /**
     * public int[] countersRep()
     * <p>
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * Note: The size of of the array depends on the maximum order of a tree, and an empty heap returns an empty array.
     */

    public int[] countersRep() {
        int  count=0;
        int []array = {};
        int maxRank=0;
        HeapNode start=this.first;
        if (this.isEmpty()){// if empty return empty array
            return array;
        }
        for (int i = 0; i <this.numberOfTrees();  i++) { // find highest rank
            if(start.getRank()>maxRank)
            {
                maxRank= start.getRank();
            }
        start=start.getNext();
        }


        int[] arr = new int[maxRank+1]; //initiializing the array
        for (int i = 0; i <this.numberOfTrees();  i++) {// insert all ranks of trees
            arr[start.getRank()]++;
            start = start.getNext();
        }
        return arr;
    }

    /**
     * public void delete(HeapNode x)
     * <p>
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     */
    public void delete(HeapNode x) {// using deletemin and decreaseKey
        int key = x.getKey();
        int minKey = this.min.getKey();
            this.decreaseKey(x, (key - minKey) + 1);// make key the minimum

        this.deleteMin();// delete minimum which is the key now
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     * <p>
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {

        if (x.getParent() == null || x.getParent().getKey() < x.getKey()-delta) {// if root does not matter
            x.setKey(x.getKey() - delta);
            if (x.getKey() < this.min.getKey()) {
                this.min = x;
            }
        }

        else {// if key is now lower than parent
            x.setKey(x.getKey() - delta);
            if (x.getKey() < this.min.getKey()) {
                this.min = x;
            }
            cascadingCuts(x);// send to cascading cuts

        }


    }


    public void cascadingCuts(HeapNode x) {
        HeapNode parent=x.getParent();
        do {
            boolean sgn =parent.getMark();// getting the mark of the parent
            if (x.getMark())// if x is marked need to remove mark
            {
                this.marked--;
                x.setMark(false);
            }
            x.setParent(null);// cutting x from the parent
            parent.setRank(parent.getRank() - 1);// update rank of parent
            if (x.getNext() == x) {// if x is the only child
                parent.setChild(null);

            } else {// there are other children
                parent.setChild(x.getNext());
                x.getPrev().setNext(x.getNext());
                x.getNext().setPrev(x.getPrev());
            }
            // changing x to first node
            x.setNext(this.first);
            this.first.setPrev(x);
            x.setPrev(this.last);
            this.last.setNext(x);
            this.first=x;
            this.trees++;
            totalCuts++;
            if (sgn==false)// if parent sign is false no need to continue
            {
                if (parent.getParent()==null)// if this is a root no need to change the mark
                {
                    break;
                }
                else// if not root change the mark
                {
                    parent.setMark(true);
                    this.marked++;
                    break;
                }
            }
            x=parent;
            parent=parent.getParent();

        }
        while (parent != null);//until x is not root

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
        return this.trees+2*this.marked; // return potential as instructed
    }


    public int numberOfTrees()
    {
        return this.trees;// return number of trees
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
        return totalLinks; // updated through the code
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
        return totalCuts; // updated through the code
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
        int[] emptyArr = {};
        if (k == 0){
            return emptyArr;
        }
        int[] arr = new int[k];// initialize the array
        FibonacciHeap heap2 = new FibonacciHeap();
        arr[0] = H.min.getKey(); //Insert the min key to the array
        heap2.insert(H.min.getKey());
        heap2.findMin().setInfo(H.min);
        int counter = 1;
        HeapNode node = H.min;
        while ( counter < k){ //While the array is not full
            heap2.deleteMin();
            node = node.getChild();
            if (node != null) {
                int key = node.getKey();
                do {// insert children
                    heap2.insert(node.getKey());
                    heap2.first.setInfo(node);
                    node = node.getNext();
                }
                while (key != node.getKey());
            }
            node = heap2.findMin().getInfo();
            arr[counter] = node.getKey();
            counter ++;
        }

        return arr;
    }

    /**
     * public class HeapNode
     *
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in another file.
     *
     */

    public static class HeapNode{

        private int key;
        private int rank;
        private boolean mark;
        private HeapNode child;
        private HeapNode next;
        private HeapNode prev;
        private HeapNode parent;
        private HeapNode info;// for kmin function only
        private HeapNode() {// default builder
            this.key = Integer.MAX_VALUE;
            this.rank = -1;
            this.mark = false;
            this.child = null;
            this.next = null;
            this.prev = null;
            this.parent = null;
            this.info=null;
        }
        public HeapNode(int key) {// regular builder with keu
            this.key = key;
            this.rank = 0;
            this.mark = false;
            this.child = null;
            this.next = null;
            this.prev = null;
            this.parent = null;
            this.info=null;

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
        public HeapNode getInfo() {return this.info;}
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
        public void setInfo(HeapNode info)
        {
            this.info=info;
    }
        public String toString(){
            return ""+this.key;
        }
    }
}