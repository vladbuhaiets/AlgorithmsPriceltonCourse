package Deque;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int i) {
        Item[] newItems = (Item[]) new Object[i];
        for (int j = 0; j < size; j++) {
            newItems[j] = items[j];
        }
        items = newItems;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (size == items.length)
            resize(2 * items.length);
        items[size] = item;
        size++;
    }


    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        int index = StdRandom.uniform(size);

        Item item = items[index];
        items[index] = null;

        if (index != (size - 1)) {
            items[index] = items[size - 1];
            items[size - 1] = null;
        }

        if (size > 0 && ( size == items.length / 4))
            resize(items.length / 2);
        size--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        int index = StdRandom.uniform(size);

        Item item = items[index];

        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {
        private int all = size;

        @Override
        public boolean hasNext() {
            return all != 0;
        }

        @Override
        public Item next() {
               if (!hasNext())
                throw new NoSuchElementException();

            int index = StdRandom.uniform(all);

            Item item = items[index];
            items[index] = null;

            if (index != (all - 1)) {
                items[index] = items[all - 1];
                items[all - 1] = null;
            }
            all--;
            return item;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();

        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(4);

        StdOut.println("------------------------------");
        StdOut.println(randomizedQueue.sample());
        StdOut.println(randomizedQueue.sample());
        StdOut.println(randomizedQueue.sample());
        StdOut.println(randomizedQueue.sample());
        StdOut.println("------------------------------");

        StdOut.println(randomizedQueue.dequeue());
        StdOut.println(randomizedQueue.dequeue());
        StdOut.println(randomizedQueue.dequeue());
        StdOut.println(randomizedQueue.dequeue());
        StdOut.println("------------------------------");
        StdOut.println(randomizedQueue.size());



    }
}