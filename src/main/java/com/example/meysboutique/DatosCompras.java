package com.example.meysboutique;

public class DatosCompras {
    private String codigo_compra;
    private String fecha_compra;
    private String codigo_empleado;
    private String codigo_transaccion;
    private String codigo_producto;
    private String codigo_proveedor;
    private String cantidad_producto;
    private String precio_compra;
    private String total_compra;


    public DatosCompras(String codigo_compra, String fecha_compra, String codigo_empleado, String codigo_transaccion, String codigo_producto, String codigo_proveedor, String cantidad_producto, String precio_compra, String total_compra) {
        this.codigo_compra = codigo_compra;
        this.fecha_compra = fecha_compra;
        this.codigo_empleado = codigo_empleado;
        this.codigo_transaccion = codigo_transaccion;
        this.codigo_producto = codigo_producto;
        this.codigo_proveedor = codigo_proveedor;
        this.cantidad_producto = cantidad_producto;
        this.precio_compra = precio_compra;
        this.total_compra = total_compra;
    }


    public String getCodigo_compra() {
        return codigo_compra;
    }

    public void setCodigo_compra(String codigo_compra) {
        this.codigo_compra = codigo_compra;
    }

    public String getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(String fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public String getCodigo_empleado() {
        return codigo_empleado;
    }

    public void setCodigo_empleado(String codigo_empleado) {
        this.codigo_empleado = codigo_empleado;
    }

    public String getCodigo_transaccion() {
        return codigo_transaccion;
    }

    public void setCodigo_transaccion(String codigo_transaccion) {
        this.codigo_transaccion = codigo_transaccion;
    }

    public String getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public String getCodigo_proveedor() {
        return codigo_proveedor;
    }

    public void setCodigo_proveedor(String codigo_proveedor) {
        this.codigo_proveedor = codigo_proveedor;
    }

    public String getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(String cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }

    public String getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(String precio_compra) {
        this.precio_compra = precio_compra;
    }

    public String getTotal_compra() {
        return total_compra;
    }

    public void setTotal_compra(String total_compra) {
        this.total_compra = total_compra;
    }
}
