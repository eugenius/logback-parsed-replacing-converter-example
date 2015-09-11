import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author e.beschastnov
 */
public class SimpleTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testSimple() throws Exception {
        logger.error("", new RuntimeException());
    }
}