
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class Solution {

    private final Scanner in;
    private final PrintStream out;
    private final String[] args;

    static class Interval implements Comparable<Interval> {

        private final int start;
        private final int end;
        private final long weight;

        public Interval(int start, int end, long weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        @Override
        public int compareTo(Interval o) {
            int compare = Integer.compare(start, o.start);
            if (compare != 0) {
                return 0;
            }
            compare = Integer.compare(end, o.end);
            if (compare != 0) {
                return 0;
            }
            return Long.compare(weight, o.weight);
        }

        public boolean contains(int point) {
            return point >= start && point <= end;
        }

    }

    public Solution(InputStream in, OutputStream out, String[] args) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
        this.args = args;
    }

    public static void main(String[] args) {
        new Solution(System.in, System.out, args).solve();
    }

    public void solve() {
        int n = in.nextInt();
        int m = in.nextInt();
        Interval[] intervals = new Interval[m];
        IntStream.range(0, m).forEach(i -> intervals[i] = new Interval(in.nextInt(), in.nextInt(), in.nextLong()));
        Map<Integer, Long> values = new TreeMap<>();
        Arrays.stream(intervals).forEach(interval -> {
            values.compute(interval.start, (i, previous) -> (previous == null ? 0 : previous) + interval.weight);
            values.compute(interval.end + 1, (i, previous) -> (previous == null ? 0 : previous) - interval.weight);
        });
        long currentMax = 0;
        long current = 0;
        for (Long max : values.values()) {
            current += max;
            if (current > currentMax) {
                currentMax = current;
            }
        }
        out.print(currentMax);
    }
}
