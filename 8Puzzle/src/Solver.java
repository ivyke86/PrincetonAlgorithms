import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private MinPQ<Node> pq ;
	private MinPQ<Node> Tpq ;
	private Stack<Board> st ;
	private int moves = -1 ;
	private boolean isSolvable ;
	
	private class Node {
		public Node prev ;
		private int moves;
		private int ham ;
		private int man ;
		private boolean isTwin;
		public Board board ;
		public Node( Node pN, Board b, int m, boolean T ) {
			prev = pN ;
			board = b ;
			moves = m ;
			isTwin = T;
			ham = board.hamming();
			man = board.manhattan();
		}
		
		public boolean isGoal() {
			return board.isGoal();
		}
				
		public Iterable<Board> neighbors()     // all neighboring boards
		{
			return board.neighbors();
		}
		
		public int moves() {return moves;}
		public int ham() {return ham;}
		public int man() {return man;}
		public boolean isTwin() {return isTwin; }
		
		public Comparator<Node> hamOrder()
		{
			return new Comparator<Node>() {
				public int compare( Node a, Node b ) {
					int aP = a.moves() + a.ham();
					int bP = b.moves() + b.ham();
					return Integer.compare(aP, bP);
				}
			};
		}
		
		public Comparator<Node> manOrder()
		{
			return new Comparator<Node>() {
				public int compare( Node a, Node b ) {
					int aP = a.moves() + a.man();
					int bP = b.moves() + b.man();
					return Integer.compare(aP, bP);
				}
			};
		}
	}
	
	
	
	public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
	{
		Node root =  new Node( null, initial, 0, false );
		Node Troot = new Node( null, initial.twin(), 0, true );
		//pq = new MinPQ<Node>(0, root.hamOrder()) ;
		pq = new MinPQ<Node>(0, root.manOrder()) ;
		pq.insert(root);
		pq.insert(Troot);
		
		Node now ;
		while( true ) {
			now = pq.delMin();
						
			//StdOut.println( now.moves() );
			
			if( now.isGoal() ) {
				if( now.isTwin() ) {
					isSolvable = false;
					break;
				} else {
					isSolvable = true;
					moves = now.moves();
					break;
				}
			}
			
			for( Board nB : now.neighbors() ) {  
				if( now.prev!=null && nB.equals( now.prev.board ) ) continue;
				Node nNode = new Node( now, nB, now.moves()+1, now.isTwin() );
				pq.insert(nNode);
			}
		}
		
		if( isSolvable ) {
			st = new Stack<Board>() ;
			
			while( now != null ) {
				st.push(now.board);
				now = now.prev;
			}
		}
	}
	
	public boolean isSolvable()            // is the initial board solvable?
	{
		return isSolvable;
	}
	
	public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
	{
		return st;
	}
	
	public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
	{
		return moves;
	}
	
	public static void main(String[] args) // solve a slider puzzle (given below)
	{
		 // create initial board from file
	    In in = new In("C:\\Users\\park.jeounghyun\\Desktop\\8puzzle\\puzzle50.txt");
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
}
