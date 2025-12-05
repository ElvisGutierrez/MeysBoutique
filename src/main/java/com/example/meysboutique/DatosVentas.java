package com.example.meysboutique;

import java.math.BigDecimal;
import java.sql.Date;

public class DatosVentas {

    int codigoVenta;
    Date fechaVenta;
    String nombreCliente;
    String nombreEmpleado;
    String nombreProducto;
    String tipoTransaccion;
    int cantidadProducto;
    BigDecimal totalVenta;


    public DatosVentas(int codigoVenta, Date fechaVenta, String nombreCliente, String nombreEmpleado,  String tipoTransaccion, String nombreProducto, int cantidadProducto, BigDecimal totalVenta) {
        this.codigoVenta = codigoVenta;
        this.fechaVenta = fechaVenta;
        this.nombreCliente = nombreCliente;
        this.nombreEmpleado = nombreEmpleado;
        this.tipoTransaccion = tipoTransaccion;
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
        this.totalVenta = totalVenta;
    }


    public int getCodigoVenta() {
        return codigoVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }
}
