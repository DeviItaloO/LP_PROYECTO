package com.example.notasrecordatorio.network.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RecordatorioDTO implements Serializable {
    private Long id;
    private String titulo;
    private String descripcion;
    private String fechaRecordatorio;
    private String estado;
    private NotaDTO idNota;

    public RecordatorioDTO(String titulo, String descripcion, String fechaRecordatorio, String estado, NotaDTO idNota) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaRecordatorio = fechaRecordatorio;
        this.estado = estado;
        this.idNota = idNota;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public NotaDTO getIdNota() {
        return idNota;
    }
    public void setIdNota(NotaDTO idNota) {
        this.idNota = idNota;
    }
}
