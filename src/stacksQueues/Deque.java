/**
 * @author Davis Jeffrey
 */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Requirement:
 * Generalization of a stack and a queue that supports
 * adding and removing items from either the front or the back of the data structure.
 * Each deque operation (including construction) == O(n)
 * Each Iterator operation (including construction) == O(n)
 */
public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node nextNode;
        Node previousNode;
    }

    private Node firstNode;
    private Node lastNode;
    private int size;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    private void validateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateItem(item);
        Node oldFirstNode = firstNode;
        firstNode = new Node();
        firstNode.item = item;
        if (oldFirstNode != null) {
            firstNode.nextNode = oldFirstNode;
            oldFirstNode.previousNode = firstNode;
        }
        size++;
        if (lastNode == null) {
            lastNode = firstNode;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        validateItem(item);
        Node oldLastNode = lastNode;
        lastNode = new Node();
        lastNode.item = item;
        if (oldLastNode != null) {
            lastNode.previousNode = oldLastNode;
            oldLastNode.nextNode = lastNode;
        }
        size++;
        if (firstNode == null) {
            firstNode = lastNode;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node currentFirstNode = firstNode;
        firstNode = firstNode.nextNode;
        firstNode.previousNode = null;
        size--;
        return currentFirstNode.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node currentLastNode = lastNode;
        lastNode = lastNode.previousNode;
        lastNode.nextNode = null;
        size--;
        return currentLastNode.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    class DequeIterator implements Iterator<Item> {
        private Node itrFirstNode;

        public DequeIterator() {
            //init a private copy of the Deque
            itrFirstNode = firstNode;
        }

        @Override
        public boolean hasNext() {
            return itrFirstNode != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Deque is empty");
            }
            Node currentFirstNode = itrFirstNode;
            itrFirstNode = itrFirstNode.nextNode;
            return currentFirstNode.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }
    }

    private void printQueue() {
        StdOut.println("Deque: ");
        Iterator itr = new DequeIterator();
        while (itr.hasNext()) {
            StdOut.print(itr.next() + " ");
        }
        StdOut.print(" --- Size = " + size());
        StdOut.println("");
    }

    // unit testing (required) : Should call every public method in the class
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        StdOut.println("addFirst() : 1 2 3");
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("3");
        StdOut.println("addLast() : 6 5 4");
        deque.addLast("6");
        deque.addLast("5");
        deque.addLast("4");
        deque.printQueue();
        StdOut.println("removeFirst() x2");
        deque.removeFirst();
        deque.removeFirst();
        deque.printQueue();
        StdOut.println("removeLast() x 1");
        deque.removeLast();
        StdOut.println("Deque: ");
        Iterator itr = deque.iterator();
        while (itr.hasNext()) {
            StdOut.print(itr.next() + " ");
        }
        StdOut.print(" --- Size = " + deque.size());
        StdOut.println("");
        StdOut.println("isEmpty = " + deque.isEmpty());
    }

}