import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y == that.y) return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        if (this.y == that.y) {
            return 0;
        }
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return 1;
        else if (this.x < that.x) return -1;
        else if (this.x > that.x) return 1;
        return 0;
    }

    private class BySlope implements Comparator<Point> {
        private final Point p0;

        public BySlope(Point p0) {
            this.p0 = p0;
        }

        public int compare(Point p1, Point p2) {
            double slope1 = p0.slopeTo(p1);
            double slope2 = p0.slopeTo(p2);

            if (slope1 < slope2) {
                return -1;
            }
            else if (slope1 > slope2) {
                return 1;
            }

            return 0;
        }
    }

    public Comparator<Point> slopeOrder() {
        return new BySlope(this);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
