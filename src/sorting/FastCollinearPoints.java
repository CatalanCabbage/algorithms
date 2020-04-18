/**
 * @author Davis Jeffrey
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * --Assignment
 * (Mergesort, Linked List implemented)
 * <p>
 * Given an array of points, find maximal line segment containing 4 or more points exactly once
 * Constraints: Time = O(n^2 log n), Space = n + number of line segments
 * <p>
 * Assignment Score: 95/100
 */
public class FastCollinearPoints {
    private Point[] points;
    private LineSegment[] segments;
    private int numOfSegments = 0;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        validatePoints(points);
        this.points = points;
        segments = new LineSegment[points.length];
        double[][] slopes;
        for (int i = 0; i < points.length; i++) {
            slopes = getSlopes(points[i], points); //slopes[slope][index]; this is to know original index after sorting
            sortSlopes(slopes);
            findAndAddSegments(slopes);
        }
        trimSegmentsArr();
    }

    //Validate input Points array
    private void validatePoints(Point[] points) {
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

    //Populate an array with [slopes wrt Point p] and [index of each point]
    private double[][] getSlopes(Point p, Point[] points) {
        double[][] slopes = new double[points.length][2];
        for (int i = 0; i < points.length; i++) {
            slopes[i][0] = p.slopeTo(points[i]);
            slopes[i][1] = i; //To maintain index
        }
        return slopes;
    }

    //Takes arr[slopes][indices]; sorts given array by slopes, but maintaining slope-index pair
    //Eg: Input --> Output: ([200][0], [100][1], [50][2]) --> ([50][2], [100][1], [200][0])
    private void sortSlopes(double[][] slopes) {
        double[][] aux = new double[slopes.length][2];
        mergeSort(slopes, aux, 0, slopes.length - 1);
    }

    private void mergeSort(double[][] a, double[][] aux, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + ((hi - lo) / 2);
        mergeSort(a, aux, lo, mid);
        mergeSort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private void merge(double[][] a, double[][] aux, int lo, int mid, int hi) {
        if (a[mid][0] < a[mid + 1][0]) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            aux[i][0] = a[i][0];
            aux[i][1] = a[i][1];
        }
        int p1 = lo, p2 = mid + 1;
        for (int i = lo; i <= hi; i++) {
            if (p1 > mid) {
                a[i][0] = aux[p2][0];
                a[i][1] = aux[p2][1];
                p2++;
            } else if (p2 > hi) {
                a[i][0] = aux[p1][0];
                a[i][1] = aux[p1][1];
                p1++;
            } else if (aux[p1][0] < aux[p2][0]) {
                a[i][0] = aux[p1][0];
                a[i][1] = aux[p1][1];
                p1++;
            } else {
                a[i][0] = aux[p2][0];
                a[i][1] = aux[p2][1];
                p2++;
            }
        }
    }

    /**
     * @Param slopes : Sorted array of slopes
     * Logic: When slope made with different points are same, it means those points are collinear with the ref point
     * Hence, counts number of times Slope is repeated; if repeated >= 3 times, create a segment
     */
    private void findAndAddSegments(double[][] slopes) {
        double currentSlope = slopes[0][0]; //[0][0] == Negative Infinity (Least value, slope of point to itself)
        int start = 0;
        int end = 0;
        for (int i = 1; i <= slopes.length; i++) {
            if (i == slopes.length) { //Handling for last entry only, since next "start" is not required
                end = i - 1;
                if (end - start + 1 >= 3) {
                    addSegment(slopes, start, end);
                }
            } else if (slopes[i][0] != currentSlope) { //If slope is different from previous
                end = i - 1;
                if (end - start + 1 >= 3) { //Check how many times the previous slope repeated (so, end = i - 1)
                    addSegment(slopes, start, end);
                }
                start = i; //Update start pointer for the next slope
                currentSlope = slopes[start][0];
            }
        }

    }

    private void addSegment(double[][] slopes, int start, int end) {
        Point[] pointsInSegment = new Point[end - start + 2]; // +2 since including 0th point
        pointsInSegment[0] = points[(int) slopes[0][1]]; //slopes[0][1] = Actual index of 0th point before Sorting
        int p1 = start;
        for (int i = 1; i < end - start + 2; i++) {
            pointsInSegment[i] = points[(int) slopes[p1][1]];
            p1++;
        }
        //Find min and max points in segment in order to create the Line Segment
        Point minPoint = pointsInSegment[0];
        Point maxPoint = pointsInSegment[0];
        for (int i = 0; i < pointsInSegment.length; i++) {
            minPoint = minPoint.compareTo(pointsInSegment[i]) < 0 ? minPoint : pointsInSegment[i];
            maxPoint = maxPoint.compareTo(pointsInSegment[i]) > 0 ? maxPoint : pointsInSegment[i];
        }
        if (isSegmentDuplicate(slopes[start][0], pointsInSegment)) {
            return;
        }
        resizeSegmentsArr();
        segments[numOfSegments] = new LineSegment(minPoint, maxPoint);
        addToFoundSegments(slopes[start][0], minPoint);
        numOfSegments++;
    }

    //Make segments array bigger if it's getting full
    private void resizeSegmentsArr() {
        if (numOfSegments >= (segments.length * 4 / 5)) {
            LineSegment[] temp = new LineSegment[segments.length * 3 / 2];
            for (int i = 0; i < numOfSegments; i++) {
                temp[i] = segments[i];
            }
            segments = temp;
        }
    }

    //If the slope matches the slope of a segment already found, it's either the same line or a parallel line
    //If the slope matches AND any there's a common point, then it's the same line; it's duplicate
    private boolean isSegmentDuplicate(double slope, Point[] points) {
        Node tempNode = foundSegmentsNode;
        while (tempNode != null) {
            if (tempNode.slope == slope) { //Check if same slope
                for (int i = 0; i < points.length; i++) { //If yes, check for a common point
                    if (tempNode.pointOnSegment == points[i]) {
                        return true;
                    }
                }
            }
            tempNode = tempNode.nextNode;
        }
        return false;
    }

    //Linked list impl containing Slope and any one Point from a found segment, for isSegmentDuplicate() method
    private Node foundSegmentsNode;

    private class Node {
        private Node nextNode;
        private double slope;
        private Point pointOnSegment;

        Node(double slope, Point point) {
            this.slope = slope;
            this.pointOnSegment = point;
        }
    }

    private void addToFoundSegments(double slope, Point point) {
        Node node = new Node(slope, point);
        if (foundSegmentsNode == null) { //This is the first node
            foundSegmentsNode = node;
        } else {
            node.nextNode = foundSegmentsNode;
            foundSegmentsNode = node;
        }
    }


    //segments[] could be larger than required;
    //After computing all segments, resize the array to fit the size of total segments correctly
    private void trimSegmentsArr() {
        LineSegment[] segments = new LineSegment[numOfSegments];
        for (int i = 0; i < segments.length; i++) {
            segments[i] = this.segments[i];
        }
        this.segments = segments;
    }

    // the number of line segments
    public int numberOfSegments() {
        return numOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("input40.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        in.close();
    }
}
