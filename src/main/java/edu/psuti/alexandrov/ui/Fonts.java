package edu.psuti.alexandrov.ui;

public enum Fonts {

    EXO_2_LIGHT("/fonts/exo2/Exo2-Light.ttf"),
    EXO_2_SEMIBOLD("/fonts/exo2/Exo2-SemiBold.ttf"),
    EXO_2_BOLD("/fonts/exo2/Exo2-Bold.ttf")
    ;

    private final String filePath;

    Fonts(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
