import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private LineSegment[] seg;
	private ArrayList<LineSegment> foundSeg = new ArrayList<>() ;
	
	public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
	{
		if( points == null ) { throw new IllegalArgumentException () ; }
		
		for( int i = 0 ; i < points.length ; i++ ) {
			if(points[i] == null) { throw new IllegalArgumentException () ; }
			for( int j = i + 1 ; j < points.length ; j++ ) {
				if( points[i].compareTo( points[j] ) == 0 ) { throw new IllegalArgumentException () ; }
			}
		}
		
		Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        	
		for( int i = 0 ; i < pointsCopy.length-3 ; i++ ) {
			for( int j = i + 1 ; j < pointsCopy.length-2 ; j++ ) {
				for( int k = j + 1 ; k < pointsCopy.length-1 ; k++ ) {
					for( int l = k + 1 ; l < pointsCopy.length ; l++ ) {
						if( pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[k]) &&
							pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[l])) {
							LineSegment nSeg = new LineSegment( pointsCopy[i], pointsCopy[l] ) ;
							this.addNewSeg(nSeg) ;
						}
					}
				}
			}
		}
		seg = foundSeg.toArray(new LineSegment[ foundSeg.size() ]) ;
	}
	
	private void addNewSeg( LineSegment nSeg ) {
		boolean isAdd = true ;
//		for( LineSegment S:foundSeg ) {
//			if( S.toString().equals(nSeg.toString())) isAdd = false ;
//		}
		if( isAdd ) foundSeg.add(nSeg) ;
	}

	public int numberOfSegments()        // the number of line segments
	{
		return this.seg.length ;
	}
	
	public LineSegment[] segments()                // the line segments
	{
		return Arrays.copyOf(seg, seg.length) ;
	}
}
