import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Node first = new Node() ;
	private Node last = new Node() ;
	private int n = 0 ;
	private class Node 
	{
		Item item ;
		Node prev ;
		Node next ;
	}
	public RandomizedQueue()                 // construct an empty randomized queue
	{
		first.next = last ;
		last.prev = first ;
	}
	
	public boolean isEmpty()                 // is the randomized queue empty?
	{
		return n == 0 ;
	}
	
	public int size()                        // return the number of items on the randomized queue
	{
		return n ;
	}
	
	public void enqueue(Item item)           // add the item
	{
		if( item == null ) { throw new IllegalArgumentException() ; }
		Node node = new Node() ;
		node.item = item ;
		node.next = first.next ;
		node.next.prev = node ;
		node.prev = first ;
		first.next = node ;
		n++ ;
	}
	
	public Item dequeue()                    // remove and return a random item
	{
		int ran = StdRandom.uniform( n ) ;
		Node node = first.next ;
		int i = 0 ;
		while( node != last ) {
			if( i == ran ) {
				break ;
			}
			node = node.next ;
			i++ ;
		}
		node.prev.next = node.next ;
		node.next.prev = node.prev ;
		Item ret = node.item ;
		node = null ;
		n-- ;
		return ret ;
	}
	
	public Item sample()                     // return a random item (but do not remove it)
	{
		int ran = StdRandom.uniform( n ) ;
		Node node = first.next ;
		int i = 0 ;
		while( node != last ) {
			if( i == ran ) {
				break ;
			}
			node = node.next ;
			i++ ;
		}
		return node.item ;
	}
	
	public Iterator<Item> iterator()         // return an independent iterator over items in random order
	{
		return new RandomizedQueueIterator() ;
	}
	
	private class RandomizedQueueIterator implements Iterator<Item>
	{
	   private int count = n ;
	   private boolean[] arr = new boolean[count] ;
	   public RandomizedQueueIterator () {
		   for( int i = 0 ; i < arr.length ; i++ ) {
			   arr[i] = true ;
		   }
	   }
	   public boolean hasNext() { return count != 0 ; }
	   public void remove() { throw new UnsupportedOperationException() ; }
	   public Item next() {
		   if (!hasNext()) throw new NoSuchElementException();
		   int ran = StdRandom.uniform( count ) ;
		   Node node = first.next ;
		   int i = 0 ;
		   while( !arr[i] ) {
			   node = node.next ;
			   i++ ;
		   }
		   
		   while( ran > 0 ) {
			   if( arr[i+1] ) {
				   ran-- ;
			   }
			   i++ ;
			   node = node.next ;
		   }
		   arr[i] = false ;
		  
		   count-- ;
		   return node.item ;
	   }
	}
	
	public static void main(String[] args)   // unit testing (optional)
	{
		RandomizedQueue<String> R = new RandomizedQueue<String>() ;
		R.enqueue("1");
		R.enqueue("2");
		R.enqueue("3");
		R.enqueue("4");
		R.enqueue("5");
		R.enqueue("6");
		R.enqueue("7");
		R.enqueue("8");
		R.enqueue("0");
		StdOut.println( R.dequeue() ) ;
		StdOut.println( R.dequeue() ) ;
		StdOut.println( R.dequeue() ) ;
		for( String s : R ) {
			StdOut.println("("+s+")");
		}
	}
}