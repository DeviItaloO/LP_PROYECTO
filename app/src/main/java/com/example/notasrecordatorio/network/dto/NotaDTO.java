package com.example.notasrecordatorio.network.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotaDTO implements Serializable {
    private Long id;
    private String titulo;
    private String descripcion;
    private String fechaRecordatorio;
    private String estado;
    private UsuarioDTO usuario;
    private CategoriaDTO categoria;
    private String fechaCreacion;
    private Long idUsuario;
    private Long idCategoria;

    public NotaDTO(Long id){this.id = id;}

    public NotaDTO(Long id,String titulo, String descripcion, String fechaRecordatorio, String estado, Long idUsuario, Long idCategoria) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaRecordatorio = fechaRecordatorio;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
        this.fechaCreacion = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getFechaRecordatorio() {
        return fechaRecordatorio;
    }
    public void setFechaRecordatorio(String fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Long getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public UsuarioDTO getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
    public CategoriaDTO getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }
}
