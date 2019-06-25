import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] result ; 
	private int trials ;
	public PercolationStats(int n, int p)    // perform trials independent experiments on an n-by-n grid
	{
		trials = p ;
		result = new double[trials] ;
		for( int i = 0 ; i < trials ; i++ ) {
			Percolation perc =  new Percolation( n ) ;
			while( !perc.percolates() ) {
				int randRow = StdRandom.uniform(n) + 1  ;
				int randCol = StdRandom.uniform(n) + 1 ;
				perc.open(randRow, randCol);
			}
			result[i] = perc.numberOfOpenSites() / (double)(n*n) ;
		}
	}
	public double mean()                          // sample mean of percolation threshold
	{
		return StdStats.mean( result ) ;
	}
	public double stddev()                        // sample standard deviation of percolation threshold
	{
		return StdStats.stddev( result ) ;
	}
	public double confidenceLo()                  // low  endpoint of 95% confidence interval
	{
		double mean = mean() ;
		double variance = Math.sqrt(stddev()) ;
		return mean - 1.96*variance/( Math.sqrt(trials) ) ;
	}
	public double confidenceHi()                  // high endpoint of 95% confidence interval
	{
		double mean = mean() ;
		double variance = Math.sqrt(stddev()) ;
		return mean + 1.96*variance/( Math.sqrt(trials) ) ;
	}

	public static void main(String[] args)        // test client 
	{
		PercolationStats pS = new PercolationStats( StdIn.readInt(), StdIn.readInt() ) ;
		StdOut.println("mean = " + pS.mean());
		StdOut.println("stddev = " + pS.stddev());
		StdOut.println("95% confidence interval = [" + pS.confidenceLo() + ", " + pS.confidenceHi() + "]");
	}
}
