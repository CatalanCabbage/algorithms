/**
 * @author Davis Jeffrey
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Requirement:
 * Client class. Take an integer k(numOfItems) as a command-line argument;
 * read a sequence of strings from standard input using StdIn.readString();
 * print exactly k of them, uniformly at random.
 * Print each item from the sequence at most once.
 * Bonus: Make memory constraint a factor of k (not completed)
 * Assignment Score: 95/100
 */
public class Permutation {
    public static void main(String[] args) {
        int numOfItems = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        //StdOut.println("Enter space separated input strings, Ctrl+Z/Ctrl+D to end input");
        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < numOfItems; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}