import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int level ;
	private int openS = 0 ;
	private boolean[][] board ;
	private WeightedQuickUnionUF uf ;
	
	public Percolation(int n) {
        level = n ;
        if( n<=0 ) {
        	throw new IllegalArgumentException(n + " is less than 0");  
        }
        board = new boolean[n+1][n+1] ;
        uf = new WeightedQuickUnionUF(level*level + 2);
        for( int r = 1 ; r <= n ; r++ ) {
        	for( int c = 1 ; c <= n ; c++ ) {
        		board[r][c] = false ;
        	}
        }
    }
	
	public void open( int row, int col ) {
		if( row < 1 || row > level || col < 1 || col > level ) {
			throw new IllegalArgumentException( "row = "+row+" col = "+col );  
		}
		if( isOpen( row, col ) ) return ;
		board[row][col] = true ;
		openS++ ;
		int pos = (row-1)*level + col ;
		if( isValidate( row-1, col ) && isOpen( row-1, col ) ) uf.union(pos, pos - level);
		if( isValidate( row+1, col ) && isOpen( row+1, col ) ) uf.union(pos, pos + level);
		if( isValidate( row, col-1 ) && isOpen( row, col-1 ) ) uf.union(pos, pos - 1);
		if( isValidate( row, col+1 ) && isOpen( row, col+1 ) ) uf.union(pos, pos + 1);
		
		if( row == 1 ) uf.union(pos, 0) ;
		if( row == level ) uf.union(pos, level*level+1) ;
	}
	
	public boolean isOpen(int row, int col) {
		if( row < 1 || row > level || col < 1 || col > level ) {
			throw new IllegalArgumentException( "row = "+row+" col = "+col );  
		}
		if( board[row][col] == true ) return true ;
		return false ;
	}
		
	public boolean isFull(int row, int col) {
		if( row < 1 || row > level || col < 1 || col > level ) {
			throw new IllegalArgumentException( "row = "+row+" col = "+col );  
		}
		int pos = (row-1)*level + col ;
		return uf.connected(pos, 0) ;
	}
	
	public int numberOfOpenSites() {
		return openS ;
	}
	
	public boolean percolates() {
		return uf.connected(0, level*level + 1) ;
	}
	
	private boolean isValidate( int row, int col ) {
		if( row < 1 || row > level || col < 1 || col > level ) {
			return false ;  
		}
		return true ;
	}
}
