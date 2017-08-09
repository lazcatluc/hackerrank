import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

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
    
    @Test
    public void solvesInputOutput() throws Exception {
        withInputFile().solve();
        
        assertOutput().isEqualToIgnoringWhitespace(readOutput());
    }

    private String readOutput() throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Solution.class.getResourceAsStream("output.txt"), Charset.forName("UTF-8")))) {
            return bufferedReader.lines().reduce(new StringBuilder(), 
                    (sb, s) -> sb.append(s).append("\n"), 
                    (sb1, sb2) -> sb1.append(sb2)).toString();
        }
    }
}
