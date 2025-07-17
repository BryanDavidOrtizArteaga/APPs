package com.example.savoraapp.model; // O tu paquete correspondiente

import java.util.List;

public class Plato {
    private String nombre;
    private String descripcion;
    private List<String> ingredientes;
    private double precio;
    private int imagenResId;
    private String categoria;

    public Plato() {
    }

    public Plato(String nombre, String descripcion, List<String> ingredientes, double precio, int imagenResId, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes;
        this.precio = precio;
        this.imagenResId = imagenResId;
        this.categoria = categoria;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<String> getIngredientes() { return ingredientes; }
    public void setIngredientes(List<String> ingredientes) { this.ingredientes = ingredientes; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    // Getter y Setter para imagenResId
    public int getImagenResId() { return imagenResId; }
    public void setImagenResId(int imagenResId) { this.imagenResId = imagenResId; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}