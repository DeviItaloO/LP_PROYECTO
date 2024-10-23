package com.example.notasrecordatorio.network.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UsuarioDTO implements Serializable {
    private Long id;
    private String nombre;
    private String email;
    private String contrasenia;
    private String fechaCreacion;
    private List<NotaDTO> notas;

    public UsuarioDTO(String nombre, String email, String contrasenia) {
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.fechaCreacion = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getContrasenia() {
        return contrasenia;
    }
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public List<NotaDTO> getNotas() { return notas; }
    public void setNotas(List<NotaDTO> notas) { this.notas = notas; }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
