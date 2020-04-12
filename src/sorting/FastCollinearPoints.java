/**
 * @author Davis Jeffrey
 */

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
    private double[] slopes;
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        slopes = new double[points.length];
        segments = new LineSegment[points.length / 4];
        slopeOfAddedSegments = new double[points.length / 4];
        pointInAddedSegments = new Point[points.length / 4];
        int start = 0;
        int end = 0;
        int slope = 0;
        //For each point, sort the points by slopes -- O(n*n log n)
        for (Point point : points) {
            calculateSlopes(points, point);
            sort(slopes);
            //Iterate; if 4+ repeated, make a segment
            for (int j = 0; j < points.length; j++) {
                if (slope == slopes[j]) {
                    continue;
                } else { //New slope found; set end index
                    start = j;
                    end = j == 0 ? 0 : j - 1;
                    addSegment(points, start, end);
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

    private void addSegment(Point[] points, int start, int end) {
        //Compare with 1 added point and its segment's slope to prevent duplicates
        if (isLineDuplicate(points, start, end)) {
            return;
        }
        Point minPoint = points[start];
        Point maxPoint = points[start];
        for (int i = start; i <= end; i++) {
            minPoint = (minPoint.compareTo(points[i]) < 0) ? minPoint : points[i];
            maxPoint = (maxPoint.compareTo(points[i]) > 0) ? maxPoint : points[i];
        }
        LineSegment segment = new LineSegment(minPoint, maxPoint);
        segments[numberOfSegments] = segment;
        slopeOfAddedSegments[numberOfSegments] = minPoint.slopeTo(maxPoint);
        pointInAddedSegments[numberOfSegments] = minPoint;
        numberOfSegments++;
    }

    private boolean isLineDuplicate(Point[] points, int start, int end) {
        double slope = points[start].slopeTo(points[end]);
        for (int i = 0; i < slopeOfAddedSegments.length; i++) {
            if (slopeOfAddedSegments[i] == slope) {
                for (int j = start; j <= end; j++) {
                    if (points[j].compareTo(pointInAddedSegments[i]) == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void calculateSlopes(Point[] points, Point reference) {
        for (int i = 0; i < points.length; i++) {
            slopes[i] = reference.slopeTo(points[i]);
        }
    }

    private double[] sort(double[] arr) {
        double[] temp = new double[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1);
        return arr;
    }

    private void mergeSort(double[] arr, double[] temp, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) / 2;
        mergeSort(arr, temp, lo, mid);
        mergeSort(arr, temp, mid + 1, hi);
        merge(arr, temp, lo, hi);
    }

    private void merge(double[] arr, double[] temp, int lo, int hi) {
        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        int mid = (lo + hi) / 2;
        if (temp[mid] < temp[mid + 1]) {
            return;
        }
        int p1 = lo;
        int p2 = mid + 1;
        for (int i = lo; i <= hi; i++) {
            if (p1 > mid) {
                arr[i] = temp[p2++];
            } else if (p2 > hi) {
                arr[i] = temp[p1++];
            } else if (temp[p1] < temp[p2]) {
                arr[i] = temp[p1++];
            } else {
                arr[i] = temp[p2++];
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
    }

    private static void testMergeSort() {
        double[] testArr = {1.0, 5.0, 2.0, 3.0, 7.0, 2.0};
        FastCollinearPoints fcp = new FastCollinearPoints(new Point[]{});
        assert (Arrays.equals(fcp.sort(testArr), new double[]{1.0, 2.0, 2.0, 3.0, 5.0, 7.0}));
    }
}
