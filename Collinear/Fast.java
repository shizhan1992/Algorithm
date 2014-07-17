import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Fast {
	public static void main(String[] args) {
		HashMap<Double, ArrayList<Point>> linerecord = new HashMap<Double, ArrayList<Point>>();

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
		Point[] extrapoints = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			Point p = new Point(x, y);
			p.draw();
			points[i] = p;
			extrapoints[i] = p;
		}

		// loop for every origin
		for (int i = 0; i < N; i++) {
			Arrays.sort(extrapoints, points[i].SLOPE_ORDER);
			// examines 3 points at a time
			for (int j = 1; j < N - 2;) {
				int window = j;
				double slope1 = points[i].slopeTo(extrapoints[window++]);
				double slope2 = points[i].slopeTo(extrapoints[window++]);
				double slope3 = points[i].slopeTo(extrapoints[window++]);
				if (slope1 == slope2 && slope2 == slope3) {
					if (linerecord.containsKey(slope1)
							&& linerecord.get(slope1).contains(points[i])) {
						j += 2;
					} else {
						ArrayList<Point> linepoints = new ArrayList<Point>();
						// get the max length of a line
						while (window <= N - 1) {
							if (slope1 != points[i]
									.slopeTo(extrapoints[window]))
								break;
							else
								window++;
						}

						// sort the line
						for (int x = j; x < window; x++) {
							linepoints.add(extrapoints[x]);
						}
						linepoints.add(points[i]);
						Collections.sort(linepoints);

						if (linerecord.containsKey(slope1))
							linerecord.get(slope1).addAll(linepoints);
						else
							linerecord.put(slope1, linepoints);
						for (int x = 0; x < linepoints.size() - 1; x++) {
							System.out.print(linepoints.get(x).toString()
									+ "->");
						}
						System.out.println(linepoints
								.get(linepoints.size() - 1).toString());
						linepoints.get(0).drawTo(
								linepoints.get(linepoints.size() - 1));
						j = window;
					}

				} else if (slope2 == slope3)
					j += 1;
				else
					j += 2;
			}
		}

		// display to screen all at once
		StdDraw.show(0);

		// reset the pen radius
		StdDraw.setPenRadius();
	}
}
