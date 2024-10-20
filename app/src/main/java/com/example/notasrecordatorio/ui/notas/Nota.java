package com.example.notasrecordatorio.ui.notas;

import androidx.room.vo.Entity;

public class Nota {
    private String fecha;
    private String contenido;

    public Nota(String fecha, String contenido) {
        this.fecha = fecha;
        this.contenido = contenido;
    }

    // Getters y setters
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    @Override
    public String toString() {
        return "Fecha: " + fecha + ", Nota: " + contenido;
    }
}
