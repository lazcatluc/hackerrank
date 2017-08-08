
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {

    private final Scanner in;
    private final PrintStream out;
    private final String[] args;
    private final int[][] matrix = new int[6][6];

    public Solution(InputStream in, OutputStream out, String[] args) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
        this.args = args;
        IntStream.range(0, 6).forEach(i -> IntStream.range(0, 6).forEach(j -> matrix[i][j] = this.in.nextInt()));
    }

    public static void main(String[] args) {
        new Solution(System.in, System.out, args).solve();
    }

    public void solve() {
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                int value = hourglassSum(i, j);
                if (value > max) {
                    max = value;
                }
            }
        }
        out.print(max);
    }

    public int hourglassSum(int i, int j) {
        return matrix[i - 1][j - 1] + matrix[i - 1][j] + matrix[i - 1][j + 1] + matrix[i][j] + matrix[i + 1][j - 1]
                + matrix[i + 1][j] + matrix[i + 1][j + 1];
    }
}
