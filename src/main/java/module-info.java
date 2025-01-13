module com.example.frontend {
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.net.http;
    requires org.json;
    requires de.jensd.fx.glyphs.fontawesome;

    exports com.example.frontend;
    exports com.example.frontend.Views;
    exports com.example.frontend.Controller;
    exports com.example.frontend.Models;

    opens com.example.frontend to javafx.fxml;
    opens com.example.frontend.Controller to javafx.fxml;
    opens com.example.frontend.Views to javafx.fxml;

}