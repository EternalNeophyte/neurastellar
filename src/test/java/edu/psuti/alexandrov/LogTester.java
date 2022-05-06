package edu.psuti.alexandrov;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class LogTester {

    private static final Logger LOG;

    static {
        LoggerSource.load();
        LOG = LoggerFactory.getLogger(LogTester.class);
    }

    @Test
    public void runLogger() {
        assertDoesNotThrow(() -> LOG.info("Ok"));
    }
}
