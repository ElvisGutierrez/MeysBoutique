package com.example.meysboutique;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;


public class ClienteController implements Initializable {

    public ClienteController() {
    }

    @FXML
    private Button btnBoutiques;

    @FXML
    private Button btnCargos;

    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnUsuarios;

    @FXML
    private Button btn_New;

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_eliminar;

    @FXML
    private TableView<DatosClientes> tbl_datos_clientes;

    @FXML
    private TableColumn<DatosClientes, Integer> colCodigo;

    @FXML
    private TableColumn<DatosClientes, String> colNombre;

    @FXML
    private TableColumn<DatosClientes, String> colApellido;

    @FXML
    private TableColumn<DatosClientes, String> colDui;

    @FXML
    private TableColumn<DatosClientes, String> colTelefono;

    @FXML
    private TableColumn<DatosClientes, String> colDireccion;

    private ObservableList<DatosClientes> clienteData = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> direccionCliente;

    @FXML
    private Pane pnl_botones;

    @FXML
    private Pane pnl_campos;

    @FXML
    private Pane panel_agregar;

    @FXML
    private TextField txf_apellido_cliente;

    @FXML
    private TextField txf_dui_cliente;

    @FXML
    private TextField txf_nombre_cliente;

    @FXML
    private TextField txf_telefono_cliente;

    private int clienteSeleccionado;

    @FXML
    void ClickBtnCancelar(ActionEvent event) {
        txf_nombre_cliente.setText("");
        txf_apellido_cliente.setText("");
        txf_dui_cliente.setText("");
        txf_telefono_cliente.setText("");
        direccionCliente.setValue(direccionCliente.getItems().get(0));

        tbl_datos_clientes.setDisable(false);
        pnl_campos.setDisable(true);
        panel_agregar.setDisable(true);
    }

    @FXML
    void usuariosOpen(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("usuarios-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Usuarios-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void ClickBtnPDF(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                contentStream.beginText();
                contentStream.setFont(getFont(document), 12); 
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Reporte de Clientes");
                contentStream.newLineAtOffset(0, -20);

                for (DatosClientes cliente : clienteData) {
                    contentStream.showText("Nombre: " + cliente.getNombreCliente());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Apellido: " + cliente.getApellidoCliente());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("DUI: " + cliente.getDuiCliente());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Teléfono: " + cliente.getTelefonoCliente());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Municipio: " + cliente.getNombreMunicipio());
                    contentStream.newLineAtOffset(0, -20);
                }

                contentStream.endText();
                contentStream.close();

                document.save(file);
                document.close();

                mostrarMensajeExito("Generación de PDF", "El reporte PDF se generó exitosamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeError("Error", "Error al generar el reporte PDF: " + e.getMessage());
        }
    }

    private PDType0Font getFont(PDDocument document) throws IOException {
        return PDType0Font.load(document, getClass().getResourceAsStream("/fonts/Roboto-Light.ttf"));
    }

    @FXML
    void ClickBtnDelete(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de eliminar?");
        alert.setContentText("Esta acción no se puede deshacer.");
        Optional<ButtonType> result = alert.showAndWait();

        try {
            Connection connection = DatabaseUtil.getConnection();

            DatosClientes clienteSeleccionado = tbl_datos_clientes.getSelectionModel().getSelectedItem();

            if (clienteSeleccionado != null) {

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    int codigoCliente = clienteSeleccionado.getCodigoCliente();

                    String deleteClienteQuery = "DELETE FROM tablaCliente WHERE codigoCliente = ?";
                    PreparedStatement deleteClienteStatement = connection.prepareStatement(deleteClienteQuery);
                    deleteClienteStatement.setInt(1, codigoCliente);
                    deleteClienteStatement.executeUpdate();

                    mostrarMensajeExito("Eliminación exitosa", "Cliente eliminado exitosamente.");

                    cargarDatosClientes();

                    tbl_datos_clientes.getSelectionModel().clearSelection();
                    Limpiar();
                    btn_eliminar.setDisable(true);
                    btn_actualizar.setDisable(true);
                    pnl_campos.setDisable(true);
                }

            } else {
                mostrarMensajeError("Error", "Debes seleccionar un cliente para eliminar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error", "Error al eliminar el cliente.");
        }
    }

    @FXML
    void comprasOpen(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("compra-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Compras-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void ClickBtnNew(ActionEvent event) {
        panel_agregar.setDisable(false);
        pnl_campos.setDisable(false);
        btn_eliminar.setDisable(true);
        btn_actualizar.setDisable(true);
        tbl_datos_clientes.getSelectionModel().clearSelection();
        tbl_datos_clientes.setDisable(true);
        Limpiar();
    }

    @FXML
    void ClickBtnUpdate(ActionEvent event) {
        try {
            Connection connection = DatabaseUtil.getConnection();

            int codigoCliente =  clienteSeleccionado;  
            String nuevoNombre = txf_nombre_cliente.getText();
            String nuevoApellido = txf_apellido_cliente.getText();
            String nuevoDui = txf_dui_cliente.getText();
            String nuevoTelefono = txf_telefono_cliente.getText();
            String selectedMunicipio = direccionCliente.getValue();
            int nuevoCodigoMunicipio = obtenerCodigoMunicipio(selectedMunicipio);

            if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || nuevoDui.isEmpty() || nuevoTelefono.isEmpty()) {
                mostrarMensajeError("Error", "Debe llenar todos los campos.");
            } else {
                String updateClienteQuery = "UPDATE tablaCliente " +
                        "SET nombreCliente = ?, apellidoCliente = ?, DUI = ?, telefonoCliente = ?, codigoMunicipio = ? " +
                        "WHERE codigoCliente = ?";
                PreparedStatement updateClienteStatement = connection.prepareStatement(updateClienteQuery);
                updateClienteStatement.setString(1, nuevoNombre);
                updateClienteStatement.setString(2, nuevoApellido);
                updateClienteStatement.setString(3, nuevoDui);
                updateClienteStatement.setString(4, nuevoTelefono);
                updateClienteStatement.setInt(5, nuevoCodigoMunicipio);
                updateClienteStatement.setInt(6, codigoCliente);
                updateClienteStatement.executeUpdate();

                mostrarMensajeExito("Actualización exitosa", "Cliente actualizado exitosamente.");

                cargarDatosClientes();

                tbl_datos_clientes.getSelectionModel().clearSelection();
                Limpiar();
                btn_actualizar.setDisable(true);
                pnl_campos.setDisable(true);
                btn_eliminar.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error", "Error al actualizar el cliente.");
        }
    }

    @FXML
    void clickAgregar(ActionEvent event) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();
        String nombre = txf_nombre_cliente.getText();
        String apellido = txf_apellido_cliente.getText();
        String dui = txf_dui_cliente.getText();
        String telefono = txf_telefono_cliente.getText();

        String selectedMunicipio = direccionCliente.getValue(); 
        int codigoMunicipio = obtenerCodigoMunicipio(selectedMunicipio);

        if (nombre.isEmpty() || apellido.isEmpty() || dui.isEmpty() || telefono.isEmpty()) {
            mostrarMensajeError("Error", "Debe llenar todos los campos.");
        } else {
            if(validarCampos()){
                String insertClienteQuery = "INSERT INTO tablaCliente (nombreCliente, apellidoCliente, DUI, telefonoCliente, codigoMunicipio) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertClienteStatement = connection.prepareStatement(insertClienteQuery);
                insertClienteStatement.setString(1, nombre);
                insertClienteStatement.setString(2, apellido);
                insertClienteStatement.setString(3, dui);
                insertClienteStatement.setString(4, telefono);
                insertClienteStatement.setInt(5, codigoMunicipio);
                insertClienteStatement.executeUpdate();

                mostrarMensajeExito("Registro exitoso", "Cliente registrado exitosamente.");

                cargarDatosClientes();

                panel_agregar.setDisable(true);
                pnl_campos.setDisable(true);
                tbl_datos_clientes.getSelectionModel().clearSelection();
                tbl_datos_clientes.setDisable(false);
                Limpiar();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        asignarEventosHover(btnBoutiques);
        asignarEventosHover(btnUsuarios);
        asignarEventosHover(btnCerrar);
        asignarEventosHover(btnMenu);
        asignarEventosHover(btnClientes);

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT nombreMunicipio FROM tablaMunicipio";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> municipios = FXCollections.observableArrayList();

            while (resultSet.next()) {
                municipios.add(resultSet.getString("nombreMunicipio"));
            }

            direccionCliente.setItems(municipios);

            direccionCliente.setValue(municipios.get(0));

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoCliente"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellidoCliente"));
        colDui.setCellValueFactory(new PropertyValueFactory<>("duiCliente"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoCliente"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("nombreMunicipio"));

        tbl_datos_clientes.setItems(clienteData);

        cargarDatosClientes();

        tbl_datos_clientes.setOnMouseClicked(event -> {
            DatosClientes clienteSeleccionado = tbl_datos_clientes.getSelectionModel().getSelectedItem();
            if (clienteSeleccionado != null) {
                txf_nombre_cliente.setText(clienteSeleccionado.getNombreCliente());
                txf_apellido_cliente.setText(clienteSeleccionado.getApellidoCliente());
                txf_dui_cliente.setText(clienteSeleccionado.getDuiCliente());
                txf_telefono_cliente.setText(clienteSeleccionado.getTelefonoCliente());
                direccionCliente.setValue(clienteSeleccionado.getNombreMunicipio());

                clienteSeleccionado = tbl_datos_clientes.getSelectionModel().getSelectedItem();
                this.clienteSeleccionado = clienteSeleccionado.getCodigoCliente();

                pnl_campos.setDisable(false);
                btn_actualizar.setDisable(false);
                btn_eliminar.setDisable(false);
            }
        });

        txf_nombre_cliente.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 50) {
                txf_nombre_cliente.setText(oldValue); 
            } else if (!newValue.matches("^[a-zA-Z\\s]*$")) {
                txf_nombre_cliente.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        txf_apellido_cliente.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 50) {
                txf_apellido_cliente.setText(oldValue); 
            } else if (!newValue.matches("^[a-zA-Z\\s]*$")) {
                txf_apellido_cliente.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        txf_telefono_cliente.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_telefono_cliente.setText(newValue.replaceAll("[^\\d-]", ""));
            }
            if (newValue.length() > 9) {
                txf_telefono_cliente.setText(newValue.substring(0, 9));
            }
        });

        txf_dui_cliente.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_dui_cliente.setText(newValue.replaceAll("[^\\d-]", ""));
            }
            if (newValue.length() > 10) {
                txf_dui_cliente.setText(newValue.substring(0, 10));
            }
        });
    }

    @FXML
    void menuInicioOpen(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Inicio-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        int codigoUsuario = obtenerCodigoUsuarioActual();

        eliminarSesionUsuario(codigoUsuario);

        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        mostrarMensajeExito("Cerrar sesión", "Sesión cerrada exitosamente.");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Login-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private int obtenerCodigoUsuarioActual() {
        int codigoUsuario = -1; 

        try {
            Connection connection = DatabaseUtil.getConnection();
            if (connection != null) {
                String selectQuery = "SELECT codigoUsuario FROM tablaSesionUsuario LIMIT 1";
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    codigoUsuario = resultSet.getInt("codigoUsuario");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarMensajeError("Error de SQL", "Ocurrió un error al ejecutar la consulta SQL: " + ex.getMessage());
        }

        return codigoUsuario;
    }

    private void eliminarSesionUsuario(int codigoUsuario) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            if (connection != null) {
                String deleteQuery = "DELETE FROM tablaSesionUsuario WHERE codigoUsuario = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, codigoUsuario);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarMensajeError("Error de SQL", "Ocurrió un error al ejecutar la consulta SQL: " + ex.getMessage());
        }
    }

    private void asignarEventosHover(Button boton) {
        boton.setOnMouseEntered(event -> {
            boton.setStyle("-fx-background-color: rgb(238, 187, 195, 0.4); -fx-text-fill: white;");
        });

        boton.setOnMouseExited(event -> {
            boton.setStyle("-fx-background-color: transparent;");
        });
    }

    private void mostrarMensajeExito(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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

    private void cargarDatosClientes() {
        clienteData.clear();

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            String query = "SELECT c.codigoCliente, c.nombreCliente, c.apellidoCliente, c.DUI, c.telefonoCliente, m.nombreMunicipio " +
                    "FROM tablaCliente c " +
                    "INNER JOIN tablaMunicipio m ON c.codigoMunicipio = m.codigoMunicipio";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int codigoCliente = resultSet.getInt("codigoCliente");
                String nombreCliente = resultSet.getString("nombreCliente");
                String apellidoCliente = resultSet.getString("apellidoCliente");
                String duiCliente = resultSet.getString("DUI");
                String telefonoCliente = resultSet.getString("telefonoCliente");
                String nombreMunicipio = resultSet.getString("nombreMunicipio");

                DatosClientes cliente = new DatosClientes(codigoCliente, nombreCliente, apellidoCliente, duiCliente, telefonoCliente, nombreMunicipio);
                clienteData.add(cliente);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudieron cargar los datos desde la base de datos.");
        }
    }

    private void Limpiar() {
        txf_nombre_cliente.clear();
        txf_apellido_cliente.clear();
        txf_dui_cliente.clear();
        txf_telefono_cliente.clear();
    }

    private boolean validarCampos() {
        boolean camposValidos = true;

        String telefono = txf_telefono_cliente.getText();
        if (telefono.isEmpty() || !telefono.matches("^\\d{4}-\\d{4}$")) {
            mostrarMensajeError("Error en el teléfono", "El teléfono debe tener el formato 0000-0000.");
            camposValidos = false;
        }

        String dui = txf_dui_cliente.getText();
        if (dui.isEmpty() || !dui.matches("^\\d{8}-\\d$")) {
            mostrarMensajeError("Error en el DUI", "El DUI debe tener el formato 00000000-0.");
            camposValidos = false;
        }

        return camposValidos;
    }
}
