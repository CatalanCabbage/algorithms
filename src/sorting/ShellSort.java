/**
 * @author Davis Jeffrey
 */

import edu.princeton.cs.algs4.StdOut;

/**
 * ShellSort == h-sort
 * Basically Insertion Sort, but comparison happens at h-intervals instead of 1
 * i.e., Compare n with (n-h) instead of (n-1)
 * H-values used here = Knuth's formula (Gives O(N^(3/2)) ):
 * h = ((3^k) - 1)/2, where h <= N/2
 */

public class ShellSort<Item extends Comparable<Item>> {

    private Item[] arr;
    private int numOfSwaps = 0;
    private int numOfCompares = 0;
    //All practical h values, from 1 to Integer.MAX_VALUE
    private int[] hArr = {1, 4, 13, 40, 121, 364, 1093, 3280, 9841, 29524, 88573,
            265720, 797161, 2391484, 7174453, 21523360, 64570081, 193710244, 581130733};

    private void swap(int index1, int index2) {
        Item tempItem = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tempItem;
    }

    private boolean lessThan(Item a, Item b) {
        return a.compareTo(b) < 0;
    }

    private int getHIndex(int length) {
        int index;
        for (index = 0; hArr[index] <= length / 2; index++) {
        }
        return index - 1;
    }

    public Item[] sort(Item[] inpArr, boolean useInsertionSort) {
        arr = inpArr;
        int size = arr.length;
        if(inpArr == null || size < 1) {
            throw new IllegalArgumentException("Array has no elements");
        } else if(size == 1) {
            return inpArr;
        }
        numOfSwaps = 0;
        numOfCompares = 0;
        int hIndex;
        int h;
        if(useInsertionSort) {
            hIndex = 0; //Makes h == 1, converting this into Insertion Sort
        } else {
            hIndex = getHIndex(size);
        }

        do { //Continue till h becomes 1
            h = hArr[hIndex];
            for (int i = 0; i < size / h; i++) { //Passes required to completely h-sort the array
                //Start j from h, so you can compare it with j-h
                for (int j = h; j < size; j++) { //One pass through whole array
                    numOfCompares++;
                    if (lessThan(arr[j], arr[j - h])) {
                        swap(j, j - h);
                        numOfSwaps++;
                    }
                    //Utility code, to print the array
                    for (int k = 0; k < size; k++) {
                        if(k == j || k == j - h) {
                            StdOut.print((Integer) arr[k] + "* ");
                        } else {
                            StdOut.print((Integer) arr[k] + "  ");
                        }
                    }
                    StdOut.println("");
                }
            }
            hIndex--;
        } while (h != 1);
        StdOut.println("Number of compares: " + numOfCompares);
        StdOut.println("Number of swaps: " + numOfSwaps);
        return arr;
    }

    public Item[] sort(Item[] inpArr) {
        return sort(inpArr, false);
    }

    //Prints num of compares and swaps for Shell and Insertion
    public void compareShellWithInsertion(Item[] inpArr) {
        sort(inpArr.clone(), true);
        int insertionCompares = numOfCompares;
        int insertionSwaps = numOfSwaps;
        sort(inpArr.clone(), false);
        int shellCompares = numOfCompares;
        int shellSwaps = numOfSwaps;
        StdOut.println("Insertion sort: Compares = " + insertionCompares + ", Swaps = " + insertionSwaps);
        StdOut.println("Shell sort: Compares = " + shellCompares + ", Swaps = " + shellSwaps);
    }

    public static void main(String[] args) {
        ShellSort<Integer> shellSort = new ShellSort<>();
        Integer[] testArr = {1, 7, 9, 2, 5, 5, 1, 3, 2, 7, 8, 4, 4, 2, 5, 6, 7, 8, 9, 10};
        //Integer[] sortedArr = shellSort.sort(testArr);
        shellSort.compareShellWithInsertion(testArr);
    }
}