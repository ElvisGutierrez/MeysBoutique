package com.example.meysboutique;

import java.math.BigDecimal;

public class DatosProductos {
    private int codigoProducto;
    private String nombreProducto;
    private String descripcion;
    private BigDecimal precio;
    private String nombreCategoria;
    private String nombreProveedor;
    private int stock;

    public DatosProductos(int codigoProducto, String nombreProducto, String descripcion, BigDecimal precio, String nombreCategoria, String nombreProveedor, int stock) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.nombreCategoria = nombreCategoria;
        this.nombreProveedor = nombreProveedor;
        this.stock = stock;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
