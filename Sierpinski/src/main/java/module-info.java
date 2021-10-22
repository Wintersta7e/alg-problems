module nkr.sierpinski.sierpinskicarpet {
    requires javafx.controls;
    requires javafx.fxml;


    opens nkr.sierpinski to javafx.fxml;
    exports nkr.sierpinski;
}