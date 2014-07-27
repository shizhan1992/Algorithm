public class KdTree {

	private static class Node {
		private Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this
		// node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree
		private boolean isvertical;

		public Node(Point2D p, RectHV rect, Node lb, Node rt, boolean isvertical) {
			super();
			this.p = p;
			this.rect = rect;
			this.lb = lb;
			this.rt = rt;
			this.isvertical = isvertical;
		}
	}

	private int N;
	private Node root;
	private SET<Point2D> pointsets;
	private double distance;
	private boolean flag;

	// construct an empty set of points
	public KdTree() {
		N = 0;
		root = null;
	}

	// is the set empty?
	public boolean isEmpty() {
		return root == null ? true : false;
	}

	// number of points in the set
	public int size() {
		return N;
	}

	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (N == 0) {
			root = new Node(p, new RectHV(0, 0, 1, 1), null, null, true);
			N++;
		} else {
			N++;
			root = insert(root, p);
		}
	}

	private Node insert(Node rootnode, Point2D p) {
		if (rootnode.p.compareTo(p) == 0) {
			N--;
			return rootnode;
		}

		if (rootnode.isvertical) {
			if (p.x() < rootnode.p.x()) {
				if (rootnode.lb == null) {
					rootnode.lb = new Node(p, new RectHV(rootnode.rect.xmin(),
							rootnode.rect.ymin(), rootnode.p.x(), rootnode.rect
									.ymax()), null, null, false);
				} else {
					rootnode.lb = insert(rootnode.lb, p);
				}
			} else {
				if (rootnode.rt == null)
					rootnode.rt = new Node(p, new RectHV(rootnode.p.x(),
							rootnode.rect.ymin(), rootnode.rect.xmax(),
							rootnode.rect.ymax()), null, null, false);
				else
					rootnode.rt = insert(rootnode.rt, p);
			}
		} else {
			if (p.y() < rootnode.p.y()) {
				if (rootnode.lb == null)
					rootnode.lb = new Node(p, new RectHV(rootnode.rect.xmin(),
							rootnode.rect.ymin(), rootnode.rect.xmax(),
							rootnode.p.y()), null, null, true);
				else
					rootnode.lb = insert(rootnode.lb, p);
			} else {
				if (rootnode.rt == null)
					rootnode.rt = new Node(p, new RectHV(rootnode.rect.xmin(),
							rootnode.p.y(), rootnode.rect.xmax(), rootnode.rect
									.ymax()), null, null, true);
				else
					rootnode.rt = insert(rootnode.rt, p);
			}
		}
		return rootnode;
	}

	// does the set contain the point p?
	public boolean contains(Point2D p) {
		Node x = root;
		while (x != null) {
			if (x.p.compareTo(p) == 0)
				return true;
			if (x.isvertical) {
				if (p.x() < x.p.x())
					x = x.lb;
				else
					x = x.rt;
			} else {
				if (p.y() < x.p.y())
					x = x.lb;
				else
					x = x.rt;
			}
		}
		return false;
	}

	// draw all of the points to standard draw
	public void draw() {
		StdDraw.setXscale();
		StdDraw.setYscale();
		traverse(root);
		StdDraw.show(0);
	}

	private void traverse(Node root) {
		if (root == null)
			return;

		StdDraw.setPenRadius(.01);
		StdDraw.setPenColor(StdDraw.BLACK);
		root.p.draw();
		StdDraw.setPenRadius();
		if (root.isvertical) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(root.p.x(), root.rect.ymin(), root.p.x(), root.rect
					.ymax());
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(root.rect.xmin(), root.p.y(), root.rect.xmax(), root.p
					.y());
		}
		traverse(root.lb);
		traverse(root.rt);
	}

	// all points in the set that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		pointsets = new SET<Point2D>();
		range(rect, root);
		return pointsets;
	}

	private void range(RectHV rect, Node root) {
		if (root != null && rect.intersects(root.rect)) {
			if (rect.contains(root.p))
				pointsets.add(root.p);
			range(rect, root.lb);
			range(rect, root.rt);
		}
	}

	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D rp) {
		flag = true;
		distance = Double.POSITIVE_INFINITY;
		Point2D point = null;
		point = nearest(root, rp, point, false);
		return point;
	}

	private Point2D nearest(Node root1, Point2D rp, Point2D np, boolean reverse) {
		if (root1 != null) {
			double rootdis = root1.p.distanceSquaredTo(rp);
			if (rootdis < distance) {
				distance = rootdis;
				np = root1.p;
			}

			if (reverse || root1.lb != null && root1.lb.rect.contains(rp)) {
				if (!reverse)
					np = nearest(root1.lb, rp, np, false);
				if (root1.rt != null
						&& root1.rt.rect.distanceSquaredTo(rp) < distance) {
					np = nearest(root1.rt, rp, np, true);
				}
				flag = false;
			}
			if (reverse || root1.rt != null && root1.rt.rect.contains(rp)) {
				if (!reverse)
					np = nearest(root1.rt, rp, np, false);
				if (root1.lb != null
						&& root1.lb.rect.distanceSquaredTo(rp) < distance) {
					np = nearest(root1.lb, rp, np, true);
				}
				flag = false;
			}
			if (!reverse && flag) {
				if (root1.lb != null)
					np = nearest(root1.lb, rp, np, false);
				if (root1.rt != null)
					np = nearest(root1.rt, rp, np, false);
			}
			return np;
		} else {
			return np;
		}
	}

	public static void main(String[] args) {
		KdTree kdtree = new KdTree();
		PointSET pointset = new PointSET();
		// read in the input
		String filename = args[0];
		In in = new In(filename);
		while (in.hasNextLine()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			kdtree.insert(p);
			pointset.insert(p);
		}
//		StdOut.println(kdtree.size());
//		kdtree.draw();
//		In rin = new In(filename);
//		while (rin.hasNextLine()) {
//			double x = rin.readDouble();
//			double y = rin.readDouble();
//			Point2D p = new Point2D(x, y);
//			StdOut.println(kdtree.contains(p));
//		}
//		Iterator<Point2D> it = kdtree.range(new RectHV(0.21, 0, 0.5, 0.5))
//				.iterator();
//		while (it.hasNext()) {
//			StdOut.println(it.next().toString());
//		}
//		for (int i = 0; i < 100000; i++) {
//			Point2D rq = new Point2D(StdRandom.uniform(), StdRandom.uniform());
//			Point2D kd = kdtree.nearest(rq);
//			Point2D pset = pointset.nearest(rq);
//			if (kd.compareTo(pset) != 0) {
//				StdOut.println(rq.toString());
//				StdOut.println(kd.toString());
//				StdOut.println(pset.toString());
//				break;
//			}
//			StdOut.println(i);
//		}
//		StdOut.println(kdtree.nearest(
//				new Point2D(0.463004571293938, 0.745171979092912)).toString());
	}
}
