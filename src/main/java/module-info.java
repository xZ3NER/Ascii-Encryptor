module app {
    requires javafx.controls;
    requires javafx.fxml;

    exports app;
    opens app to javafx.fxml;

    /**
     * The structure of this file is:
     * 1.- module 'name of the package inside the java folder'
     * 2.- requires 'javafx.controls' (controller) & 'javafx.fxml' (view)
     * 3.- needs an 'exports' and 'opens' of the same package at the point 1.-
     * Use 'export' and 'opens' when their classes import any javafx library.
     */
}
