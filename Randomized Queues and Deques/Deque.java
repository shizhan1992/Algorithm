
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private int N;          // size of the queue
    private Node first;     // top of queue
    private Node last;     // top of queue
	
    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node pre;
    }
    
	// construct an empty deque
	public Deque(){
		first = null;
		last = null;
        N = 0;
	}            
	
	// is the deque empty?
	public boolean isEmpty(){
		return N == 0;
	}
	
	// return the number of items on the deque
	public int size(){
		return N;
	}
	
	// insert the item at the front
	public void addFirst(Item item){
		if(item == null)
			throw new java.lang.NullPointerException();
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		if(isEmpty()) last = first;
		else oldfirst.pre = first;
		N++;
	}
	
	// insert the item at the end
	public void addLast(Item item){
		if(item == null)
			throw new java.lang.NullPointerException();
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.pre = oldlast;
		if(isEmpty()) first = last;
		else oldlast.next = last;
		N++;
	}   
	
	// delete and return the item at the front
	public Item removeFirst(){
		if(isEmpty()) 
			throw new java.util.NoSuchElementException();
		Item item = first.item;
		first = first.next;
		N--;
		if(isEmpty()){
			last = null;
		}else{
			first.pre = null;
		}
		return item;
	}   
	
	// delete and return the item at the end
	public Item removeLast(){
		if(isEmpty()) 
			throw new java.util.NoSuchElementException();
		Item item = last.item;
		last = last.pre;
		N--;
		if(isEmpty()){
			first = null;
		}else{
			last.next = null;
		}
		return item;
	}  
	
	// return an iterator over items in order from front to end
	public Iterator<Item> iterator(){
		return new ListIterator();
	}         
	
	private class ListIterator implements Iterator<Item>{
		private Node current = first;
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if(!hasNext()) throw new java.util.NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Deque<String> deque = new Deque<String> ();
		while(!StdIn.isEmpty()) {
			String s = StdIn.readString();
			deque.addFirst(s);
		}
		StdOut.println(deque.size());
		Iterator<String> it = deque.iterator();
		while(it.hasNext()){
			StdOut.print(it.next() + " ");
		}
		
	}
}
