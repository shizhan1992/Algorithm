/*************************************************************************
 * Name: shizhan
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

	// compare points by slope
	public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
		@Override
		public int compare(Point o1, Point o2) {
			return Double.compare(slopeTo(o1), slopeTo(o2));
		}
	};

	private final int x; // x coordinate
	private final int y; // y coordinate

	// create the point (x, y)
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// plot this point to standard drawing
	public void draw() {
		StdDraw.point(x, y);
	}

	// draw line between this point and that point to standard drawing
	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	// slope between this point and that point
	public double slopeTo(Point that) {
		if (this.compareTo(that) == 0)
			return Double.NEGATIVE_INFINITY;
		else {
			if (this.y == that.y)
				return 0.0;
			if (this.x == that.x)
				return Double.POSITIVE_INFINITY;

			return (double) (that.y - this.y) / (that.x - this.x);
		}
	}

	// is this point lexicographically smaller than that one?
	// comparing y-coordinates and breaking ties by x-coordinates
	public int compareTo(Point that) {
		if (this.y < that.y)
			return -1;
		else if (this.y == that.y) {
			if (this.x < that.x)
				return -1;
			else if (this.x == that.x)
				return 0;
			else
				return 1;
		} else
			return 1;
	}

	// return string representation of this point
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	// unit test
	public static void main(String[] args) {
		System.out.println((double) 3 / 2);
	}
}