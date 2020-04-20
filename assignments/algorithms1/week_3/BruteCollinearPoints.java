import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {  // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException();
        for (Point point : points) if (point == null) throw new IllegalArgumentException();

        points = points.clone();

        Arrays.sort(points);

//        for (Point p : points) {
//            StdOut.println(p);
//        }
//        StdOut.println();

        int n = points.length;
        for (int i = 1; i < n; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) throw new IllegalArgumentException();
        }

        ArrayList<LineSegment> a = new ArrayList<LineSegment>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int m = k + 1; m < n; m++) {
                        double slope1 = points[i].slopeTo(points[j]);
                        double slope2 = points[i].slopeTo(points[k]);
                        double slope3 = points[i].slopeTo(points[m]);

                        if (slope1 == Double.NEGATIVE_INFINITY) {
                            continue;
                        }

                        if (slope1 == slope2 && slope2 == slope3) {
                            a.add(new LineSegment(points[i], points[m]));
                        }
                    }
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
