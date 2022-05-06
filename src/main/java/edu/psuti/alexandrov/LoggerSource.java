package edu.psuti.alexandrov;

public final class LoggerSource {

    private LoggerSource() {
        throw new AssertionError("Not supposed to be instantiated");
    }

    public static void load() {
        String path = NeurastellarApplication.class
                .getClassLoader()
                .getResource("logger.properties")
                .getFile();
        System.setProperty("java.util.logging.config.file", path);
    }
}
