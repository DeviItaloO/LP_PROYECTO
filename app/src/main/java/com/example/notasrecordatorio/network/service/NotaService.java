package com.example.notasrecordatorio.network.service;

import com.example.notasrecordatorio.network.dto.NotaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NotaService {
    // Obtener todas las notas
    @GET("buscar")
    Call<List<NotaDTO>> obtenerNotas();

    // Crear una nueva nota
    @POST("crear")
    Call<NotaDTO> crearNota(@Body NotaDTO nota);
    @PUT("actualizar/{id}")
    Call<NotaDTO> actualizarNota(@Path("id")Long id, @Body NotaDTO notaDTO);


    // Eliminar una nota por fecha
    @DELETE("notas/{fecha}")
    Call<Void> eliminarNota(@Path("fecha") String fecha);
}
