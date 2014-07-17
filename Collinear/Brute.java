import java.util.ArrayList;
import java.util.Collections;

public class Brute {
	public static void main(String[] args) {
		// rescale coordinates and turn on animation mode
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);
		StdDraw.setPenRadius(0.01); // make the points a bit larger

		// read in the input
		String filename = args[0];
		In in = new In(filename);
		int N = in.readInt();
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			Point p = new Point(x, y);
			p.draw();
			points[i] = p;
		}

		// examines 4 points at a time
		for (int p = 0; p < N; p++) {
			for (int q = p + 1; q < N; q++) {
				for (int r = q + 1; r < N; r++) {
					for (int s = r + 1; s < N; s++) {
						ArrayList<Point> fourpoints = new ArrayList<Point>();
						fourpoints.add(points[p]);
						fourpoints.add(points[q]);
						fourpoints.add(points[r]);
						fourpoints.add(points[s]);
						double slope1 = fourpoints.get(0).slopeTo(
								fourpoints.get(1));
						double slope2 = fourpoints.get(0).slopeTo(
								fourpoints.get(2));
						double slope3 = fourpoints.get(0).slopeTo(
								fourpoints.get(3));

						if (slope1 == slope2 && slope2 == slope3) {
							Collections.sort(fourpoints);
							System.out.println(fourpoints.get(0).toString()
									+ "->" + fourpoints.get(1).toString()
									+ "->" + fourpoints.get(2).toString()
									+ "->" + fourpoints.get(3).toString());
							fourpoints.get(0).drawTo(fourpoints.get(3));
						}
					}
				}
			}
		}

		// display to screen all at once
		StdDraw.show(0);

		// reset the pen radius
		StdDraw.setPenRadius();
	}
}
