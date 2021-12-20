public class MyTester {
    public static void main(String[] args)
    {
        FibonacciHeap heap=new FibonacciHeap();
        heap.insert(100);
        heap.insert(100);
        heap.insert(100);
        heap.insert(100);
        heap.insert(160);
        heap.insert(161);
       //heap.insert(8);
        //heap.insert(100);
        FibonacciHeap.Display a=new FibonacciHeap.Display();
        //a.display(heap);
        while(!heap.isEmpty())
        {
            System.out.println(heap.findMin().getKey());
            heap.deleteMin();
            if(!heap.isEmpty())
            {
                a.display(heap);
            }
        }


    }




}
