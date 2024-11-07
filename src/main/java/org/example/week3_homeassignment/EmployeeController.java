package org.example.week3_homeassignment;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.ResourceBundle;

public class EmployeeController {
    private Dotenv dotenv = Dotenv.load();
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    Button saveButton;
    @FXML
    private void initialize() {
        addLanguageChangeListener();
        addSaveButtonListener();
    }

    private void addLanguageChangeListener() {
        languageComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("English")) {
                setEn(null);
            } else if (newValue.equals("Farsi")) {
                setIR(null);
            } else if (newValue.equals("Japanese")) {
                setJP(null);
            }
        });
    }


    private void addSaveButtonListener() {
        saveButton.setOnAction(event -> {
            String selectedLanguage = languageComboBox.getValue();
            if (selectedLanguage == null || selectedLanguage.isEmpty()) {
                System.err.println("No language selected. Cannot determine table name.");
                // Optionally, show an alert to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a language.");
                alert.showAndWait();
                return;
            }

            String tableName = "";
            if (selectedLanguage.equals("English")) {
                tableName = "employee_en";
            } else if (selectedLanguage.equals("Farsi")) {
                tableName = "employee_fa";
            } else if (selectedLanguage.equals("Japanese")) {
                tableName = "employee_ja";
            }
            updateEmployeeTable(tableName);
        });
    }



    private void updateEmployeeTable(String tableName) {
        if (tableName == null || tableName.isEmpty()) {
            System.err.println("Table name is null or empty. Cannot insert data.");
            return;
        }

        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USERNAME");
        String password = dotenv.get("DB_PASSWORD");

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO " + tableName + " (first_name, last_name, email) VALUES (?, ?, ?)";
            System.out.println("Executing SQL: " + sql); // Debug statement

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, firstname.getText());
                statement.setString(2, lastname.getText());
                statement.setString(3, email.getText());
                int rowsInserted = statement.executeUpdate();
                System.out.println("Rows inserted: " + rowsInserted); // Debug statement
            } catch (Exception e) {
                System.err.println("Error executing SQL statement: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setEn(ActionEvent actionEvent) {
        Locale l = new Locale("en", "EN");
        loadView(l);
    }

    public void setIR(ActionEvent actionEvent) {
        Locale l = new Locale("fa", "IR");
        loadView(l);
    }

    public void setJP(ActionEvent actionEvent) {
        Locale l = new Locale("ja", "JP");
        loadView(l);
    }

    public void loadView(Locale locale) {
        FXMLLoader fxmlLoader = new FXMLLoader(EmployeeController.class.getResource("view.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("lang", locale));
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) languageComboBox.getScene().getWindow();
            stage.setScene(new Scene(root, 400, 300));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}