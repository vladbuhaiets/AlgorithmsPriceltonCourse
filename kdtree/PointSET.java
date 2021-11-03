import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> treeSet;

    public PointSET()                               // construct an empty set of points
    {
        treeSet = new TreeSet<>();
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return treeSet.isEmpty();
    }

    public int size()                         // number of points in the set
    {
        return treeSet.size();
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) throw new IllegalArgumentException();
        treeSet.add(p);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null) throw new IllegalArgumentException();
        return treeSet.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        for (Point2D point : treeSet) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) throw new IllegalArgumentException();
        TreeSet<Point2D> newSet = new TreeSet<>();
        for (Point2D point : treeSet) {
            if (rect.contains(point))
                newSet.add(point);
        }
        return newSet;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        Point2D nearest = null;

        for (Point2D point : treeSet) {
            if (nearest == null) {
                nearest = point;
                continue;
            }
            if (p.distanceSquaredTo(nearest) > p.distanceSquaredTo(point))
                nearest = point;
        }
        return nearest;
    }

}