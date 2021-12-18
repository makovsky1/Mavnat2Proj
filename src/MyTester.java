public class MyTester {
    public static void main(String[] args)
    {
        FibonacciHeap heap=new FibonacciHeap();
        heap.insert(10);
        heap.insert(23);
        heap.insert(144);
        heap.insert(12);
        heap.insert(18);
        FibonacciHeap.Display a=new FibonacciHeap.Display();
        a.display(heap);

        heap.deleteMin();
        System.out.println(heap.findMin().getKey());
        a.display(heap);
        heap.deleteMin();
        System.out.println(heap.findMin().getKey()+"  this is after second delete");
        System.out.println("-------");
        a.display(heap);

    }




}
