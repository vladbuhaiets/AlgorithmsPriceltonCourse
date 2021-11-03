package Queue;

import java.util.Stack;

public class Queue {

    private Node first, last;
    Stack<String> ops = new Stack<String>();
    Stack<Double> vals = new Stack<Double>();
    int size = 0;
    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return ops.isEmpty() && vals.isEmpty();
    }

    public void enqueue(Object item) {
        if (item instanceof String)
            ops.push(String.valueOf(item));
        if (item instanceof Double)
            vals.push(Double.parseDouble(String.valueOf(item)));
        size++;
    }

    public String dequeue() {
        if(isEmpty() && size > 3)
            return "";
        size -= 3;
        return vals.pop() + ops.pop() + vals.pop();
    }

}
