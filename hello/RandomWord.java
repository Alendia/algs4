import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 1;
        String winner = "";
        while(!StdIn.isEmpty()) {
            String value = StdIn.readString();
            if (StdRandom.bernoulli((double) 1 / i)) {
                winner = value;
            }
            i++;
        }
        StdOut.println(winner);
    }
}