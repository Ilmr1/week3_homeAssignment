package org.example.week3_homeassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class EmployeeManagement extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmployeeManagement.class.getResource("view.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("lang_en_EN"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Employee Management!");
        stage.setMinWidth(400);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}