import java.util.ArrayList;
import java.util.Arrays;

public class Brute2 {
    private final ArrayList<LineSegment> lineSegments;

    /**
     * Finds all line segments containing 4 points.
     *
     * @throws IllegalArgumentException the argument to the constructor is null,
     *                              if any point in the array is null, or if the
     *                              argument to the constructor contains a
     *                              repeated point.
     */
    public Brute2(Point[] points) {
        isLegal(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        lineSegments = new ArrayList<>();
        Arrays.sort(pointsCopy);

        for (int i = 0; i < pointsCopy.length - 3; i++) {
            for (int j = i + 1; j < pointsCopy.length - 2; j++) {
                for (int k = j + 1; k < pointsCopy.length - 1; k++) {
                    if (!isCollinear(pointsCopy, i, j, k)) {
                        continue;
                    }
                    for (int m = k + 1; m < pointsCopy.length; ++m) {
                        if (isCollinear(pointsCopy, i, k, m)) {
                            lineSegments.add(new LineSegment(pointsCopy[i], pointsCopy[m]));
                        }
                    }
                }
            }
        }
    }

    private boolean isCollinear(Point[] points, int i, int j, int k) {
        double firstSlope = points[i].slopeTo(points[j]);
        double secondSlope = points[i].slopeTo(points[k]);
        return firstSlope == secondSlope;
    }

    private void isLegal(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    /**
     * Returns the number of line segments.
     *
     * @return the number of line segments.
     */
    public int numberOfSegments() {
        return lineSegments.size();
    }

    /**
     * Returns a LineSegment array.
     *
     * @return a LineSegment array.
     */
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[numberOfSegments()]);
    }
}