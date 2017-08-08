import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
    public void solvesInputOutput() throws Exception {
        withInputFile().solve();
        
        assertOutput().isEqualToIgnoringWhitespace(readOutput());
    }
    
    @Test(timeout = 5000)
    public void solvesHugeInputInTimelyFashion() throws Exception {
        int n = 10000000;
        int m = 200000;
        long k = 1_000_000_000;
        
        StringBuilder inputBuilder = new StringBuilder().append(n).append(" ").append(m).append("\n");
        IntStream.range(0, m).forEach(i -> inputBuilder.append(1).append(" ").append(n).append(" ").append(k).append("\n"));
        withInput(inputBuilder.toString()).solve();
        
        assertOutput().isEqualTo("200000000000000");
    }

    private String readOutput() throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Solution.class.getResourceAsStream("output.txt"), Charset.forName("UTF-8")))) {
            return bufferedReader.lines().reduce(new StringBuilder(), 
                    (sb, s) -> sb.append(s).append("\n"), 
                    (sb1, sb2) -> sb1.append(sb2)).toString();
        }
    }
}
