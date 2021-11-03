package Stack;

import java.util.Iterator;

public class Stack implements Iterable<Integer> {
    private Node first = null;

    @Override
    public Iterator<Integer> iterator() {
        return new stackListIterator();
    }

    private class stackListIterator implements Iterator<Integer> {

        private Node current = first;
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Integer next() {
            Integer item = current.item;
            current = current.next;
            return item;
        }
    }

    private class Node {
        Integer item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(Integer item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    public Integer pop() {
        Integer item = first.item;
        first = first.next;
        return item;
    }

    public int getMax() {
        Iterator<Integer> it = iterator();
        Integer max = it.next();
        while (it.hasNext())
        {
            if (it.next() > max)
                max = it.next();
        }
        return max;
    }

    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(3);
        stack.push(2);
        stack.push(1);
        stack.push(4);
        for (Integer integer: stack) {
            System.out.println(integer);
        }
        System.out.println("Max: " + stack.getMax());
        stack.pop();
        for (Integer integer: stack) {
            System.out.println(integer);
        }
        System.out.println("Max: " + stack.getMax());


    }
}
