/**
 * @author Davis Jeffrey
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Arrays;

/**
 * Contains 2 variants:
 * -Quick sort
 * -Dijkstra's 3-way partitioning(Dutch Flag)
 *
 * Based on partitioning an array
 * Pick an arbitrary element, make sure all elements to the left are smaller, and right are larger
 */
public class QuickSort {
    public static void main(String[] args) {

        QuickSort quickSort = new QuickSort();

        //Sorting test
        StdOut.println("Sorting tests:");
        for (int i = 0; i < 20; i++) {
            int[] arr = {4, 5, 2, 7, 1, 9, 8};
            quickSort.sort(arr);
            quickSort.printArray(arr);
        }


        //Timed tests
        long trials = 100000000; //10^8
        //Dutch flag problem test using normal QuickSort
        StdOut.println("Duplicate keys: QuickSort");
        Stopwatch s1 = new Stopwatch();
        for (long i = 0; i < trials; i++) {
            int[] arr = {1, 9, 2, 9, 1, 9, 2, 2, 2, 9, 1, 1, 2, 9, 1, 1, 2, 1, 2, 9};
            quickSort.sort(arr);
            //quickSort.printArray(arr);
        }
        double time1 = s1.elapsedTime();

        //Dutch flag problem test using 3-way sort
        StdOut.println("Duplicate keys: 3-way sort");
        Stopwatch s2 = new Stopwatch();
        for (long i = 0; i < trials; i++) {
            int[] arr = {1, 9, 2, 9, 1, 9, 2, 2, 2, 9, 1, 1, 2, 9, 1, 1, 2, 1, 2, 9};
            quickSort.threeWaySort(arr);
            //quickSort.printArray(arr);
        }
        double time2 = s2.elapsedTime();

        //Dutch flag problem test using Java system sort
        StdOut.println("Duplicate keys: Java system sort");
        Stopwatch s3 = new Stopwatch();
        for (long i = 0; i < trials; i++) {
            int[] arr = {1, 9, 2, 9, 1, 9, 2, 2, 2, 9, 1, 1, 2, 9, 1, 1, 2, 1, 2, 9};
            StdRandom.shuffle(arr);
            Arrays.sort(arr);

        }
        double time3 = s3.elapsedTime();

        StdOut.println("Time taken for Duplicate keys: QuickSort = " + time1); //For 10^8, 72.191s
        StdOut.println("Time taken for Duplicate keys: 3-way sort = " + time2); //For 10^8, 39.662s
        StdOut.println("Time taken for Duplicate keys: Java system sort = " + time3); //For 10^8, 43.123s
    }

    private void printArray(int[] arr) {
        for (int element : arr) {
            StdOut.print(element + ", ");
        }
        StdOut.println("");
    }

    private void sort(int[] arr) {
        StdRandom.shuffle(arr);
        sort(arr, 0, arr.length - 1);
    }

    /**
     * @param a  Array to be sorted
     * @param lo Index of first bounded element
     * @param hi Index of last bounded element
     */
    private void sort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int pIndex = partition(a, lo, hi); //Index of element known to be in place
        sort(a, lo, pIndex - 1);
        sort(a, pIndex + 1, hi);
    }

    /**
     * Takes array of random values, arranges it such that
     * elements to the left of an arbitrary value are less, and right are greater
     *
     * @return Index of the partitioning element known to be in place
     */
    private int partition(int a[], int lo, int hi) {
        int p = a[lo];
        int i = lo;
        int j = hi + 1;
        while (true) {
            //Find element on the left that needs to be swapped
            //Warning: Doing i++ here will result in an OutOfBounds exception
            while (a[++i] < p) {
                //Increment/decrement is done first; otherwise, will get stuck when a[i] == p
                if (i == hi) {
                    break;
                }
            }
            //Find element on the right that needs to be swapped
            while (a[--j] > p) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            swap(a, i, j);
        }
        swap(a, lo, j); //At this point, j has crossed over, and points to the lesser element and i to the greater
        return (j);
    }

    /**
     * i and j are indices in array a to be swapped
     */
    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * Dijkstra's 3 way partitioning, fastest sort for input with many duplicates
     */
    private void threeWaySort(int[] arr) {
        StdRandom.shuffle(arr);
        threeWaySort(arr, 0, arr.length - 1);
    }


    /**
     * Dijkstra's 3-way sort for duplicate keys (Dutch flag problem)
     */
    private void threeWaySort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }

        //Partition, such that final array is divided like this, where lt and gt are pointers:
        // [ ...less than p... |   equal to p   | ...more than p...]
        //                     ^                ^
        //                     lt               gt
        int p = a[lo];
        int i = lo;
        int lt = lo, gt = hi;
        while (i <= gt) {
            if (a[i] < p) {
                swap(a, lt, i); //If a[i] is less, move it to the left and bring p(at lt) to the right
                i++;
                lt++; //Keep lt pointer on the index of p
            } else if (a[i] > p) {
                swap(a, gt, i);
                gt--;
                //Leave i unchanged, since new value at i could also be > p
            } else if (a[i] == p) {
                i++;
                //Leave lt unchanged; now, elements from lt to i all == p
            }
        }

        threeWaySort(a, lo, lt - 1);
        threeWaySort(a, gt + 1, hi);
    }
}
