import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public void buildsSolutionWithInput() throws Exception {
        assertThat(withInput("1 1")).isNotNull();
    }
    
    @Test
    public void buildsSolutionWithInputFile() throws Exception {
        assertThat(withInputFile()).isNotNull();
    }

    public AbstractCharSequenceAssert<?, String> assertOutput() {
        return assertThat(new String(out.toByteArray(), Charset.forName("UTF-8")));
    }
    
    @Test
    public void appendQueryAppendsToSequence() throws Exception {
        Solution solution = withInput("1 1");
        
        Solution.AppendQuery appendQuery = solution.new AppendQuery();
        appendQuery.accept(0, 0);
        
        assertThat(solution.getSequence(0).get(0)).isEqualTo(0);
    }
    
    @Test
    public void assignQueryChangesLastAnswer() throws Exception {
        Solution solution = withInput("1 1");
        
        solution.new AppendQuery().accept(0, 1);
        Solution.AssignQuery assignQuery = solution.new AssignQuery();
        assignQuery.accept(0, 0);
        
        assertOutput().isEqualTo("1");
    }
    
    @Test
    public void xorTest() throws Exception {
        assertThat( (1 ^ 0) % 2 ).isEqualTo(1);
    }
    
    @Test
    public void solvesSampleInput() throws Exception {
        Solution solution = withInputFile();
        
        solution.solve();
        
        assertThat(solution.getSequence(0).get(0)).isEqualTo(5);
        assertThat(solution.getSequence(1).get(0)).isEqualTo(7);
        assertThat(solution.getSequence(0).get(1)).isEqualTo(3);
        assertOutput().matches("7\\r?\\n3");
    }
}
