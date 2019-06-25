import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
	private Node first = new Node() ;
	private Node last = new Node() ;
	private class Node 
	{
		Item item ;
		Node prev ;
		Node next ;
	}

   public Deque()                           // construct an empty deque
   {
	   first.prev = first ;
	   first.next = last ;
	   last.prev = first ;
	   last.next = last ;
   }
 
   public boolean isEmpty()                 // is the deque empty?
   {
	   return first.next == last ;
   }
   
   public int size()                        // return the number of items on the deque
   {
	   int n = 0 ;
	   for( Node node = first.next ; node != last ; node = node.next ) {
		   n++ ;
	   }
	   return n ;
   }
   
   public void addFirst(Item item)          // add the item to the front
   {
	   if( item == null ) { throw new IllegalArgumentException() ; }
	   Node node = new Node() ;
	   node.item = item ;
	   node.next = first.next ;
	   node.next.prev = node ;
	   node.prev = first ;
	   first.next = node ;
   }
   
   public void addLast(Item item)           // add the item to the end
   {
	   if( item == null ) { throw new IllegalArgumentException() ; }
	   Node node = new Node() ;
	   node.item = item ;
	   node.prev = last.prev ;
	   node.prev.next = node ;
	   node.next = last ;
	   last.prev = node ;
   }
   
   public Item removeFirst()                // remove and return the item from the front
   {
	   Node node = first.next ;
	   if( node == last ) { throw new NoSuchElementException () ; }
	   first.next = node.next ;
	   first.next.prev = first ;
	   Item ret = node.item ;
	   node = null ;
	   return ret ;
   }
   
   public Item removeLast()                 // remove and return the item from the end
   {
	   Node node = last.prev ;
	   if( node == first ) { throw new NoSuchElementException () ; }
	   last.prev = node.prev ;
	   last.prev.next = last ;
	   Item ret = node.item ;
	   node = null ;
	   return ret ;
   }
   
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
	   return new DequeIterator() ;
   }
   
   private class DequeIterator implements Iterator<Item>
   {
	   private Node current = first.next ;
	   public boolean hasNext() { return current != last ; }
	   public void remove() { throw new UnsupportedOperationException() ; }
	   public Item next() {
		   if (!hasNext()) throw new NoSuchElementException();
		   Item item = current.item ;
		   current = current.next ;
		   return item ;
	   }
   }
   
   public static void main(String[] args)   // unit testing (optional)
   {
	   Deque<String> d = new Deque<String>() ;
	   d.addFirst("a");
	   d.addLast("c");
	   d.addFirst("b");
	   d.addFirst("1");
	   StdOut.println(d.size()) ;
	   StdOut.println(d.isEmpty()) ;
	   StdOut.println(d.removeFirst()) ;
	   StdOut.println(d.removeLast()) ;
	   StdOut.println(d.removeFirst()) ;
	   StdOut.println(d.removeFirst()) ;
	   StdOut.println(d.isEmpty()) ;
	   
	   /*
	   Iterator it = d.iterator() ;
	   StdOut.println(it.next()) ;
	   StdOut.println(it.next()) ;
	   StdOut.println(it.next()) ;
	   
	   
	   for( String s : d ) {
		   StdOut.println(s) ;
	   }
	   */
	   
   }
   
}