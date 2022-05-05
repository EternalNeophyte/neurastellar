import edu.psuti.alexandrov.LoggerSource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestLogger {

    private static final Logger LOG;

    static {
        LoggerSource.load();
        LOG = LoggerFactory.getLogger(TestLogger.class);
    }

    @Test
    public void runLogger() {
        assertDoesNotThrow(() -> LOG.info("Ok"));
    }
}
