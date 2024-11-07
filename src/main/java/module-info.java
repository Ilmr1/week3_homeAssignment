module org.example.week3_homeassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires dotenv.java;


    opens org.example.week3_homeassignment to javafx.fxml;
    exports org.example.week3_homeassignment;
}