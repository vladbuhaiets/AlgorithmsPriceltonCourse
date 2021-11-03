package collinear;

import collinear.LineSegment;

import java.awt.*;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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
        Arrays.sort(points);
        LineSegment[] tempLineSegments = new LineSegment[points.length * points.length];
        int index = 0;


        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int p = k + 1; p < points.length; p++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) && points[i].slopeTo(points[k]) == points[i].slopeTo(points[p])) {
                            tempLineSegments[index] = new LineSegment(points[i], points[p]);
                            index++;
                        }
                    }
                }
            }
        }
        lineSegments = new LineSegment[index];
        for (int i = 0; i < lineSegments.length; i++) {
            lineSegments[i] = tempLineSegments[i];
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

}
