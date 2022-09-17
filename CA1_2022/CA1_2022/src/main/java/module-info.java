module com.example.ca1_2022 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.ca1_2022 to javafx.fxml;
    exports com.example.ca1_2022;
}