package com.example.savoraapp.ui.gallery;
public class Cita {
    private String idCita;
    private String fecha;
    private String nombreCliente;
    private String telefonoCliente;
    private String correoCliente;
    private String numeroPersonas;
    private String preferencias;
    private String notas;
    private String uidUsuario;

    public Cita() {}
    public Cita(String idCita, String fecha, String nombreCliente, String telefonoCliente, String correoCliente, String numeroPersonas, String preferencias, String notas, String uidUsuario) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.nombreCliente = nombreCliente;
        this.telefonoCliente = telefonoCliente;
        this.correoCliente = correoCliente;
        this.numeroPersonas = numeroPersonas;
        this.preferencias = preferencias;
        this.notas = notas;
        this.uidUsuario = uidUsuario;
    }

    public String getIdCita() { return idCita; }
    public void setIdCita(String idCita) { this.idCita = idCita; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getTelefonoCliente() { return telefonoCliente; }
    public void setTelefonoCliente(String telefonoCliente) { this.telefonoCliente = telefonoCliente; }

    public String getCorreoCliente() { return correoCliente; }
    public void setCorreoCliente(String correoCliente) { this.correoCliente = correoCliente; }

    public String getNumeroPersonas() { return numeroPersonas; }
    public void setNumeroPersonas(String numeroPersonas) { this.numeroPersonas = numeroPersonas; }

    public String getPreferencias() { return preferencias; }
    public void setPreferencias(String preferencias) { this.preferencias = preferencias; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public String getUidUsuario() { return uidUsuario; }
    public void setUidUsuario(String uidUsuario) { this.uidUsuario = uidUsuario; }
}
