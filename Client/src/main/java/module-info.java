module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires Server;

    opens com.gui to javafx.fxml;
    exports com.gui;
    exports com.gui.controller.user;
    opens com.gui.controller.user to javafx.fxml;
    exports com.gui.controller.admin;
    opens com.gui.controller.admin to javafx.fxml;
    exports com.gui.controller.general;
    opens com.gui.controller.general to javafx.fxml;
}