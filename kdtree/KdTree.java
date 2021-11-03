import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size;
    private Point2D nearestPoint;

    private class Node {
        private final Point2D point;
        private Node left, right;
        private final boolean isVert;
        private RectHV rectHV;

        public Node(Point2D point, boolean isVert, Node previous) {
            this.point = point;
            this.isVert = isVert;

            if (previous == null)
                this.rectHV = new RectHV(0, 0, 1, 1);
            else {
                int cmp = previous.compareTo(point);
                double xmin = previous.rectHV.xmin();
                double xmax = previous.rectHV.xmax();
                double ymin = previous.rectHV.ymin();
                double ymax = previous.rectHV.ymax();


                if (isVert) {
                    if (cmp > 0)
                        ymax = previous.point.y();
                    else
                        ymin = previous.point.y();
                } else {
                    if (cmp > 0)
                        xmax = previous.point.x();
                    else
                        xmin = previous.point.x();
                }
                this.rectHV = new RectHV(xmin, ymin, xmax, ymax);
            }
        }

        public int compareTo(Point2D that) {
            if (isVert)
                return Double.compare(this.point.x(), that.x());
            else
                return Double.compare(this.point.y(), that.y());
        }

        public void draw() {
            StdDraw.setPenRadius(0.01);

            if (isVert) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(point.x(), rectHV.ymin(), point.x(), rectHV.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(rectHV.xmin(), point.y(), rectHV.xmax(), point.y());
            }

            StdDraw.setPenColor(StdDraw.BLACK);
            point.draw();

            if (left != null) {
                left.draw();
            }

            if (right != null) {
                right.draw();
            }
        }

    }

    public KdTree() {
        root = null;
        size = 0;
        nearestPoint = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        root = put(p, root, true, null);

    }

    private Node put(Point2D p, Node node, boolean isVert, Node previous) {

        if (node == null) {
            size++;
            return new Node(p, isVert, previous);
        }

        if (node.point.compareTo(p) == 0) return node;

        if (node.compareTo(p) > 0)
            node.left = put(p, node.left, !isVert, node);
        else
            node.right = put(p, node.right, !isVert, node);

        return node;

    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return get(root, p) != null;
    }

    private Node get(Node sNode, Point2D point) {
        if (sNode == null) return null;

        if (sNode.point.equals(point)) return sNode;

        if (sNode.compareTo(point) > 0)
            return get(sNode.left, point);
        else
            return get(sNode.right, point);

    }

    public void draw() {
        if (root != null)
            root.draw();
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) throw new IllegalArgumentException();

        return range(rect, root, new LinkedList<Point2D>());
    }

    private List<Point2D> range(RectHV rectHV, Node node, List<Point2D> points) {
        if (node == null) return points;

        if (node.rectHV.intersects(rectHV)) {
            if (rectHV.contains(node.point))
                points.add(node.point);

            range(rectHV, node.left, points);
            range(rectHV, node.right, points);
        }
        return points;
    }


    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (isEmpty()) return null;

        nearestPoint = root.point;
        nearestPoint(root, p);

        return nearestPoint;
    }

    private void nearestPoint(Node node, Point2D point) {
        if (node == null) return;

        if (node.rectHV.distanceSquaredTo(point) < nearestPoint.distanceSquaredTo(point)) {
            if (node.point.distanceSquaredTo(point) < nearestPoint.distanceSquaredTo(point))
                nearestPoint = node.point;

            if (node.compareTo(point) > 0) {
                nearestPoint(node.left, point);
                nearestPoint(node.right, point);
            } else {
                nearestPoint(node.right, point);
                nearestPoint(node.left, point);
            }
        }

    }

}
