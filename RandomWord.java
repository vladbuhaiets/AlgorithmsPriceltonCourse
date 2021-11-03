import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champ = "";
        if (!StdIn.isEmpty())
            champ = StdIn.readString();
        Integer count = 2;
        while (!StdIn.isEmpty()) {
            String maybeNextChamp = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / (double) count)) {
                champ = maybeNextChamp;
            }
            count++;
        }
        StdOut.print(champ);
    }
}

