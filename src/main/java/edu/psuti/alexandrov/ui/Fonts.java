package edu.psuti.alexandrov.ui;

public enum Fonts {

    EXO_2_LIGHT("/fonts/exo2/Exo2-Light.ttf")
    ;

    private final String filePath;

    Fonts(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
