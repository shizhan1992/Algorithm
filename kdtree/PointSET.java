import java.util.Iterator;

public class PointSET {
	private SET<Point2D> pointsets;

	// construct an empty set of points
	public PointSET() {
		pointsets = new SET<Point2D>();
	}

	// is the set empty?
	public boolean isEmpty() {
		return pointsets.isEmpty();
	}

	// number of points in the set
	public int size() {
		return pointsets.size();
	}

	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (!contains(p)) {
			pointsets.add(p);
		}
	}

	// does the set contain the point p?
	public boolean contains(Point2D p) {
		return pointsets.contains(p);
	}

	// draw all of the points to standard draw
	public void draw() {
		StdDraw.setXscale();
        StdDraw.setYscale();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		Iterator<Point2D> it = pointsets.iterator();
		while (it.hasNext()) {
			it.next().draw();
		}
		StdDraw.show(0);
	}

	// all points in the set that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		SET<Point2D> containpoints = new SET<Point2D>();
		Iterator<Point2D> it = pointsets.iterator();
		while (it.hasNext()) {
			Point2D p = it.next();
			if (rect.contains(p)) {
				containpoints.add(p);
			}
		}
		return containpoints;
	}

	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D p) {
		if (pointsets.isEmpty()) {
			return null;
		} else {
			double mindistance = Double.POSITIVE_INFINITY;
			Point2D nearestpoint = null;
			Iterator<Point2D> it = pointsets.iterator();
			while (it.hasNext()) {
				Point2D point = it.next();
				double dis = p.distanceTo(point);
				if (dis < mindistance) {
					mindistance = dis;
					nearestpoint = point;
				}
			}
			return nearestpoint;
		}
	}

	// test
	public static void main(String[] args) {
		PointSET pointset = new PointSET();

		// read in the input
		String filename = args[0];
		In in = new In(filename);
		while (in.hasNextLine()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			pointset.insert(p);
		}
//		pointset.draw();

		// test nearest
		Point2D nearestpoint = pointset.nearest(new Point2D(0.5, 0.5));
		StdOut.println(nearestpoint.x() + "  " + nearestpoint.y());

//		// test range
//		Iterator<Point2D> it = pointset.range(new RectHV(0, 0, 1, 0.5))
//				.iterator();
//		while (it.hasNext()) {
//			Point2D p = it.next();
//			StdOut.println(p.x() + "  " + p.y());
//		}
	}
}
