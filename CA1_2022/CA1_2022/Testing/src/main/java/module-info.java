module com.example.testing {
    requires javafx.fxml;
    requires org.testng;


    opens com.example.testing to javafx.fxml;
    exports com.example.testing;
}