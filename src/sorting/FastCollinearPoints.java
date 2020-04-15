/**
 * @author Davis Jeffrey
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * --Assignment
 * <p>
 * Given an array of points, find maximal line segment containing 4 or more points exactly once
 * Constraints: Time = O(n^2 log n), Space = n + number of line segments
 */
public class FastCollinearPoints {

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
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
        FastCollinearPoints2 collinear = new FastCollinearPoints2(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private Point[] points;
    private LineSegment[] segments;
    private int numOfSegments = 0;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        this.points = points;
        segments = new LineSegment[points.length];
        validatePoints(points);
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

    }

    //Populate an array with slopes wrt Point p, and index of each point
    private double[][] getSlopes(Point p, Point[] points) {
        return null;
    }

    //Takes arr[slopes][indices]; sorts given array by slopes, but maintaining slope-index pair
    private void sortSlopes(double[][] slopes) {
    }

    private void findAndAddSegments(double[][] slopes) {
        double currentSlope = slopes[0][0]; //[0][0] == Negative Infinity (Slope of point to itself)
        int start = 0;
        int end = 0;
        for (int i = 1; i < slopes.length; i++) {
            if (slopes[i][0] != currentSlope) { //Next slope starts
                end = i - 1;
                if (end - start + 1 >= 3) { //If >3 consecutive slopes are the same, it's collinear(incl point[0])
                    addSegment(slopes, start, end);
                }
                start = i;
            }
        }

    }

    private void addSegment(double[][] slopes, int start, int end) {
        if (isSegmentDuplicate()) {
            return;
        }
        Point[] pointsInSegment = new Point[end - start + 2]; // +2 since including 0th point
        pointsInSegment[0] = points[(int)slopes[0][1]]; //slopes[0][1] = Actual index of 0th point before Sorting
        for (int i = 1; i < end - start + 2; i++) {
            pointsInSegment[i] = points[(int)slopes[start + i - 1][1]]; //[start + i - 1] since i = 1 initially, not 0
        }

        //Find min and max points in segment in order to create the Line Segment
        Point minPoint = pointsInSegment[0];
        Point maxPoint = pointsInSegment[0];
        for (int i = 0; i < pointsInSegment.length; i++) {
            minPoint = minPoint.compareTo(pointsInSegment[i]) < 0? minPoint : pointsInSegment[i];
            maxPoint = maxPoint.compareTo(pointsInSegment[i]) > 0? maxPoint : pointsInSegment[i];
        }
        segments[numOfSegments] = new LineSegment(minPoint, maxPoint);
        numOfSegments++;
    }

    private boolean isSegmentDuplicate() {
        return true;
    }

    //Initially, segments[] is larger than required;
    //After computing segments, resize the array to size of total segments
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





}
