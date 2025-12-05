package com.example.meysboutique;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

public class ResgistroController {

    public ResgistroController() {
    }

    @FXML
    private TextField primerApellidoUsuario;

    @FXML
    private TextField confirmarUsuario;

    @FXML
    private TextField contraseñaUsuario;

    @FXML
    private TextField correoUsuario;

    @FXML
    private DatePicker fechaUsuario;

    @FXML
    private TextField primerNombreUsuario;

    @FXML
    private TextField segundoApellidoUsuario;

    @FXML
    private TextField segundoNombreUsuario;

    @FXML
    private TextField telefonoUsuario;

    @FXML
    private TextField tercerNombreUsuario;

    @FXML
    private TextField apellidoConyugalUsuario;

    @FXML
    private ComboBox<String> direccionUsuario;

    public void initialize() {
        fechaUsuario.setValue(LocalDate.of(2002, 1, 1));

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT nombreMunicipio FROM tablaMunicipio";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> municipios = FXCollections.observableArrayList();

            while (resultSet.next()) {
                municipios.add(resultSet.getString("nombreMunicipio"));
            }

            direccionUsuario.setItems(municipios);

            direccionUsuario.setValue(municipios.get(0));

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        primerNombreUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                primerNombreUsuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        segundoNombreUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                segundoNombreUsuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        tercerNombreUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                tercerNombreUsuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        primerApellidoUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                primerApellidoUsuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        segundoApellidoUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                segundoApellidoUsuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        apellidoConyugalUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                apellidoConyugalUsuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        telefonoUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[0-9-]*$")) {
                telefonoUsuario.setText(newValue.replaceAll("[^0-9-\\s]", ""));
            }
        });
    }

    public void btnRegistroAcceder(ActionEvent actionEvent) throws IOException, SQLException {

        if (validarCampos()) {
            Connection connection = null;
            connection = DatabaseUtil.getConnection();

            String selectedMunicipio = direccionUsuario.getValue(); 
            int codigoMunicipio = obtenerCodigoMunicipio(selectedMunicipio);

            String sql = "INSERT INTO tablaUsuario (primerNombre, segundoNombre, tercerNombre, primerApellido, segundoApellido, fechaNacimiento, apellidoCasado, telefono, contraseña, correo, codigoMunicipio) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, primerNombreUsuario.getText());
            preparedStatement.setString(2, segundoNombreUsuario.getText());
            preparedStatement.setString(3, tercerNombreUsuario.getText());
            preparedStatement.setString(4, primerApellidoUsuario.getText());
            preparedStatement.setString(5, segundoApellidoUsuario.getText());
            preparedStatement.setDate(6, java.sql.Date.valueOf(fechaUsuario.getValue()));
            preparedStatement.setString(7, apellidoConyugalUsuario.getText());
            preparedStatement.setString(8, telefonoUsuario.getText());
            preparedStatement.setString(9, contraseñaUsuario.getText());
            preparedStatement.setString(10, correoUsuario.getText());
            preparedStatement.setInt(11, codigoMunicipio);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();

                mostrarMensajeExito("Registro exitoso", "El usuario se ha registrado correctamente.");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setTitle("Login-Mey's Boutique");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } else {
                mostrarMensajeError("Error en el registro", "No se pudo realizar el registro.");
            }
        }
    }

    @FXML
    void cancelarRegisterAcction(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Login-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private boolean validarCampos() {
        boolean camposValidos = true;

        String nombre = primerNombreUsuario.getText();
        if (nombre.isEmpty() || nombre.matches(".*\\d.*")) {
            mostrarMensajeError("Error en el nombre", "El primer nombre no puede: \n• estar vacío. \n• contener números. \n• contener caracteres especiales. \n• contener espacios.");
            camposValidos = false;
        }

        String segundoNombre = segundoNombreUsuario.getText();
        if (segundoNombre.isEmpty() || !segundoNombre.matches("^[a-zA-Z]+$")) {
            mostrarMensajeError("Error en el segundo nombre", "El segundo nombre no puede: \n• estar vacío. \n• contener números. \n• contener caracteres especiales. \n• contener espacios.");
            camposValidos = false;
        }

        String tercerNombre = tercerNombreUsuario.getText();
        if (!tercerNombre.isEmpty()) {
            if (!tercerNombre.matches("^[a-zA-Z]+$")) {
                mostrarMensajeError("Error en el tercer nombre", "El tercer nombre no puede: \n• contener números. \n• contener caracteres especiales. \n• contener espacios.");
                camposValidos = false;
            }
        }

        String apellido = primerApellidoUsuario.getText();
        if (apellido.isEmpty() || !apellido.matches("^[a-zA-Z]+$")) {
            mostrarMensajeError("Error en el apellido", "El primer apellido no puede: \n• estar vacío. \n• contener números. \n• contener caracteres especiales. \n• contener espacios.");
            camposValidos = false;
        }

        String segundoApellido = segundoApellidoUsuario.getText();
        if (segundoApellido.isEmpty() || !segundoApellido.matches("^[a-zA-Z]+$")) {
            mostrarMensajeError("Error en el segundo apellido", "El segundo apellido no puede: \n• estar vacío. \n• contener números. \n• contener caracteres especiales. \n• contener espacios.");
            camposValidos = false;
        }

        String apellidoConyugal = apellidoConyugalUsuario.getText();
        if (!apellidoConyugal.isEmpty()) {
            if (!apellidoConyugal.matches("^[a-zA-Z]+$")) {
                mostrarMensajeError("Error en el apellido conyugal", "El el apellido conyugal no puede: \n• contener números. \n• contener caracteres especiales. \n• contener espacios.");
                camposValidos = false;
            }
        }

        String telefono = telefonoUsuario.getText();
        if (telefono.isEmpty() || !telefono.matches("^\\d{4}-\\d{4}$")) {
            mostrarMensajeError("Error en el teléfono", "El teléfono debe tener el formato 0000-0000.");
            camposValidos = false;
        }

        String correo = correoUsuario.getText();
        if (correo.isEmpty() || !correo.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}")) {
            mostrarMensajeError("Error en el correo", "El correo electrónico no es válido.");
            camposValidos = false;
        }

        LocalDate fechaNacimiento = fechaUsuario.getValue();
        LocalDate fechaMinima = LocalDate.of(1980, 1, 1); 
        LocalDate fechaMaxima = LocalDate.of(2002, 12, 31);
        if (fechaNacimiento == null || fechaNacimiento.isBefore(fechaMinima) || fechaNacimiento.isAfter(fechaMaxima)) {
            mostrarMensajeError("Error en la fecha de nacimiento", "Selecciona una fecha de nacimiento válida.");
            camposValidos = false;
        }

        String contraseña = contraseñaUsuario.getText();
        if (contraseña.isEmpty() || contraseña.length() < 6) {
            mostrarMensajeError("Error en la contraseña", "La contraseña no puede: \n • estar vacía \n • debe tener al menos 6 caracteres.");
            camposValidos = false;
        }

        String confirmacionContraseña = confirmarUsuario.getText();
        if (!confirmacionContraseña.equals(contraseña)) {
            mostrarMensajeError("Error en la confirmación de contraseña", "Las contraseñas no coinciden.");
            camposValidos = false;
        }

        return camposValidos;
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

    public int obtenerCodigoMunicipio(String nombreMunicipio) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        String municipioQuery = "SELECT codigoMunicipio FROM tablaMunicipio WHERE nombreMunicipio = ?";
        PreparedStatement municipioStatement = connection.prepareStatement(municipioQuery);
        municipioStatement.setString(1, nombreMunicipio);
        ResultSet municipioResultSet = municipioStatement.executeQuery();

        int codigoMunicipio = 1;

        if (municipioResultSet.next()) {
            codigoMunicipio = municipioResultSet.getInt("codigoMunicipio");
        }

        return codigoMunicipio;
    }
}
