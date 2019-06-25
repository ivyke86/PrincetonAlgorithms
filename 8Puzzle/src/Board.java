import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	private char[][] tiles ;
	private int n ;
	
	public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
	{
		n = blocks.length ;
		tiles =  new char[n][n] ;
		int r = 0 ;
		for( int[] row : blocks ) {
			int c = 0 ;
			for( int block : row ) {
				tiles[r][c] = intTochar(block) ;
				c++ ;
			}
			r++ ;
		}
	}
	
	public int dimension()                 // board dimension n
	{
		return n;
	}
	
	public int hamming()                   // number of blocks out of place
	{
		int ham = 0;
		int goal = 1 ;
		for( int r = 0 ; r < n ; r++ ) {
			for( int c = 0 ; c < n ; c++ ) {
				if( goal == n*n ) break;
				if( charToint( tiles[r][c] ) != goal ) ham++;
				goal++;
			}
		}
		return ham;
	}
	
	public int manhattan()                 // sum of Manhattan distances between blocks and goal
	{
		int man = 0 ;
		int goal = 1 ;
		for( int r = 0 ; r < n ; r++ ) {
			for( int c = 0 ; c < n ; c++ ) {
				int tile = charToint( tiles[r][c] );
				if( tile != 0 && tile != goal ) {
					//StdOut.println( tiles[r][c]+"-"+tile + " - " + Math.abs((tile-1)/n - (goal-1)/n) + " - " + Math.abs((tile-1)%n - (goal-1)%n));
					man = man + Math.abs((tile-1)/n - (goal-1)/n) + Math.abs((tile-1)%n - (goal-1)%n);
				}
				goal++;
			}
		}
		return man;
	}
	
	public boolean isGoal()                // is this board the goal board?
	{
		return hamming() == 0 ;
	}
	
	public Board twin()                    // a board that is obtained by exchanging any pair of blocks
	{
		int [][] tc = new int[n][n];
		for( int r = 0 ; r < n ; r++ ) {
			for( int c = 0 ; c < n ; c++ ) {
				tc[r][c] = charToint( tiles[r][c] ) ;
			}
		}
		
		int fc, sc;
		fc = sc = 0 ;
		for( int c = 0 ; c < n ; c++ ) {
			if( tc[0][c] != 0 )
			{
				fc = c ;
				if( tc[0][c+1] != 0 ) {
					sc = c+1;
				}else {
					sc = c+2;
				}
				break;
			}
		}
		
		int buf =  tc[0][fc];
		tc[0][fc] = tc[0][sc] ;
		tc[0][sc] = buf ;
		
		return new Board( tc ) ;
	}
	
	public boolean equals(Object y)        // does this board equal y?
	{
		if(y.getClass() != this.getClass()) return false;
		Board that = (Board) y;
		return toString().equals(that.toString()) ;
	}
	
	public Iterable<Board> neighbors()     // all neighboring boards
	{
		Stack<Board> st = new Stack<Board>() ;
		neighbor( st );
		return st;
	}
	
	private void neighbor( Stack<Board> st )
	{
		int zr = 0;
		int zc = 0;
		int [][] it = new int[n][n];
		for( int r = 0 ; r < n ; r++ ) {
			for( int c = 0 ; c < n ; c++ ) {
				it[r][c] =  charToint(tiles[r][c]);
				if( tiles[r][c] == '0' ) {
					zr = r;
					zc = c;
				}
			}
		}
		
		int [][] around = {{zr-1, zc}, {zr+1, zc}, {zr, zc-1}, {zr, zc+1}};
		
		for( int []nt : around ) {
			int r = nt[0] ;
			int c = nt[1] ;
			if( r < n && c < n && r >= 0 && c >= 0 ) {				
				int temp = it[r][c] ;
				it[r][c] = it[zr][zc];
				it[zr][zc] = temp ;
				Board nB = new Board(it);
				st.push(nB);
				it[zr][zc] = it[r][c];
				it[r][c] = temp ;
			}
		}
	}
	
	private int charToint( char c )
	{
		return c - '0';
	}
	
	private char intTochar( int i )
	{
		return (char)( '0' + i );
	}
	
	public String toString()               // string representation of this board (in the output format specified below)
	{
		StringBuilder s = new StringBuilder() ;
		s.append( n + "\n" ) ;
		for( int r = 0 ; r < n ; r++ ) {
			for( int c = 0 ; c < n ; c++ ) {
				s.append( String.format("%2d ", charToint( tiles[r][c] ))) ;
			}
			s.append("\n");
		}
		return s.toString();
	}
	
	public static void main(String[] args) // unit tests (not graded)
	{
		int[][] a = {{0,1,3},{4,2,5},{7,8,6}} ;
		Board b = new Board(a) ;
		
		StdOut.println( b.hamming() );
		StdOut.println( b.manhattan());
		StdOut.println(b);
//		for( Board n : b.neighbors() ) {
//			StdOut.println("---");
//			for( Board m : n.neighbors() ) {
//				StdOut.println(m);
//			}
//		}
	}
}
