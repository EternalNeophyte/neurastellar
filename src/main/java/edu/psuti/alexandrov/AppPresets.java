package edu.psuti.alexandrov;

import edu.psuti.alexandrov.ui.Fonts;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;

public final class AppPresets {

    private static final Class<NeurastellarApp> APP_CLASS = NeurastellarApp.class;

    private AppPresets() {
        throw new AssertionError("Not supposed to be instantiated");
    }

    public static void loadLogger() {
        String path = APP_CLASS
                .getClassLoader()
                .getResource("logger.properties")
                .getFile();
        System.setProperty("java.util.logging.config.file", path);
    }

    public static Parent fxml() {
        try {
            URL source = APP_CLASS.getResource("/ui.fxml");
            return FXMLLoader.load(source);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Font font(Fonts font, int size) {
        String source = APP_CLASS
                .getResource(font.getFilePath()).toExternalForm();
        return Font.font(source, size);
    }
}
