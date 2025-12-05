package com.example.meysboutique;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.meysboutique.DatabaseUtil.getConnection;

public class ProveedorController implements Initializable {

    public ProveedorController() {
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
    private Button btn_actualizar;

    @FXML
    private Button btn_eliminar;

    @FXML
    private Button btn_New;

    @FXML
    private TableColumn<DatosProveedores, String> colCodigo;

    @FXML
    private TableColumn<DatosProveedores, String> colDireccion;

    @FXML
    private TableColumn<DatosProveedores, String> colNombre;

    @FXML
    private TableColumn<DatosProveedores, String> colTelefono;

    @FXML
    private Pane pnl_botones;

    @FXML
    private Pane pnl_campos;

    @FXML
    private ComboBox<String> txf_direccion_proveedor;

    @FXML
    private TableView<DatosProveedores> tbl_datos_proveedor;

    @FXML
    private TextField txf_nombre_proveedor;

    @FXML
    private TextField txf_nombre_encargado;

    @FXML
    private TextField txf_telefono_proveedor;

    @FXML
    void ClickBtnCancelar(ActionEvent event) {
        vaciar();
        pnl_botones.setDisable(false);
        pnl_campos.setDisable(true);
        tbl_datos_proveedor.getSelectionModel().clearSelection();
        tbl_datos_proveedor.setDisable(false);
    }

    @FXML
    void ClickBtnDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de eliminar?");
        alert.setContentText("Esta acción no se puede deshacer.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            int nfila = tbl_datos_proveedor.getSelectionModel().getSelectedIndex();

            if (nfila >= 0) {
                DatosProveedores proveedor = tbl_datos_proveedor.getItems().get(nfila);
                eliminarProveedorDeBaseDeDatos(proveedor); 
                tbl_datos_proveedor.getItems().remove(nfila); 
                vaciar();
            } else {
                mostrarMensajeExito("Seleccione una fila","No se ha seleccionado ninguna fila para eliminar.");
                tbl_datos_proveedor.getSelectionModel().clearSelection();
            }
        }

        tbl_datos_proveedor.getSelectionModel().clearSelection();
    }

    @FXML
    void ClickBtnNew(ActionEvent event) {
        estadoGuardado = 1;
        pnl_campos.setDisable(false);
        pnl_botones.setDisable(true);
        tbl_datos_proveedor.getSelectionModel().clearSelection();
        tbl_datos_proveedor.setDisable(true);
        vaciar();
    }

    @FXML
    void ClickBtnUpdate(ActionEvent event) {
        if (tbl_datos_proveedor.getSelectionModel().isEmpty()) {
            mostrarMensajeExito("Seleccionar fila","Seleccione una fila para editar.");
        } else {
            estadoGuardado = 2;
            pnl_campos.setDisable(false);
            pnl_botones.setDisable(true);
            tbl_datos_proveedor.setDisable(true);
        }
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

    int estadoGuardado = 0;

    @FXML
    void clickAgregar(ActionEvent event) {
        String nombreProveedor = txf_nombre_proveedor.getText();
        String nombreEncargado = txf_nombre_encargado.getText();
        String direccion = txf_direccion_proveedor.getValue();
        String telefono = txf_telefono_proveedor.getText();



        if (nombreProveedor.isEmpty() || nombreEncargado.isEmpty() || direccion == null || telefono.isEmpty()) {

            mostrarMensajeError("Error en los campos de texto", "Por favor, complete todos los campos.");
            return;
        } else {
            if (!telefono.matches("^\\d{4}-\\d{4}$")){
                mostrarMensajeError("Error en el campo de teléfono", "El número de teléfono debe tener el formato: 0000-0000.");
                return;
            }else{
                if (estadoGuardado == 1) {
                    DatosProveedores proveedor = new DatosProveedores(0, nombreProveedor, nombreEncargado, direccion, telefono);
                    tbl_datos_proveedor.getItems().add(proveedor);

                    insertarProveedorEnBaseDeDatos(proveedor);

                    proveedor.setCodigoProveedor(obtenerCodigoProveedorDesdeBaseDeDatos()); 
                    tbl_datos_proveedor.refresh(); 

                    mostrarMensajeExito("Datos","Datos Guardados");
                    tbl_datos_proveedor.setDisable(false);
                    tbl_datos_proveedor.getSelectionModel().clearSelection();
                } else if (estadoGuardado == 2) {
                    int nfila = tbl_datos_proveedor.getSelectionModel().getSelectedIndex();
                    if (nfila >= 0) {
                        DatosProveedores proveedor = tbl_datos_proveedor.getItems().get(nfila);
                        proveedor.setNombreProveedor(nombreProveedor);
                        proveedor.setNombreEncargado(nombreEncargado);
                        proveedor.setDireccion(direccion);
                        proveedor.setTelefono(telefono);
                        tbl_datos_proveedor.refresh(); 

                        actualizarProveedorEnBaseDeDatos(proveedor);

                        mostrarMensajeExito("Datos","Datos Actualizados");
                        tbl_datos_proveedor.getSelectionModel().clearSelection();
                        tbl_datos_proveedor.setDisable(false);
                    } else {
                        mostrarMensajeExito("Seleccionar fila","No se ha seleccionado ninguna fila para actualizar.");
                    }
                }
                cargarDatosDesdeBaseDeDatos();
            }

            vaciar();
            pnl_campos.setDisable(true);
            pnl_botones.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cargarDatosDesdeBaseDeDatos();

        asignarEventosHover(btnBoutiques);
        asignarEventosHover(btnUsuarios);
        asignarEventosHover(btnCerrar);
        asignarEventosHover(btnMenu);
        asignarEventosHover(btnClientes);

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEncargado"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        tbl_datos_proveedor.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends DatosProveedores> change) -> {

            if (!change.getList().isEmpty()) {
                btn_actualizar.setDisable(false);
                btn_eliminar.setDisable(false);
            } else {
                btn_actualizar.setDisable(true);
                btn_eliminar.setDisable(true);
            }
        });

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT nombreMunicipio FROM tablaMunicipio";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> municipios = FXCollections.observableArrayList();

            while (resultSet.next()) {
                municipios.add(resultSet.getString("nombreMunicipio"));
            }

            txf_direccion_proveedor.setItems(municipios);

            txf_direccion_proveedor.setValue(municipios.get(0));

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tbl_datos_proveedor.setOnMouseClicked(event -> {
            DatosProveedores proveedorSeleccionado = tbl_datos_proveedor.getSelectionModel().getSelectedItem();
            if (proveedorSeleccionado != null) {
                txf_nombre_proveedor.setText(proveedorSeleccionado.getNombreProveedor());
                txf_nombre_encargado.setText(proveedorSeleccionado.getNombreEncargado());
                txf_direccion_proveedor.setValue("Valor deseado");
                txf_telefono_proveedor.setText(proveedorSeleccionado.getTelefono());
            }
        });

        tbl_datos_proveedor.setOnMouseClicked(event -> {
            DatosProveedores proveedorSeleccionado = tbl_datos_proveedor.getSelectionModel().getSelectedItem();
            if (proveedorSeleccionado != null) {
                txf_nombre_proveedor.setText(proveedorSeleccionado.getNombreProveedor());
                txf_nombre_encargado.setText(proveedorSeleccionado.getNombreEncargado());

                String direccion = proveedorSeleccionado.getDireccion();

                txf_direccion_proveedor.setValue(direccion);

                txf_telefono_proveedor.setText(proveedorSeleccionado.getTelefono());
            }
        });

        txf_nombre_proveedor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 100) {
                txf_nombre_proveedor.setText(oldValue); 
            } else if (!newValue.matches("^[a-zA-Z\\s]*$")) {
                txf_nombre_proveedor.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        txf_nombre_encargado.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$ ")) {
                txf_nombre_encargado.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        txf_telefono_proveedor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_telefono_proveedor.setText(newValue.replaceAll("[^\\d-]", ""));
            }
            if (newValue.length() > 9) {
                txf_telefono_proveedor.setText(newValue.substring(0, 9));
            }
        });
    }


    private void asignarEventosHover(Button boton) {
        boton.setOnMouseEntered(event -> {
            boton.setStyle("-fx-background-color: rgb(238, 187, 195, 0.4); -fx-text-fill: white;");
        });

        boton.setOnMouseExited(event -> {
            boton.setStyle("-fx-background-color: transparent;");
        });
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

    private void vaciar() {
        txf_nombre_proveedor.clear();
        txf_nombre_encargado.clear();
        txf_telefono_proveedor.clear();
        txf_direccion_proveedor.setValue(null);
    }

    private void insertarProveedorEnBaseDeDatos(DatosProveedores proveedor) {
        try (Connection con = getConnection()) {


            String selectedMunicipio = txf_direccion_proveedor.getValue(); 
            int codigoMunicipio = obtenerCodigoMunicipio(selectedMunicipio);

            String insertSQL = "INSERT INTO tablaProveedor (nombreProveedor, nombreEncargado, telefono, codigoMunicipio) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(insertSQL);
            stmt.setString(1, proveedor.getNombreProveedor());
            stmt.setString(2, proveedor.getNombreEncargado());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setInt(4, codigoMunicipio);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudieron insertar los datos en la base de datos.");
        }
    }


    private void actualizarProveedorEnBaseDeDatos(DatosProveedores proveedor) {

        try (Connection con = getConnection()) {

            String selectedMunicipio = txf_direccion_proveedor.getValue();
            int nuevoCodigoMunicipio = obtenerCodigoMunicipio(selectedMunicipio);

            String updateSQL = "UPDATE tablaProveedor SET nombreProveedor = ?, nombreEncargado = ?, telefono = ?, codigoMunicipio = ? WHERE codigoProveedor = ?";
            PreparedStatement stmt = con.prepareStatement(updateSQL);
            stmt.setString(1, proveedor.getNombreProveedor());
            stmt.setString(2, proveedor.getNombreEncargado());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setInt(4, nuevoCodigoMunicipio);
            stmt.setInt(5, proveedor.getCodigoProveedor()); 


            cargarDatosDesdeBaseDeDatos();

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudieron actualizar los datos en la base de datos.");
        }
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



    private void cargarDatosDesdeBaseDeDatos() {
        try (Connection con = getConnection()) {
            String selectSQL = "SELECT P.codigoProveedor, P.nombreProveedor, P.nombreEncargado, M.nombreMunicipio, P.telefono " +
                    "FROM tablaProveedor AS P " +
                    "INNER JOIN tablaMunicipio AS M ON P.codigoMunicipio = M.codigoMunicipio";
            PreparedStatement stmt = con.prepareStatement(selectSQL);
            ResultSet resultSet = stmt.executeQuery();

            tbl_datos_proveedor.getItems().clear();

            while (resultSet.next()) {
                int codigoProveedor = resultSet.getInt("codigoProveedor");
                String nombreProveedor = resultSet.getString("nombreProveedor");
                String nombreEncargado = resultSet.getString("nombreEncargado");
                String nombreMunicipio = resultSet.getString("nombreMunicipio");
                String telefono = resultSet.getString("telefono");

                DatosProveedores proveedor = new DatosProveedores(codigoProveedor, nombreProveedor, nombreEncargado, nombreMunicipio, telefono);
                tbl_datos_proveedor.getItems().add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudieron cargar los datos desde la base de datos.");
        }
    }

    private void eliminarProveedorDeBaseDeDatos(DatosProveedores proveedor) {
        try (Connection con = getConnection()) {
            String deleteSQL = "DELETE FROM tablaProveedor WHERE codigoProveedor = ?";
            PreparedStatement stmt = con.prepareStatement(deleteSQL);

            stmt.setInt(1, proveedor.getCodigoProveedor()); 
            System.out.println("SQL Query: " + stmt.toString());
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                mostrarMensajeExito("Eliminar registro", "Registro eliminado con éxito.");
            } else {
                mostrarMensajeError("Eliminar registro", "No se pudo eliminar el registro.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudo eliminar el registro de la base de datos.");
        }
    }

    private int obtenerCodigoProveedorDesdeBaseDeDatos() {
        int codigoProveedor = 0; 
        return codigoProveedor;
    }


}
