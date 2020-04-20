import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {  // finds all line segments containing 4 or more points
        if (points == null) throw new IllegalArgumentException();
        for (Point point : points) if (point == null) throw new IllegalArgumentException();

        points = points.clone();

        Arrays.sort(points);
        int n = points.length;
        for (int i = 1; i < n; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) throw new IllegalArgumentException();
        }

        ArrayList<LineSegment> a = new ArrayList<LineSegment>();
        for (int i = 0; i < n; i++) {
            Arrays.sort(points);

            Point p0 = points[i];
            Arrays.sort(points, p0.slopeOrder());
//            StdOut.println("Point p0: " + p0);

            int j = 1;
            int count = 1;
            boolean isSmallest = true;
            while (j < n) {
//                StdOut.println("Point j: " + points[j] + ", slope: " + p0.slopeTo(points[j]));
                if (p0.slopeTo(points[j]) == p0.slopeTo(points[j - 1])) {
                    count += 1;
                    if (p0.compareTo(points[j]) > 0 || p0.compareTo(points[j - 1]) > 0) {
//                        StdOut.println(p0 + " is larger than " + points[j]);
                        isSmallest = false;
                    }
                    if (j == n - 1 && count >= 3 && isSmallest) {
//                        StdOut.println("Add segment: " + p0 + " -> " + points[j]);
                        a.add(new LineSegment(p0, points[j]));
                    }
                }
                else {
                    if (count >= 3 && isSmallest) {
//                        StdOut.println("Add segment: " + p0 + " -> " + points[j - 1]);
                        a.add(new LineSegment(p0, points[j - 1]));
                    }
                    count = 1;
                    isSmallest = true;
                }
                j += 1;
            }
        }

        LineSegment[] temp = new LineSegment[a.size()];
        this.lineSegments = a.toArray(temp);
    }
    public int numberOfSegments() {  // the number of line segments
        return this.lineSegments.length;
    }
    public LineSegment[] segments() {  // the line segments
        return this.lineSegments.clone();
    }

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
        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.RED);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        StdDraw.setPenRadius();
        StdDraw.setPenColor();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
