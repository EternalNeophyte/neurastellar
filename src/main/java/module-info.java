module edu.psuti.alexandrov {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;

    requires ai.djl.api;
    requires ai.djl.mxnet_engine;
    requires com.opencsv;
    requires org.slf4j;

    opens edu.psuti.alexandrov to javafx.fxml, ai.djl.api, ai.djl.mxnet_engine;
    opens edu.psuti.alexandrov.ui to javafx.fxml, ai.djl.api, ai.djl.mxnet_engine;
    opens edu.psuti.alexandrov.stellar to com.opencsv, ai.djl.api, ai.djl.mxnet_engine;
    exports edu.psuti.alexandrov.stellar;
    exports edu.psuti.alexandrov;
    exports edu.psuti.alexandrov.ui;
}