import java.awt.Font;
import edu.princeton.cs.algs4.StdDraw;

public class Main {

  public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }
    
    int border = 2500;

    // draw the points
    StdDraw.clear();
    StdDraw.setPenRadius(0.01);
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0 - border, 32768 + border);
    StdDraw.setYscale(0 - border, 32768 + border);

    StdDraw.setPenColor(StdDraw.WHITE);
    StdDraw.filledSquare((0 - border + 32768 + border) / 2, (0 - border + 32768 + border) / 2,
        (0 - border + 32768 + border) / 2);

    StdDraw.setPenColor(StdDraw.BLUE);

    StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 8));

    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();
    
    StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.text((0 - border + 32768 + border) / 2, -border / 2,
       "Finding segments...");
    StdDraw.show();

    // print and draw the line segments
    long startTime = System.nanoTime();    
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    //FastCollinearPoints collinear = new FastCollinearPoints(points);
    long estimatedTime = System.nanoTime() - startTime;
    
    StdDraw.clear();
    
    StdDraw.setPenRadius(0.008);
    StdDraw.setPenColor(StdDraw.BLUE);
    for (Point p : points) {
      p.draw();
    }
    
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.setPenRadius();
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    
    StdDraw.setPenColor(StdDraw.BLACK);
    
    StdDraw.text((0 - border + 32768 + border) / 2, -border / 2,
        (float)estimatedTime/1000000000 + " seconds, " + points.length + " points, " + collinear.numberOfSegments() + "segments");

    StdDraw.show();
  }
}
