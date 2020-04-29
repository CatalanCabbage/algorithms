import java.util.Iterator;

/**
 * @author Davis Jeffrey
 */

/**
 * A min-priority queue, based on a min-heap
 * Also implements heap-sort(desc.)
 * Min-Heap: Ordered such that the parent is always less than children
 */
public class PriorityQueue<Key extends Comparable<Key>> {

    private int capacity = 0;
    private int n = 0; //Keys present in the Queue
    private Key[] arr; //Indexing starts from 1

    public PriorityQueue() {
        this.capacity = 20;
        arr = (Key[]) new Comparable[20];
    }

    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        arr = (Key[]) new Comparable[capacity];
    }

    public Key min() {
        return arr[1];
    }

    //Add Key to last index and swim up
    public void insert(Key x) {
        resize();
        n++; //If there are 10 keys, this needs to be inserted in 11th index initially
        arr[n] = x;
        swim(n);
    }

    //Remove first key, replace it with last and sink it down
    public Key delMin() {
        Key min = arr[1];
        arr[1] = arr[n--]; //Put last Key on top and reduce size
        sink(1);
        return min;
    }

    //Moves a node that is lower down on the tree up to its correct position
    private void swim(int index) {
        int parent = index / 2;
        if (parent < 1) {
            return;
        }
        if (arr[index].compareTo(arr[parent]) < 0) {
            swap(index, parent);
            swim(parent); //Do the same checks for the index again, which is now in parent's place
        }
    }

    //Moves a node that is higher up on the tree down to its correct position
    private void sink(int index) {
        Key indexVal = arr[index];
        Key child1Val = arr[index * 2];
        Key child2Val = arr[index * 2 + 1];
        int child = index * 2;
        if (child > n) {
            return;
        }
        //Find least of index's 2 children(child and child + 1) to compare with index
        if ((child + 1 <= n) && arr[child + 1].compareTo(arr[child]) < 0) {
            child++;
        }
        //If child < index, swap. Else, order is already correct
        if (arr[child].compareTo(arr[index]) < 0) {
            swap(child, index);
            sink (child); //Do the same checks for the index again, which is now at a lower level
        }
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    private void swap(int index1, int index2) {
        Key temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    //Double the capacity if size reaches 3/4th of total capacity
    private void resize() {
        if (n > (capacity * 3 / 4)) {
            Key[] temp = (Key[]) new Object[capacity * 2];
            System.arraycopy(arr, 0, temp, 0, n + 1);
            arr = temp;
        }
    }

    public void heapSort() {
        int actualSize = n;
        for (int i = n; i > 0; i--) {
            arr[n] = delMin();
        }
        System.out.println("Queue: ");
        for (int i = 1; i <= actualSize; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    private void verifyQueue() {
        System.out.println("Queue: ");
        for (int i = 1; i <= n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("");
        for (int i = 1; i <= n / 2; i++) {
            Key child1 = null;
            Key child2 = null;
            if (i * 2 <= n) {
                child1 = arr[i * 2];
            }
            if (i * 2 + 1 <= n) {
                child2 = arr[i * 2 + 1];
            }
            System.out.println("Parent: " + arr[i] + ", Children: " + child1 + ", " + child2);
        }
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.insert(9);
        pq.insert(2);
        pq.insert(1);
        pq.insert(5);
        pq.insert(6);
        pq.insert(2);
        pq.insert(4);
        pq.insert(8);
        pq.insert(4);
        pq.insert(5);
        pq.insert(7);
        pq.verifyQueue();
        pq.heapSort(); //DelMin is tested here
    }
}
