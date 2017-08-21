import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.assertj.core.api.AbstractCharSequenceAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SolutionTest {
    private ByteArrayOutputStream out;
    private InputStream in;
    
    @Before
    public void setUp() {
        out = new ByteArrayOutputStream();
    }
    
    @After
    public void tearDown() throws IOException {
        try {
            out.close();
        }
        finally {
            if (in != null) {
                in.close();
            }
        }
    }
    
    private Solution withInput(String input) {
        in = new ByteArrayInputStream(input.getBytes(Charset.forName("UTF-8")));
        return new Solution(in, out, new String[]{});
    }
    
    private Solution withInputFile() {
        in = Solution.class.getResourceAsStream("input.txt");
        return new Solution(in, out, new String[]{});
    }
    
    @Test
    public void buildsSolutionWithInputFile() throws Exception {
        assertThat(withInputFile()).isNotNull();
    }

    public AbstractCharSequenceAssert<?, String> assertOutput() {
        return assertThat(new String(out.toByteArray(), Charset.forName("UTF-8")));
    }
    
    @Test
    public void readsTree() throws Exception {
        Solution solution = withInput("3 2 3 -1 -1 -1 -1");
        solution.printTree(solution.readTree());
        
        assertOutput().isEqualToIgnoringWhitespace("2 1 3");
    }
    
    @Test
    public void readsTreeFromFile() throws Exception {
        Solution solution = withInputFile();
        solution.printTree(solution.readTree());
        
        assertOutput().isEqualToIgnoringWhitespace("6 9 4 2 1 7 5 10 8 11 3");
    }
    
    @Test
    public void readsTreeFromFileThenSwaps2() throws Exception {
        Solution solution = withInputFile();
        solution.printTree(solution.swap(solution.readTree(), 2, 2));
        
        assertOutput().isEqualToIgnoringWhitespace("2 9 6 4 1 3 7 5 11 8 10");
    }
    
    @Test(timeout = 1000)
    public void solvesInputOutput() throws Exception {
        withInputFile().solve();
        
        assertOutput().isEqualToIgnoringWhitespace(readOutput());
    }
    
    @Test(timeout = 1000)
    public void solvesBigInputLongTree() throws Exception {
        int n = 1024;
        int t = 100;
        StringBuilder input = new StringBuilder().append(n).append(" ");
        IntStream.range(2, n + 1).forEach(i -> input.append(i).append(" ").append(" -1 "));
        input.append("-1 -1 ").append(t).append(" ");
        Arrays.asList(6,145,605,364,725,810,94,603,28,724,598,800,970,365,483,529,144,662,205,310,547,969,930,908,645,772,638,811,710,164,322,574,198,641,632,392,434,931,740,854,883,268,942,752,856,90,401,959,253,311,767,603,592,707,310,65,806,928,888,563,880,706,245,339,584,400,886,183,418,402,284,956,702,364,212,945,725,42,345,79,765,178,909,37,133,712,691,1000,499,510,516,733,352,265,702,392,356,739,1009,788)
            .forEach(i -> input.append(i).append(" "));
        
        withInput(input.toString()).solve();
    }
    
    @Test(timeout = 1000)
    public void solvesBigInputBalancedTree() throws Exception {
        int n = 1024;
        int t = 100;
        int k = 2;
        StringBuilder input = new StringBuilder().append(n).append(" ");
        IntStream.range(0, n/2 - 1).map(i -> 2 * i + 1).forEach(i -> input.append(i+1).append(" ").append(i+2).append(" "));
        IntStream.range(0, n/2 - 1).forEach(i -> input.append("-1 -1 "));
        input.append("-1 -1 ").append(t).append(" ");
        IntStream.range(0, t).forEach(i -> input.append(k).append(" "));
        
        withInput(input.toString()).solve();
    }

    private String readOutput() throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Solution.class.getResourceAsStream("output.txt"), Charset.forName("UTF-8")))) {
            return bufferedReader.lines().reduce(new StringBuilder(), 
                    (sb, s) -> sb.append(s).append("\n"), 
                    (sb1, sb2) -> sb1.append(sb2)).toString();
        }
    }
}
