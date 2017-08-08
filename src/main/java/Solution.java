

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Solution {
    
    private final Scanner in;
    private final PrintStream out;
    private final String[] args;
    
    public Solution(InputStream in, OutputStream out, String[] args) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
        this.args = args;
    }

    public static void main(String[] args) {
        new Solution(System.in, System.out, args).solve();
    }

    public void solve() {
        // TODO Auto-generated method stub
        
    }
}
