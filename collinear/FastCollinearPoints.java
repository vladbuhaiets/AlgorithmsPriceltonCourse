package collinear;

import collinear.LineSegment;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null)
            throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }

        Point[] pCopy = points.clone();
        Arrays.sort(pCopy);

        for (int i = 0; i < pCopy.length - 3; i++) {
            Arrays.sort(pCopy);
            Arrays.sort(pCopy, pCopy[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < pCopy.length; last++) {

                while (last < pCopy.length
                        && Double.compare(pCopy[p].slopeTo(pCopy[first]), pCopy[p].slopeTo(pCopy[last])) == 0) {
                    last++;
                }

                if (last - first >= 3 && pCopy[p].compareTo(pCopy[first]) < 0) {
                    lineSegments.add(new LineSegment(pCopy[p], pCopy[last - 1]));
                }

                first = last;
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }
}
