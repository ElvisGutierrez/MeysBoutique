package com.example.meysboutique;

public class DatosClientes {
    private int codigoCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String duiCliente;
    private String telefonoCliente;
    private String nombreMunicipio;

    public DatosClientes(int codigoCliente, String nombreCliente, String apellidoCliente, String duiCliente, String telefonoCliente, String nombreMunicipio) {
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.duiCliente = duiCliente;
        this.telefonoCliente = telefonoCliente;
        this.nombreMunicipio = nombreMunicipio;
    }


    public int getCodigoCliente() {
        return codigoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public String getDuiCliente() {
        return duiCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }
}
