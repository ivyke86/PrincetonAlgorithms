import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	public static void main(String[] args)        // test client 
	{
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> R = new RandomizedQueue<String>() ;
		while( !StdIn.isEmpty() ) {
			R.enqueue( StdIn.readString()) ;
		}
		
		while(k > 0) {
			StdOut.println( R.dequeue() ) ;
			k-- ;
		}
	}
}
