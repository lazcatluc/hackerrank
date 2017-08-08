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
            in.close();
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
        assertThat(withInput("input")).isNotNull();
    }
    
    @Test
    public void buildsSolutionWithInputFile() throws Exception {
        assertThat(withInputFile()).isNotNull();
    }

    public AbstractCharSequenceAssert<?, String> assertOutput() {
        return assertThat(new String(out.toByteArray(), Charset.forName("UTF-8")));
    }
    
}
