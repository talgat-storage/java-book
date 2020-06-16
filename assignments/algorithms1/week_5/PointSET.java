import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> set;

    public PointSET() {
        set = new TreeSet<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        return set.contains(p);
    }

    public void draw() {
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        List<Point2D> list = new ArrayList<>();

        for (Point2D p : set) {
            if (rect.contains(p)) {
                list.add(p);
            }
        }

        return list;
    }

    public Point2D nearest(Point2D point) {
        if (point == null) throw new IllegalArgumentException();

        double distance = Double.POSITIVE_INFINITY;
        Point2D nearest = null;

        for (Point2D p : set) {
            if (nearest == null || p.distanceSquaredTo(point) < distance) {
                nearest = p;
                distance = p.distanceSquaredTo(point);
            }
        }

        return nearest;
    }

    public static void main(String[] args) {
    }
}
