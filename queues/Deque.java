import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;
    // I triggered one bonus test when I first used Singly linked list
    // This test asks you to implement Deque with most 40N + 40 memory
    // If you use Doubly linked list as I coded below,
    // the memory will be 48N + 40 which apparently larger than the requirement
    // If you use Singly linked list, the memory is 40N + 32, but addLast() method
    // requires more time which cannot pass the test, so I have no idea about that
    // I didn't find anyone's code use Singly linked list to implement Deque
    // If someone know how to implement it, please email me
    // more information about my submit triggered this bonus test:
    // https://shorturl.at/ghxW1
    // open it and search "Uses at most 40n + 40 bytes of memory", now you can see it
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (oldFirst != null) {
            oldFirst.prev = first;
        }
        size++;
        if (size() == 1) {
            last = first;
        }
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (oldLast == null) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        }
        size--;
        if (isEmpty()) {
            last = null;
        }
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        if (first == last) {
            first = null;
            last = null;
        } else {
            last = last.prev;
        }
        if (last != null) {
            last.next = null;
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> x = new Deque<>();
        x.addFirst(1);
        x.addLast(2);
        x.addLast(5);
        StdOut.print(x.size() + "\n");
        for (Integer i: x) {
            StdOut.print(i);
        }
        x.removeFirst();
        x.removeLast();
        x.removeLast();
    }
}
