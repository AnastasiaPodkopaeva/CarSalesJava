module com.gui.demo {
    requires javafx.controls;
    requires javafx.fxml;
            
                        
    opens com.gui.demo to javafx.fxml;
    exports com.gui.demo;
}