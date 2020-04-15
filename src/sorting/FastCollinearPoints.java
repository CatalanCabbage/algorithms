/**
 * @author Davis Jeffrey
 */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * --Assignment
 * <p>
 * Given an array of points, find maximal line segment containing 4 or more points exactly once
 * Constraints: Time = O(n^2 log n), Space = n + number of line segments
 */
public class FastCollinearPoints {
    private int numberOfSegments = 0;
    private double[] slopeOfAddedSegments;
    private Point[] pointInAddedSegments;
    private double[][] slopes; //[Slope][index of that point with which slope is taken]
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        isValidInput(points);
        slopes = new double[points.length][2];
        segments = new LineSegment[points.length + 1];
        slopeOfAddedSegments = new double[points.length + 1];
        pointInAddedSegments = new Point[points.length + 1];
        int start = 0;
        int end = 0;
        double slope = 0;
        //For each point, sort the points by slopes -- O(n*n log n)
        for (int i = 0; i < points.length; i++) {
            calculateSlopes(points, points[i]);
            sort(slopes);
            //Iterate; if 4+ repeated, make a segment
            for (int j = 0; j < points.length; j++) {
                if (slope == slopes[j][0] && j != points.length - 1) {
                    continue;
                } else { //New slope found; set end index
                    slope = slopes[j][0];
                    end = j;
                    if (end - start + 1 >= 3) {
                        int[] indices = new int[end - start + 2];
                        indices[0] = i;
                        int index = 1;
                        for (int k = start; k <= end; k++) {
                            indices[index] = (int) slopes[k][1];
                            index++;
                        }
                        addSegment(points, indices);
                    }
                    start = j;
                }
            }
        }

        //Clean-up empty indices in segments
        LineSegment[] temp = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            temp[i] = segments[i];
        }
        segments = temp;
    }

    private void isValidInput(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Input is null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Input has null values");
            }
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i == j) {
                    continue;
                }
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Input has duplicate values");
                }
            }
        }
    }

    private void addSegment(Point[] points, int[] indices) {
        //Compare with 1 added point and its segment's slope to prevent duplicates
        if (isLineDuplicate(points, indices)) {
            return;
        }
        Point minPoint = points[indices[0]];
        Point maxPoint = points[indices[1]];
        for (int i = 0; i < indices.length; i++) {
            minPoint = (minPoint.compareTo(points[indices[i]]) < 0) ? minPoint : points[indices[i]];
            maxPoint = (maxPoint.compareTo(points[indices[i]]) > 0) ? maxPoint : points[indices[i]];
        }
        LineSegment segment = new LineSegment(minPoint, maxPoint);
        segments[numberOfSegments] = segment;
        slopeOfAddedSegments[numberOfSegments] = minPoint.slopeTo(maxPoint);
        pointInAddedSegments[numberOfSegments] = minPoint;
        numberOfSegments++;
    }

    private boolean isLineDuplicate(Point[] points, int[] indices) {
        if (indices.length <= 1) {
            return false;
        }
        double slope = points[indices[0]].slopeTo(points[indices[1]]);
        for (int i = 0; i < numberOfSegments - 1; i++) {
            if (slopeOfAddedSegments[i] == slope) {
                for (int j = 0; j <= indices.length; j++) {
                    if (points[indices[j]].compareTo(pointInAddedSegments[i]) == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void calculateSlopes(Point[] points, Point reference) {
        for (int i = 0; i < points.length; i++) {
            slopes[i][0] = reference.slopeTo(points[i]);
            slopes[i][1] = i; //To maintain index

        }
    }

    private double[][] sort(double[][] arr) { //Sort by first []; second is just to maintain index
        double[][] temp = new double[arr.length][2];
        mergeSort(arr, temp, 0, arr.length - 1);
        return arr;
    }

    private void mergeSort(double[][] arr, double[][] temp, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) / 2;
        mergeSort(arr, temp, lo, mid);
        mergeSort(arr, temp, mid + 1, hi);
        merge(arr, temp, lo, hi);
    }

    private void merge(double[][] arr, double[][] temp, int lo, int hi) {
        for (int i = 0; i < arr.length; i++) {
            temp[i][0] = arr[i][0];
            temp[i][1] = arr[i][1];
        }
        int mid = (lo + hi) / 2;
        if (temp[mid][0] < temp[mid + 1][0]) {
            return;
        }
        int p1 = lo;
        int p2 = mid + 1;
        for (int i = lo; i <= hi; i++) {
            if (p1 > mid) {
                arr[i][0] = temp[p2][0];
                arr[i][1] = temp[p2][1];
                p2++;
            } else if (p2 > hi) {
                arr[i][0] = temp[p1][0];
                arr[i][1] = temp[p1][1];
                p1++;
            } else if (temp[p1][0] < temp[p2][0]) {
                arr[i][0] = temp[p1][0];
                arr[i][1] = temp[p1][1];
                p1++;
            } else {
                arr[i][0] = temp[p2][0];
                arr[i][1] = temp[p2][1];
                p2++;
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        //If returned without cloning, it returns the reference, which can be mutated by the caller
        return segments.clone();
    }

    public static void main(String[] args) {
        testMergeSort();
        Point p1 = new Point(30000, 0);
        Point p2 = new Point(20000, 10000);
        Point p3 = new Point(10000, 20000);
        Point p4 = new Point(0, 30000);
        Point[] points = new Point[]{p1, p2, p3, p4};
        FastCollinearPoints fcp = new FastCollinearPoints(points);
        StdOut.println(fcp.segments()[0]);
    }

    private static void testMergeSort() {
        double[] testArr = {1.0, 5.0, 2.0, 3.0, 7.0, 2.0};
        FastCollinearPoints fcp = new FastCollinearPoints(new Point[]{});
    }
}
