import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items = (Item[]) new Object[1];
    private int head = 0;
    private int tail = 0;

    public boolean isEmpty() {
        return tail - head == 0;
    }

    public int size() {
        return tail - head;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (tail == items.length && tail - head == items.length) {
            resize(2 * items.length);
        } else if (tail == items.length && tail - head < items.length / 4) {
            resize(items.length / 2);
        } else if (tail == items.length && tail - head > items.length / 4) {
            resize(items.length);
        }
        // N first, then increment (+1)
        items[tail++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int random = StdRandom.uniform(head, tail);
        Item item = items[random];
        if (random == tail - 1) {
            items[random] = null;
        } else {
            items[random] = items[tail - 1];
            items[tail - 1] = null;
        }
        tail--;
        if (tail - head < items.length / 4) resize(items.length / 2);
        return item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        if (tail - head >= 0) System.arraycopy(items, head, copy, 0, tail - head);
        items = copy;
        tail = tail - head;
        head = 0;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int random = StdRandom.uniform(head, tail);
        return items[random];
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int size = tail - head;
        private final int[] range = initRange(size);

        private int[] initRange(int rangeSize) {
            int[] rangeArray = new int[rangeSize];
            for (int i = 0; i < rangeSize; i++) {
                rangeArray[i] = i;
            }
            return rangeArray;
        }

        private int noRepeatNum() {
            int randomIndex = StdRandom.uniform(size);
            int pickedNum = range[randomIndex];
            int lastNum = range[size - 1];
            range[randomIndex] = lastNum;
            size--;
            return pickedNum;
        }

        public boolean hasNext() {
            return size > 0;
        }

        public Item next() {
            if (size <= 0) throw new NoSuchElementException();
            int noRepeatRandom = noRepeatNum();
            return items[head + noRepeatRandom];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> x = new RandomizedQueue<>();
        x.enqueue("1");
        x.enqueue("3");
        x.enqueue("4");
        x.enqueue("4");
        StdOut.print(x.sample() + "\n");
        x.dequeue();
        for (String s: x) {
            StdOut.print(s);
        }
        x.dequeue();
        x.dequeue();
        StdOut.print("\n");
        StdOut.print(x.size());
        x.dequeue();
    }
}
