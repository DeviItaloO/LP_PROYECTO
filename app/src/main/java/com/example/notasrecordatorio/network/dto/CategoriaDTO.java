package com.example.notasrecordatorio.network.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class CategoriaDTO implements Serializable {
    private Long id;
    private String fechaCreacion;
    private String nombre;

    public CategoriaDTO(Long id, String nombre) {
        this.id = id;
        this.fechaCreacion = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
