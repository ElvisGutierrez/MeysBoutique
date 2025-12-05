package com.example.meysboutique;

import javafx.collections.FXCollections;
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
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class VentaController implements Initializable {

    public VentaController() {
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
    private ComboBox<String> cb_codigo_empleado;

    @FXML
    private ComboBox<String> cb_nombre_cliente;

    @FXML
    private ComboBox<String> cb_producto_venta;

    @FXML
    private ComboBox<String> cb_transacción_venta;

    @FXML
    private TableColumn<DatosVentas, Integer> colCantidadProducto;

    @FXML
    private TableColumn<DatosVentas, Integer> colCodigoVenta;

    @FXML
    private TableColumn<DatosVentas, Date> colFechaVenta;

    @FXML
    private TableColumn<DatosVentas, String> colNombreCliente;

    @FXML
    private TableColumn<DatosVentas, String> colNombreProducto;

    @FXML
    private TableColumn<DatosVentas, String> colNombreTransaccion;

    @FXML
    private TableColumn<DatosVentas, String> colNombreUsuario;

    @FXML
    private TableColumn<DatosVentas, BigDecimal> colTotalVenta;

    @FXML
    private Pane pnl_botones;

    @FXML
    private Pane panel_agregar;

    @FXML
    private Pane pnl_campos;

    @FXML
    private TableView<DatosVentas> tbl_datos_ventas;

    private ObservableList<DatosVentas> ventaData = FXCollections.observableArrayList();

    @FXML
    private TextField txf_cantidad_venta;

    private int ventaSeleccionada;

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
            String query = "SELECT tipoTransaccion FROM tablaTransaccion";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> transaccion = FXCollections.observableArrayList();

            while (resultSet.next()) {
                transaccion.add(resultSet.getString("tipoTransaccion"));
            }

            cb_transacción_venta.setItems(transaccion);

            cb_transacción_venta.setValue(transaccion.get(0));

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT nombreProducto FROM tablaProducto";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> productos = FXCollections.observableArrayList();

            while (resultSet.next()) {
                productos.add(resultSet.getString("nombreProducto"));
            }

            if (!productos.isEmpty()) {
                cb_producto_venta.setItems(productos);
                cb_producto_venta.setValue(productos.get(0));
            } else {
                cb_producto_venta.setItems(FXCollections.observableArrayList("No hay registros"));
                cb_producto_venta.setValue("No hay registros");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT primerNombre FROM tablaUsuario";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> empleado = FXCollections.observableArrayList();

            while (resultSet.next()) {
                empleado.add(resultSet.getString("primerNombre"));
            }

            if (!empleado.isEmpty()) {
                cb_codigo_empleado.setItems(empleado);
                cb_codigo_empleado.setValue(empleado.get(0));
            } else {
                cb_codigo_empleado.setItems(FXCollections.observableArrayList("No hay registros"));
                cb_codigo_empleado.setValue("No hay registros");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT nombreCliente FROM tablaCliente";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> cliente = FXCollections.observableArrayList();

            while (resultSet.next()) {
                cliente.add(resultSet.getString("nombreCliente"));
            }

            if (!cliente.isEmpty()) {
                cb_nombre_cliente.setItems(cliente);
                cb_nombre_cliente.setValue(cliente.get(0));
            } else {
                cb_nombre_cliente.setItems(FXCollections.observableArrayList("No hay registros"));
                cb_nombre_cliente.setValue("No hay registros");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        colCodigoVenta.setCellValueFactory(new PropertyValueFactory<>("codigoVenta"));
        colFechaVenta.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        colNombreTransaccion.setCellValueFactory(new PropertyValueFactory<>("tipoTransaccion"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colCantidadProducto.setCellValueFactory(new PropertyValueFactory<>("cantidadProducto"));
        colTotalVenta.setCellValueFactory(new PropertyValueFactory<>("totalVenta"));

        tbl_datos_ventas.setItems(ventaData);

        tbl_datos_ventas.setOnMouseClicked(event -> {
            DatosVentas VentaSeleccionada = tbl_datos_ventas.getSelectionModel().getSelectedItem();
            if (VentaSeleccionada != null) {

                String nombreCliente = VentaSeleccionada.getNombreCliente();
                cb_nombre_cliente.setValue(nombreCliente);

                String nombreEmpleado = VentaSeleccionada.getNombreEmpleado();
                cb_codigo_empleado.setValue(nombreEmpleado);

                String codigoTransaccion = VentaSeleccionada.getNombreProducto();
                cb_transacción_venta.setValue(codigoTransaccion);

                String codigoProducto = VentaSeleccionada.getTipoTransaccion();
                cb_producto_venta.setValue(codigoProducto);

                txf_cantidad_venta.setText(String.valueOf(VentaSeleccionada.getCantidadProducto()));

                VentaSeleccionada = tbl_datos_ventas.getSelectionModel().getSelectedItem();
                this.ventaSeleccionada = VentaSeleccionada.getCodigoVenta();

                pnl_campos.setDisable(false);
                btn_actualizar.setDisable(false);
                btn_eliminar.setDisable(false);
            }
        });

        txf_cantidad_venta.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[1-9.]*")) {
                txf_cantidad_venta.setText(newValue.replaceAll("[^1-9.]", ""));
            }
            if (newValue.length() > 9) {
                txf_cantidad_venta.setText(newValue.substring(0, 9));
            }
        });

        cargarDatosVentas();
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
    void ClickBtnCancelar(ActionEvent event) {
        Limpiar();

        tbl_datos_ventas.setDisable(false);
        pnl_campos.setDisable(true);
        panel_agregar.setDisable(true);
    }

    @FXML
    void ClickBtnDelete(ActionEvent event) {
        DatosVentas ventaSeleccionada = tbl_datos_ventas.getSelectionModel().getSelectedItem();
        if (ventaSeleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar esta venta?");
            alert.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int codigoVenta = ventaSeleccionada.getCodigoVenta();
                try {
                    Connection connection = DatabaseUtil.getConnection();
                    String deleteQuery = "DELETE FROM tablaVenta WHERE codigoVenta = ?";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                    deleteStatement.setInt(1, codigoVenta);

                    int rowsDeleted = deleteStatement.executeUpdate();

                    if (rowsDeleted > 0) {
                        mostrarMensajeExito("Eliminación exitosa", "Venta eliminada exitosamente.");
                        cargarDatosVentas();

                        tbl_datos_ventas.getSelectionModel().clearSelection();
                        Limpiar();
                        btn_eliminar.setDisable(true);
                        btn_actualizar.setDisable(true);
                        pnl_campos.setDisable(true);
                    } else {
                        mostrarMensajeError("Error", "La eliminación de la venta falló.");
                    }
                } catch (SQLException e) {
                    mostrarMensajeError("Error", "Error al eliminar la venta: " + e.getMessage());
                }
            }
        } else {
            mostrarMensajeError("Error", "Debes seleccionar una venta para eliminar.");
        }
    }

    @FXML
    void ClickBtnNew(ActionEvent event) {
        btn_eliminar.setDisable(true);
        btn_actualizar.setDisable(true);
        tbl_datos_ventas.getSelectionModel().clearSelection();
        tbl_datos_ventas.setDisable(true);
        panel_agregar.setDisable(false);
        pnl_campos.setDisable(false);
        Limpiar();
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
    void ClickBtnUpdate(ActionEvent event) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        String selectedCliente = cb_nombre_cliente.getValue(); 
        int codigoCliente = obtenerCodigoCliente(selectedCliente);
        String selectedUsuario = cb_codigo_empleado.getValue(); 
        int codigoUsuario = obtenerCodigoUsuario(selectedUsuario);
        String selectedTransaccion = cb_transacción_venta.getValue(); 
        int codigoTransaccion = obtenerCodigoTransaccion(selectedTransaccion);
        String selectedProducto = cb_producto_venta.getValue(); 
        int codigoProducto = obtenerCodigoProducto(selectedProducto);
        int cantidadProducto = Integer.parseInt(txf_cantidad_venta.getText()); 
        BigDecimal precioProducto = obtenerPrecioProducto(selectedProducto); 
        BigDecimal totalVenta = precioProducto.multiply(BigDecimal.valueOf(cantidadProducto));

        if (selectedCliente.isEmpty() || selectedUsuario.isEmpty() || selectedTransaccion.isEmpty()
                || selectedProducto.isEmpty() || txf_cantidad_venta.getText().isEmpty()) {
            mostrarMensajeError("Error", "Debe llenar todos los campos.");
        } else {
            String updateVentaQuery = "UPDATE tablaVenta SET codigoCliente = ?, codigoUsuario = ?, codigoTransaccion = ?, codigoProducto = ?, cantidadProducto = ?, totalVenta = ? WHERE codigoVenta = ?";

            int codigoVenta = ventaSeleccionada;

            PreparedStatement updateVentaStatement = connection.prepareStatement(updateVentaQuery);
            updateVentaStatement.setInt(1, codigoCliente);
            updateVentaStatement.setInt(2, codigoUsuario);
            updateVentaStatement.setInt(3, codigoTransaccion);
            updateVentaStatement.setInt(4, codigoProducto);
            updateVentaStatement.setInt(5, cantidadProducto);
            updateVentaStatement.setBigDecimal(6, totalVenta);
            updateVentaStatement.setInt(7, codigoVenta); 

            int rowsUpdated = updateVentaStatement.executeUpdate();

            if (rowsUpdated > 0) {
                mostrarMensajeExito("Actualización exitosa", "Venta actualizada exitosamente.");
                cargarDatosVentas();

                tbl_datos_ventas.getSelectionModel().clearSelection();
                Limpiar();
                btn_actualizar.setDisable(true);
                pnl_campos.setDisable(true);
                btn_eliminar.setDisable(true);
            } else {
                mostrarMensajeError("Error", "La actualización de la venta falló.");
            }
        }
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
    void clickAgregar(ActionEvent event) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        String selectedCliente = cb_nombre_cliente.getValue(); 
        int codigoCliente = obtenerCodigoCliente(selectedCliente);
        String selectedUsuario = cb_codigo_empleado.getValue(); 
        int codigoUsuario = obtenerCodigoUsuario(selectedUsuario);
        String selectedTransaccion = cb_transacción_venta.getValue(); 
        int codigoTransaccion = obtenerCodigoTransaccion(selectedTransaccion);
        String selectedProducto = cb_producto_venta.getValue(); 
        int codigoProducto = obtenerCodigoProducto(selectedProducto);
        int cantidadProducto = Integer.parseInt(txf_cantidad_venta.getText()); 
        BigDecimal precioProducto = obtenerPrecioProducto(selectedProducto); 
        BigDecimal totalVenta = precioProducto.multiply(BigDecimal.valueOf(cantidadProducto)); 

        String cantidad = txf_cantidad_venta.getText();


        if (selectedCliente.isEmpty() || selectedUsuario.isEmpty() || selectedTransaccion.isEmpty()
                || selectedProducto.isEmpty() || cantidad.isEmpty()) {
            mostrarMensajeError("Error", "Debe llenar todos los campos.");
        } else {
            String insertVentaQuery = "INSERT INTO tablaVenta (fechaVenta, codigoCliente, codigoUsuario, codigoTransaccion, codigoProducto, cantidadProducto, totalVenta) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertVentaStatement = connection.prepareStatement(insertVentaQuery);
            insertVentaStatement.setDate(1, java.sql.Date.valueOf(LocalDate.now())); 
            insertVentaStatement.setInt(2, codigoCliente);
            insertVentaStatement.setInt(3, codigoUsuario);
            insertVentaStatement.setInt(4, codigoTransaccion);
            insertVentaStatement.setInt(5, codigoProducto);
            insertVentaStatement.setInt(6, cantidadProducto);
            insertVentaStatement.setBigDecimal(7, totalVenta);
            insertVentaStatement.executeUpdate();

            mostrarMensajeExito("Registro exitoso", "Venta registrada exitosamente.");

            cargarDatosVentas();

            panel_agregar.setDisable(true);
            pnl_campos.setDisable(true);
            tbl_datos_ventas.getSelectionModel().clearSelection();
            tbl_datos_ventas.setDisable(false);
            Limpiar();
        }
    }

    private int obtenerCodigoCliente(String nombreCliente) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        String clienteQuery = "SELECT codigoCliente FROM tablaCliente WHERE nombreCliente = ?";
        PreparedStatement clienteStatement = connection.prepareStatement(clienteQuery);
        clienteStatement.setString(1, nombreCliente);
        ResultSet clienteResultSet = clienteStatement.executeQuery();

        int codigoCliente = -1; 

        if (clienteResultSet.next()) {
            codigoCliente = clienteResultSet.getInt("codigoCliente");
        }

        return codigoCliente;
    }

    private int obtenerCodigoUsuario(String nombreUsuario) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        String usuarioQuery = "SELECT codigoUsuario FROM tablaUsuario WHERE primerNombre = ?";
        PreparedStatement usuarioStatement = connection.prepareStatement(usuarioQuery);
        usuarioStatement.setString(1, nombreUsuario);
        ResultSet usuarioResultSet = usuarioStatement.executeQuery();

        int codigoUsuario = -1; 

        if (usuarioResultSet.next()) {
            codigoUsuario = usuarioResultSet.getInt("codigoUsuario");
        }
        return codigoUsuario;
    }

    private int obtenerCodigoTransaccion(String nombreTransaccion) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        String transaccionQuery = "SELECT codigoTransaccion FROM tablaTransaccion WHERE tipoTransaccion = ?";
        PreparedStatement transaccionStatement = connection.prepareStatement(transaccionQuery);
        transaccionStatement.setString(1, nombreTransaccion);
        ResultSet transaccionResultSet = transaccionStatement.executeQuery();

        int codigoTransaccion = -1; 

        if (transaccionResultSet.next()) {
            codigoTransaccion = transaccionResultSet.getInt("codigoTransaccion");
        }

        return codigoTransaccion;
    }

    private int obtenerCodigoProducto(String nombreProducto) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        String productoQuery = "SELECT codigoProducto FROM tablaProducto WHERE nombreProducto = ?";
        PreparedStatement productoStatement = connection.prepareStatement(productoQuery);
        productoStatement.setString(1, nombreProducto);
        ResultSet productoResultSet = productoStatement.executeQuery();

        int codigoProducto = -1; 

        if (productoResultSet.next()) {
            codigoProducto = productoResultSet.getInt("codigoProducto");
        }

        return codigoProducto;
    }

    private BigDecimal obtenerPrecioProducto(String nombreProducto) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        String precioProductoQuery = "SELECT precio FROM tablaProducto WHERE nombreProducto = ?";
        PreparedStatement precioProductoStatement = connection.prepareStatement(precioProductoQuery);
        precioProductoStatement.setString(1, nombreProducto);
        ResultSet precioProductoResultSet = precioProductoStatement.executeQuery();

        BigDecimal precioProducto = BigDecimal.ZERO; 

        if (precioProductoResultSet.next()) {
            precioProducto = precioProductoResultSet.getBigDecimal("precio");
        }

        return precioProducto;
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

    private void asignarEventosHover(Button boton) {
        boton.setOnMouseEntered(event -> {
            boton.setStyle("-fx-background-color: rgb(238, 187, 195, 0.4); -fx-text-fill: white;");
        });

        boton.setOnMouseExited(event -> {
            boton.setStyle("-fx-background-color: transparent;");
        });
    }

    private void cargarDatosVentas() {
        ventaData.clear();

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            String query = "SELECT v.codigoVenta, v.fechaVenta, c.nombreCliente, u.primerNombre, t.tipoTransaccion, p.nombreProducto, v.cantidadProducto, v.totalVenta " +
                    "FROM tablaVenta v " +
                    "INNER JOIN tablaCliente c ON v.codigoCliente = c.codigoCliente " +
                    "INNER JOIN tablaUsuario u ON v.codigoUsuario = u.codigoUsuario " +
                    "INNER JOIN tablaTransaccion t ON v.codigoTransaccion = t.codigoTransaccion " +
                    "INNER JOIN tablaProducto p ON v.codigoProducto = p.codigoProducto";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int codigoVenta = resultSet.getInt("codigoVenta");
                Date fechaVenta = resultSet.getDate("fechaVenta");
                String nombreCliente = resultSet.getString("nombreCliente");
                String nombreUsuario = resultSet.getString("primerNombre");
                String nombreTransaccion = resultSet.getString("tipoTransaccion");
                String nombreProducto = resultSet.getString("nombreProducto");
                int cantidadProducto = resultSet.getInt("cantidadProducto");
                BigDecimal totalVenta = resultSet.getBigDecimal("totalVenta");

                DatosVentas venta = new DatosVentas(codigoVenta, fechaVenta, nombreCliente, nombreUsuario, nombreProducto, nombreTransaccion, cantidadProducto, totalVenta);
                ventaData.add(venta);
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
        cb_nombre_cliente.setValue("");
        cb_codigo_empleado.setValue("");
        cb_transacción_venta.setValue("");
        cb_producto_venta.setValue("");
        txf_cantidad_venta.setText("");
    }

}
