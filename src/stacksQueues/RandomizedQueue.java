/**
 * @author Davis Jeffrey
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * --Assignment
 * Requirements:
 * Similar to a stack or queue, except that
 * the item removed is chosen uniformly at random among items in the data structure.
 * Each randomized queue operation == O(n).
 * Iterator implementation - next() and hasNext() == O(1), construction == O(n)
 * <p>
 * Plan: Implement using a resizing array
 * Assignment Score: 95/100
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int nextIndex; //Points to the next free index
    private int totalSize;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[20];
        totalSize = 20;
        nextIndex = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }


    // return the number of items on the randomized queue
    public int size() {
        return nextIndex;
    }

    //Check and resize if required
    private void resizeArray() {
        if (nextIndex < (totalSize / 4)) {
            Item[] newQueue = (Item[]) new Object[totalSize / 2];
            for (int i = 0; i < nextIndex; i++) {
                newQueue[i] = queue[i];
            }
            queue = newQueue;
            totalSize = totalSize/2;
        } else if (nextIndex > (totalSize * 3 / 4)) {
            Item[] newQueue = (Item[]) new Object[totalSize * 2];
            for (int i = 0; i < nextIndex; i++) {
                newQueue[i] = queue[i];
            }
            queue = newQueue;
            totalSize = totalSize * 2;
        }
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        queue[nextIndex] = item;
        nextIndex++;
        resizeArray();
    }

    /* Remove and return a random item
     * Remove array index at random, substitute it with the last index
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        int randIndex = StdRandom.uniform(nextIndex); //returns random [0,index)
        Item randItem = queue[randIndex];
        if(randIndex != nextIndex - 1) { //item removed isn't the last item
            queue[randIndex] = queue[nextIndex - 1]; //Substitute removed item with last item
        }
        queue[nextIndex - 1] = null; // Make old last index available for GC
        nextIndex--;
        resizeArray();
        return randItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return queue[StdRandom.uniform(nextIndex)];
    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        Item[] itrQueue;
        int itrNextIndex;

        RandomizedQueueIterator() {
            itrNextIndex = nextIndex;
            itrQueue = (Item[]) new Object[itrNextIndex];
            for(int i = 0; i < itrNextIndex; i++) {
                itrQueue[i] = queue[i];
            }
        }

        @Override
        public boolean hasNext() {
            return itrNextIndex != 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Queue is empty");
            }
            int randIndex = StdRandom.uniform(itrNextIndex); //returns random [0,index)
            Item randItem = itrQueue[randIndex];

            itrQueue[randIndex] = itrQueue[itrNextIndex - 1]; //Substitute removed item with last item
            itrQueue[itrNextIndex - 1] = null; // Make old last index available for GC
            itrNextIndex--;
            return randItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        StdOut.println("Enqueue 1 2 3 4 5");
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        StdOut.println("3 samples");
        StdOut.print(queue.sample());
        StdOut.print(queue.sample());
        StdOut.print(queue.sample());
        StdOut.println("\nItems present");
        Iterator itr = queue.iterator();
        while (itr.hasNext()){
            StdOut.print(itr.next());
        }
        StdOut.println("\nDeque 2 items");
        StdOut.print(queue.dequeue());
        StdOut.print(queue.dequeue());
        StdOut.println("\nItems present");
        itr = queue.iterator();
        while (itr.hasNext()){
            StdOut.print(itr.next());
        }
        StdOut.println("\nisEmpty = " + queue.isEmpty());
        StdOut.println("Deque remaining 3 items");
        StdOut.print(queue.dequeue());
        StdOut.print(queue.dequeue());
        StdOut.print(queue.dequeue());
        StdOut.println("\nSize = " + queue.size() + " " + "isEmpty = " + queue.isEmpty());
    }

}