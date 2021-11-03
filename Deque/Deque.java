package Deque;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node head, tail;

    
    public Deque() {
        size = 0;
        head = null;
        tail = null;
    }

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null ;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node oldhead = head;
        head = new Node();
        head.item = item;
        head.previous= null;
        if (oldhead == null) {
            head.next = null;
        }
        else {
            head.next = oldhead;
            oldhead.previous= head;
        }
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node oldlast = tail;
        tail = new Node();
        if (oldlast == null) {
            tail.previous = null;
        }
        else {
            tail.previous = oldlast;
            oldlast.next = tail;
        }
        tail.next = null;
        tail.item = item;
        if (head == null) {
            head = tail;
        }
        size++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = head.item;
        head = head.next;
        if(head != null)
        head.previous = null;
        if (head == null)
            tail = null;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = tail.item;
        tail = tail.previous;
        if (tail != null)
        tail.next = null;
        if (tail == null)
            head = null;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        Deque<Integer> deque = new Deque<Integer>();
        StdOut.println(deque.isEmpty());

        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(0);

        StdOut.println(deque.size());


        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());


        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(0);


        Iterator<Integer> it = deque.iterator();

        StdOut.println(deque.size());

        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());


    }

}