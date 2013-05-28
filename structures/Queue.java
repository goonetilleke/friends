package Friend;

import java.util.NoSuchElementException;

public class Queue<T> {
//	public Node rear;
	public int size;
	private Node<T> rear;
	
	public Queue(){
		rear = null; 
		size=0;
	}
	public void enqueue(T s){
		Node<T> temp = new Node<T>(s, null); 
		if (size == 0){
			temp.next=temp;
		} else{
			temp.next=rear.next;
			rear.next=temp;
		}
		rear=temp;
		size++;
		
	}
	public T dequeue()
	throws NoSuchElementException{
		if (size==0){
			throw new NoSuchElementException();
		}
		T data=rear.next.data;
		if (rear==rear.next){
			rear=null;
		} else{
			rear.next=rear.next.next;
		}
		size--;
		return data;
	}
	public int size(){
		return size;
		
	}
	public boolean isEmpty(){
		if (size==0){
			return true;
		}
		else{
			return false;
		}
	}
	public void clear(){
		size=0;
		rear=null;
	}
}
