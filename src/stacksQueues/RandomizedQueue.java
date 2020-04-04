/**
 * @author Davis Jeffrey
 */

import java.util.Iterator;

/**
 * Requirements:
 * Similar to a stack or queue, except that
 * the item removed is chosen uniformly at random among items in the data structure.
 * Each randomized queue operation (besides creating an iterator) == O(n).
 * Iterator implementation - next() and hasNext() == O(1), construction == O(n)
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    // construct an empty randomized queue
    public RandomizedQueue()

    // is the randomized queue empty?
    public boolean isEmpty()

    // return the number of items on the randomized queue
    public int size()

    // add the item
    public void enqueue(Item item)

    // remove and return a random item
    public Item dequeue()

    // return a random item (but do not remove it)
    public Item sample()

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {

    }
    // unit testing (required)
    public static void main(String[] args)

}