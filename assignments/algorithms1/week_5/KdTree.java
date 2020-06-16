import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private enum Position {
        VERTICAL, HORIZONTAL
    }

    private class Node {
        public Point2D point;
        public RectHV rectangle;
        public Position position;
        public Node left;
        public Node right;

        public Node(Point2D point, RectHV rectangle, Position position) {
            this.point = point;
            this.rectangle = rectangle;
            this.position = position;
        }
    }

    private int size = 0;
    private Node root = null;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D point) {
        if (point == null) throw new IllegalArgumentException();

        root = insert(root, point, new RectHV(0, 0, 1, 1), Position.VERTICAL);
    }

    private Node insert(Node node, Point2D point, RectHV rectangle, Position position) {
        if (node == null) {
            size += 1;

            return new Node(point, rectangle, position);
        }

        if (node.point.equals(point)) return node;

        if (position == Position.VERTICAL) {
            if (point.x() < node.point.x()) {
                node.left = insert(node.left, point,
                        new RectHV(node.rectangle.xmin(), node.rectangle.ymin(),
                                node.point.x(), node.rectangle.ymax()),
                        Position.HORIZONTAL
                );
            }
            else {
                node.right = insert(node.right, point,
                        new RectHV(node.point.x(), node.rectangle.ymin(),
                                node.rectangle.xmax(), node.rectangle.ymax()),
                        Position.HORIZONTAL
                );
            }
        }
        else {
            if (point.y() < node.point.y()) {
                node.left = insert(node.left, point,
                        new RectHV(node.rectangle.xmin(), node.rectangle.ymin(),
                                node.rectangle.xmax(), node.point.y()),
                        Position.VERTICAL
                );
            }
            else {
                node.right = insert(node.right, point,
                        new RectHV(node.rectangle.xmin(), node.point.y(),
                                node.rectangle.xmax(), node.rectangle.ymax()),
                        Position.VERTICAL
                );
            }
        }

        return node;
    }

    public boolean contains(Point2D point) {
        if (point == null) throw new IllegalArgumentException();

        return contains(root, point);
    }

    private boolean contains(Node node, Point2D point) {
        if (node == null) return false;

        if (node.point.equals(point)) return true;

        if (node.position == Position.VERTICAL) {
            if (point.x() < node.point.x()) return contains(node.left, point);
            return contains(node.right, point);
        }

        if (point.y() < node.point.y()) return contains(node.left, point);
        return contains(node.right, point);
    }

    public void draw() {
        drawInorder(root);
    }

    private void drawInorder(Node node) {
        if (node == null) return;

        StdDraw.setPenRadius(0.01);
        if (node.position == Position.VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(
                    node.point.x(), node.rectangle.ymin(),
                    node.point.x(), node.rectangle.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(
                    node.rectangle.xmin(), node.point.y(),
                    node.rectangle.xmax(), node.point.y());
        }
        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(node.point.x(), node.point.y());

        drawInorder(node.left);
        drawInorder(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        List<Point2D> list = new ArrayList<>();

        range(root, rect, list);

        return list;
    }

    private void range(Node node, RectHV rect, List<Point2D> list) {
        if (node == null) return;

        if (!rect.intersects(node.rectangle)) return;

        if (rect.contains(node.point)) {
            list.add(node.point);
        }

        range(node.left, rect, list);
        range(node.right, rect, list);
    }

    public Point2D nearest(Point2D point) {
        if (point == null) throw new IllegalArgumentException();

        if (root == null) return null;

        return nearest(root, point, root.point);
    }

    private Point2D nearest(Node node, Point2D point, Point2D nearest) {
        if (node == null) return nearest;

        if (nearest.distanceSquaredTo(point) < node.rectangle.distanceSquaredTo(point)) return nearest;

        if (node.point.distanceSquaredTo(point) < nearest.distanceSquaredTo(point)) {
            nearest = node.point;
        }

        if (node.left != null && node.left.rectangle.contains(point)) {
            nearest = nearest(node.left, point, nearest);
            nearest = nearest(node.right, point, nearest);
        }
        else {
            nearest = nearest(node.right, point, nearest);
            nearest = nearest(node.left, point, nearest);
        }

        return nearest;
    }

    public static void main(String[] args) {
    }
}
