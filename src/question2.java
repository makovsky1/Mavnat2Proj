import java.util.List;

public class question2 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int m = (int) Math.pow(3, 6) - 1;
        FibonacciHeap heap = new FibonacciHeap();
        heapInsert(heap,m);
        heapDelete(heap,m);


        long end = System.currentTimeMillis();

        System.out.println(end-start);

        System.out.println(heap.totalLinks());
        System.out.println(heap.totalCuts());
        System.out.println(heap.potential());

    }
    public static void heapInsert(FibonacciHeap heap, int number) {
        for (int i = 0; i < number + 1; i++) {
            heap.insert(i);
        }

    }
    public static void heapDelete(FibonacciHeap heap, int number)
    {
        number=number/4;
        for (int i = 0; i < number; i++) {
            heap.deleteMin();

        }
    }



}