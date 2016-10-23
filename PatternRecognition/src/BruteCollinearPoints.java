import java.util.ArrayList;

public class BruteCollinearPoints {

  private LineSegment[] segments = new LineSegment[0];
  private int numberOfCollinearPointsFound = 0;
  private ArrayList<Point> segmentsStartAndEndPoints = new ArrayList<>();

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
    
    if (points == null) {
      throw new java.lang.NullPointerException();
    }
    
    // Detection of corner cases is done as we go to prevent
    // an extra for loop at the beginning
    for (int i = 0; i < points.length; i++) {
      
      if (points[i] == null) {
        throw new java.lang.NullPointerException();
      }
      
      for (int j = i + 1; j < points.length; j++) {
        
        if (points[j] == null) {
          throw new java.lang.NullPointerException();
        }        
        
        
        if (points[i].compareTo(points[j]) == 0) {             
          throw new java.lang.IllegalArgumentException();
        }
        
        for (int t = j + 1; t < points.length; t++) {
          
          if (points[t] == null) {
            throw new java.lang.NullPointerException();
          }
          
          if (points[i].compareTo(points[t]) == 0 ||  
              points[j].compareTo(points[t]) == 0) {             
            throw new java.lang.IllegalArgumentException();
          }
          
          if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[t])) {
            continue;
          }
          
          for (int z = t + 1; z < points.length; z++) {

            if (points[z] == null) {
              throw new java.lang.NullPointerException();
            }
            
            if (points[i].compareTo(points[z]) == 0 ||  
                points[j].compareTo(points[z]) == 0 || 
                points[t].compareTo(points[z]) == 0) {
              throw new java.lang.IllegalArgumentException();
            }
            
            if (points[i].slopeTo(points[t]) != points[i].slopeTo(points[z])) {
              continue;
            }

            // find which point is the beginning and which one is the end of the segment
            Point p1 = getLowerPoint(points[i], points[j], points[t], points[z]);
            Point p2 = getHigherPoint(points[i], points[j], points[t], points[z]);

            // finds overlapped segments and unites them
            boolean found = false;
            for (int k = 0; k < numberOfCollinearPointsFound && !found; k = k + 2) {

              if (p1.slopeTo(p2) == segmentsStartAndEndPoints.get(k).
                  slopeTo(segmentsStartAndEndPoints.get(k + 1))) {

                // p1->p2 is contained in an existing segment
                if (p1.compareTo(segmentsStartAndEndPoints.get(k)) >= 0 && 
                    p2.compareTo(segmentsStartAndEndPoints.get(k + 1)) <= 0 &&
                    p1.slopeTo(p2) == 
                    segmentsStartAndEndPoints.get(k).slopeTo(p2) && 
                    p1.slopeTo(p2) == 
                    p1.slopeTo(segmentsStartAndEndPoints.get(k + 1))) {
                  found = true;
                  continue;
                }

                // p1->p2 sits on top of the beginning of an existing segment
                if (p1.compareTo(segmentsStartAndEndPoints.get(k)) <= 0 && 
                    p2.compareTo(segmentsStartAndEndPoints.get(k)) >= 0 && 
                    p1.slopeTo(p2) == 
                    p1.slopeTo(segmentsStartAndEndPoints.get(k + 1))) {
                  segmentsStartAndEndPoints.set(k, p1);
                  found = true;
                } else {

                  // p1->p2 sits on top of the end of an existing segment
                  if (p1.compareTo(segmentsStartAndEndPoints.get(k + 1)) <= 0 && 
                      p2.compareTo(segmentsStartAndEndPoints.get(k + 1)) >= 0 && 
                      p1.slopeTo(p2) ==
                      segmentsStartAndEndPoints.get(k).slopeTo(p2)) {
                    segmentsStartAndEndPoints.set(k+1, p2);
                    found = true;

                  }
                }
              }
            }

            if (!found) {
              segmentsStartAndEndPoints.add(numberOfCollinearPointsFound++, p1);
              segmentsStartAndEndPoints.add(numberOfCollinearPointsFound++, p2);
            }
          }
        }
      }
    }
    
    segments = getSegments();
    segmentsStartAndEndPoints = null;
  }

  private Point getLowerPoint(Point p1, Point p2, Point p3, Point p4) {
    if (p1.compareTo(p2) < 0 && 
        p1.compareTo(p3) < 0
        && p1.compareTo(p4) < 0) {
      // p1 is the beginning of the segment
      return p1;
    }

    if (p2.compareTo(p1) < 0 && 
        p2.compareTo(p3) < 0
        && p2.compareTo(p4) < 0) {
      // p2 is the beginning of the segment
      return  p2;
    }

    if (p3.compareTo(p1) < 0 && 
        p3.compareTo(p2) < 0
        && p3.compareTo(p4) < 0) {
      // p3 is the beginning of the segment
      return p3;
    }

    if (p4.compareTo(p1) < 0 && 
        p4.compareTo(p2) < 0
        && p4.compareTo(p3) < 0) {
      // p4 is the beginning of the segment
      return p4;
    }
    
    return p1;
  }
  
  private Point getHigherPoint(Point p1, Point p2, Point p3, Point p4) {
    if (p1.compareTo(p2) > 0 && 
        p1.compareTo(p3) > 0
        && p1.compareTo(p4) > 0) {
      // p1 is the end of the segment
      return p1;
    }

    if (p2.compareTo(p1) > 0 && 
        p2.compareTo(p3) > 0
        && p2.compareTo(p4) > 0) {
      // p2 is the end of the segment
      return  p2;
    }

    if (p3.compareTo(p1) > 0 && 
        p3.compareTo(p2) > 0
        && p3.compareTo(p4) > 0) {
      // p3 is the end of the segment
      return p3;
    }

    if (p4.compareTo(p1) > 0 && 
        p4.compareTo(p2) > 0
        && p4.compareTo(p3) > 0) {
      // p4 is the end of the segment
      return p4;
    }
    
    return p4;
  }
  
  private LineSegment[] getSegments() {
    LineSegment[] tmpSegments = new LineSegment[numberOfCollinearPointsFound / 2];
    for (int i = 0; i < numberOfCollinearPointsFound; i = i + 2) {
      tmpSegments[i / 2] = new LineSegment(
          segmentsStartAndEndPoints.get(i), segmentsStartAndEndPoints.get(i + 1));
    }
    
    return tmpSegments;
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
