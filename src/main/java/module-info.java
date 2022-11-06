module net.fachpersonal.uno {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens net.fachpersonal.uno to javafx.fxml;
    exports net.fachpersonal.uno;
    exports net.fachpersonal.uno.utils;
    opens net.fachpersonal.uno.utils to javafx.fxml;
}