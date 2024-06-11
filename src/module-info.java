module VisualNovelFX {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;
    requires org.junit.jupiter.api;
	requires javafx.base;
    
    opens application to javafx.graphics, javafx.fxml;
}
