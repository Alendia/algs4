import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> p = new RandomizedQueue<>();
        int times = Integer.parseInt(args[0]);
        double n = 1.0;
        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            // bonus part for limited RandomizedQueue size
            if (times == 0) {
                return;
            } else if (p.size() < times) {
                p.enqueue(value);
            } else if (StdRandom.uniform() < (times / n)) {
                p.dequeue();
                p.enqueue(value);
            }
            n++;
        }
        for (int i = 0; i < times; i++) {
            StdOut.print(p.dequeue() + "\n");
        }
    }
}
