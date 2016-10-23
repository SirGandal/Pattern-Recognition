import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class PercolationFileToPatternRecognition {

  public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
    
    PrintWriter writer = new PrintWriter("src/resources/yoda-80-pattern.txt", "UTF-8");
    final int PERCOLATION_EDGE_SIZE = 80;
    In in = new In(args[0]);
    int n = in.readInt();
    
    writer.println(n);
    
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      
      int row = in.readInt();
      int col = in.readInt();
      
      int x = (int) scale(col, 1, PERCOLATION_EDGE_SIZE, 0, 32768);
      int y = (int) scale(row, 1, PERCOLATION_EDGE_SIZE, 32768, 0);
      
      writer.println(x + " " + y);
      
      points[i] = new Point(x, y);
    }
    writer.close();
  }
  
  private static double scale(final double valueIn, final double baseMin, final double baseMax, final double limitMin, final double limitMax) {
    return ((limitMax - limitMin) * (valueIn - baseMin) / (baseMax - baseMin)) + limitMin;
  }

}
