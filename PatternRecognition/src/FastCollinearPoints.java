import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

  private LineSegment[] segments = new LineSegment[0];
  private ArrayList<Point> segmentsStartAndEndPoints = new ArrayList<>();
  private int numberOfCollinearPointsFound = 0;

  // finds all line segments containing 4 or more points
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new java.lang.NullPointerException();
    }

    if (points.length <= 1) {
      return;
    }
    
    Point[] otherPoints = new Point[points.length - 1];
    
    for (int i = 0; i < points.length; i++) {

      if (points[i] == null) {
        throw new java.lang.NullPointerException();
      }

      for (int j = 0; j < i; j++) {
        otherPoints[j] = points[j];
      }

      for (int j = i + 1; j < points.length; j++) {
        otherPoints[j - 1] = points[j];
      }

      Arrays.sort(otherPoints, points[i].slopeOrder());

      if (otherPoints[0] == null) {
        throw new java.lang.NullPointerException();
      }

      if (points[i].compareTo(otherPoints[0]) == 0) {
        throw new java.lang.IllegalArgumentException();
      }

      double slope = points[i].slopeTo(otherPoints[0]);
      int streak = 1;

      Point tmpP1 = points[i];
      Point tmpP2 = otherPoints[0];

      if (otherPoints[0].compareTo(points[i]) < 0) {
        tmpP1 = otherPoints[0];
        tmpP2 = points[i];
      }

      if (otherPoints[0].compareTo(points[i]) > 0) {
        tmpP1 = points[i];
        tmpP2 = otherPoints[0];
      }

      for (int j = 1; j < otherPoints.length; j++) {

        if (otherPoints[j] == null) {
          throw new java.lang.NullPointerException();
        }

        if (points[i].compareTo(otherPoints[j]) == 0) {
          throw new java.lang.IllegalArgumentException();
        }

        if (slope == points[i].slopeTo(otherPoints[j])) {

          if (otherPoints[j].compareTo(tmpP1) < 0) {
            tmpP1 = otherPoints[j];
          }

          if (otherPoints[j].compareTo(tmpP2) > 0) {
            tmpP2 = otherPoints[j];
          }

          streak++;
        } else {
          if (streak >= 3) {
            addPointsFound(tmpP1, tmpP2);
          }

          slope = points[i].slopeTo(otherPoints[j]);
          streak = 1;
          if (otherPoints[j].compareTo(points[i]) < 0) {
            tmpP1 = otherPoints[j];
            tmpP2 = points[i];
          }

          if (otherPoints[j].compareTo(points[i]) > 0) {
            tmpP1 = points[i];
            tmpP2 = otherPoints[j];
          }
        }
      }
      
      // if the latest streak of collinear points was at the end
      // of the list the change of slope won't be detected by the
      // if/else condition in the cycle hence we have to check
      // if there's one more add to perform
      if (streak >= 3) {
        addPointsFound(tmpP1, tmpP2);
      }
    }  
   
    segments = getSegmentsNoDuplicates();
    segmentsStartAndEndPoints = null;
    otherPoints = null;
  }

  private LineSegment[] getSegmentsNoDuplicates() {    
    ArrayList<LineSegment> tmpSegments = new ArrayList<>();
    
    for (int i = 0; i < numberOfCollinearPointsFound; i = i + 2) {
      if ((segmentsStartAndEndPoints.get(i) == null && 
          segmentsStartAndEndPoints.get(i + 1) == null)) {
        continue;
      }
      
      tmpSegments.add(new LineSegment(
          segmentsStartAndEndPoints.get(i), segmentsStartAndEndPoints.get(i + 1)));
      
      for (int j = i+2; j < numberOfCollinearPointsFound; j = j + 2) {
        
        if ((segmentsStartAndEndPoints.get(j) == null &&
            segmentsStartAndEndPoints.get(j + 1) == null)) {
          continue;
        }
        
        if (segmentsStartAndEndPoints.get(i)
            .compareTo(segmentsStartAndEndPoints.get(j)) == 0 &&
            segmentsStartAndEndPoints.get(i + 1)
            .compareTo(segmentsStartAndEndPoints.get(j + 1)) == 0) {          
          segmentsStartAndEndPoints.set(j, null);
          segmentsStartAndEndPoints.set(j + 1, null);
        }
      }
    }
    
    return tmpSegments.toArray(new LineSegment[tmpSegments.size()]);
  } 
  
  private void addPointsFound(Point p1, Point p2)
  {
    
    segmentsStartAndEndPoints.add(numberOfCollinearPointsFound++, p1);
    segmentsStartAndEndPoints.add(numberOfCollinearPointsFound++, p2);
  }
  
  // the number of line segments
  public int numberOfSegments() {
    if (segments == null) {
      return 0;
    }
    return segments.length;
  }

  // the line segments
  public LineSegment[] segments() {
    return segments.clone();
  }
}
