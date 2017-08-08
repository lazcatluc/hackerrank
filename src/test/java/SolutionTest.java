import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public void rotatesLeftOnce() throws Exception {
        Solution.Rotator rotator = new Solution.Rotator(1, 2, 3, 4, 5);
        
        rotator.rotateLeft();
        
        assertThat(rotator.getRotated()).isEqualTo(Arrays.asList(2, 3, 4, 5, 1));
    }
    
    @Test
    public void solvesInput() throws Exception {
        withInputFile().solve();
        
        assertOutput().isEqualTo("5 1 2 3 4");
    }
    
    @Test(timeout = 1000)
    public void solvesBigInputInTimelyFashion() throws Exception {
        int n = 10000;
        
        StringBuilder sb = new StringBuilder().append(n).append(" ").append(n).append("\n");
        IntStream.range(0, n).forEach(i -> sb.append(i).append(" "));
        
        withInput(sb.toString()).solve();
    }
}
