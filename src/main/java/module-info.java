module edu.psuti.alexandrov {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires eu.hansolo.tilesfx;

    requires ai.djl.api;
    requires ai.djl.mxnet_engine;
    requires com.opencsv;
    requires org.slf4j;

    opens edu.psuti.alexandrov to javafx.fxml;
    exports edu.psuti.alexandrov;
}