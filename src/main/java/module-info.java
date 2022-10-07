module dev.egonr.jfxtemplate {
    requires flogger;
    requires flogger.system.backend;
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.logging;
    requires kotlinx.coroutines.core.jvm;

    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.controls;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.materialicons;

    opens dev.egonr.jfxtemplate.view to javafx.fxml, javafx.graphics;
    opens dev.egonr.jfxtemplate.logging to java.logging;

    exports dev.egonr.jfxtemplate.main;
}