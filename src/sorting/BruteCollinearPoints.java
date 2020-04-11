/**
 * @author Davis Jeffrey
 */
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;

/**
 * --Assignment
 *
 * Examines 4 points at a time and checks whether they all lie on the same line segment
 * Logic: If 4 points p, q, r, and s are collinear, three slopes(Δ) Δ(p and q) == Δ(p and r) == Δ(p and s)
 * Brute force; Constraints: Time = O(n4), Space = n + number of segments returned
 */
public class BruteCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] segments = new LineSegment[20]; //Not resizing for brevity
    private double[] coveredSlopes = new double[20]; //Store slopes of lines in segments, so it's not repeated

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        double slope;
        for (int i = 0; i < points.length; i++) {
            //StdOut.println("");
            //StdOut.println("Loop starts");
            //StdOut.print("i = " + i);
            outerloop:
            for (int j = 0; j < points.length; j++) {
                //StdOut.print(" j = " + i);
                if(i == j) {
                    continue;
                }
                slope = points[i].slopeTo(points[j]);   //Find Δ(p0-p1)
                StdOut.println("Slope of " + points[i] + " to " + points[j] + " = " + slope);
                for (int k = 0; k < points.length; k++) {
                    //StdOut.print(" k = " + k);
                    if(k == i || k == j) {
                        continue;
                    }
                    //If Δ(p2-p1) == Δ(p0-p1), look for one more point with same Δ
                    if (points[j].slopeTo(points[k]) == slope) {
                        for (int l = 0; l < points.length; l++) {
                            //StdOut.print(" l = " + l);
                            if(l == i || l == j || l == k) {
                                continue;
                            }
                            if (points[k].slopeTo(points[l]) == slope) { //Δ(p3-p2) == Δ(p2-p1) == Δ(p0-p1)
                                Point[] commonPoints = {points[i], points[j], points[k], points[l]};
                                StdOut.println("Adding segment; (i, j, k, l) =  ( " + i + " , " + j + " , " + k + " , " + l + " )");
                                addSegment(commonPoints);
                            }
                        }
                    }
                }
            }
        }


    }

    private boolean isSlopeAlreadyCovered(double slope) {
        for (double coveredSlope : coveredSlopes) { //Continue if a line segment with this slope has already been saved
            if (slope == coveredSlope) {
                return true;
            }
        }
        return false;
    }

    //Takes an array of points, stores the longest line segment
    private void addSegment(Point[] points) {
        if (isSlopeAlreadyCovered(points[0].slopeTo(points[1]))) {
            return;
        }
        Point minPoint = points[0];
        Point maxPoint = points[0];
        for(Point p : points) {
            minPoint = (minPoint.compareTo(p) < 0)? minPoint : p;
            maxPoint = (maxPoint.compareTo(p) > 0)? maxPoint : p;
        }
        LineSegment segment = new LineSegment(minPoint, maxPoint);
        segments[numberOfSegments] = segment;
        coveredSlopes[numberOfSegments++] = minPoint.slopeTo(maxPoint);
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {
        Point  point1 = new Point(1, 1);
        Point  point2 = new Point(2, 2);
        Point  point3 = new Point(3, 3);
        Point  point4 = new Point(4, 4);
        Point[] points = {point1, point2, point3, point4};
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        StdOut.println("numberOfSegments = " + bcp.numberOfSegments());
        for(LineSegment segment : bcp.segments()) {
            StdOut.print(segment + ", ");
        }
        StdOut.println("\nCovered slopes = ");
        for(double slope : bcp.coveredSlopes) {
            StdOut.print(slope + ", ");
        }
    }
}