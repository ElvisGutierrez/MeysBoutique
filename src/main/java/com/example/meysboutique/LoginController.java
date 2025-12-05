package com.example.meysboutique;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private PasswordField contraseña;

    @FXML
    private TextField usuario;

    public LoginController() {
    }

    private final String usuarioCorrecto = "Admin";
    private final String contraseñaCorrecta = "admin123";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                usuario.setText(newValue.replaceAll("[^a-zA-Z@.\\s]", ""));
            }
        });
    }

    public void btnAcceder(ActionEvent actionEvent) throws IOException, SQLException {
        String usuarioIngresado = usuario.getText();
        String contraseñaIngresada = contraseña.getText();

        Connection connection = DatabaseUtil.getConnection();

        try {
            String sql = "SELECT * FROM tablaUsuario WHERE correo = ? AND contraseña = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usuarioIngresado);
            preparedStatement.setString(2, contraseñaIngresada);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int codigoUsuario = resultSet.getInt("codigoUsuario");

                insertarSesionUsuario(codigoUsuario);

                Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();

                mostrarMensajeExito("Inicio de sesión exitoso", "Bienvenido!");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setTitle("Inicio-Mey's Boutique");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } else {
                mostrarMensajeError("Credenciales incorrectas", "El nombre de usuario o la contraseña son incorrectos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudo verificar las credenciales.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnRegistrarse(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("registro-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Registro-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensajeExito(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void insertarSesionUsuario(int codigoUsuario) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            String insertQuery = "INSERT INTO tablaSesionUsuario (codigoUsuario, fechaInicio) VALUES (?, NOW())";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, codigoUsuario);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudo insertar la sesión del usuario.");
        }
    }
}
