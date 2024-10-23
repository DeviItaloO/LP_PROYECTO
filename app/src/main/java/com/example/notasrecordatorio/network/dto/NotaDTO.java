package com.example.notasrecordatorio.network.dto;

public class NotaDTO {
    private String titulo;
    private String descripcion;
    private String fechaRecordatorio;
    private String estado;
    private String fechaCreacion;

    public NotaDTO(String titulo, String descripcion, String fechaRecordatorio, String estado, String fechaCreacion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaRecordatorio = fechaRecordatorio;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
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
}
