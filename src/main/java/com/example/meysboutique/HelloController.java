package com.example.meysboutique;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private ProgressIndicator ProgressIndicator;

    public HelloController() {
    }

    public void initialize() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50); 
                    updateProgress(i, 100); 
                }
                return null;
            }
        };

        ProgressIndicator.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(event -> {
            try {
                conectarDB();
                if (usuarioEstaLogueado()) {
                    openInicioWindow();
                } else {
                    openLoginWindow();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        new Thread(task).start();
    }

    private void openLoginWindow() throws IOException {
        Stage currentStage = (Stage) ProgressIndicator.getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Inicio's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void openInicioWindow() throws IOException {
        Stage currentStage = (Stage) ProgressIndicator.getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Inicio's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void conectarDB () {
        try {
            Connection connection = DatabaseUtil.getConnection();
            if (connection != null) {
                mostrarAlerta("Conexión Exitosa", "La conexión a la base de datos se ha establecido correctamente.");
                connection.close();
            } else {
                mostrarAlerta("Error de Conexión", "No se pudo establecer la conexión a la base de datos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de SQL", "Ocurrió un error al ejecutar la consulta SQL: " + ex.getMessage());
        }
    }

    private boolean usuarioEstaLogueado() {
        try {
            Connection connection = DatabaseUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT COUNT(*) AS count FROM tablaSesionUsuario";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de SQL", "Ocurrió un error al ejecutar la consulta SQL: " + ex.getMessage());
        }
        return false;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}