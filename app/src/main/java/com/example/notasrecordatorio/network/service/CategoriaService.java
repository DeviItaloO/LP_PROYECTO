package com.example.notasrecordatorio.network.service;

import com.example.notasrecordatorio.network.dto.CategoriaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoriaService {
    @GET("buscar")
    Call<List<CategoriaDTO>> listarCategoria();
    @POST("crear")
    Call<CategoriaDTO> registrarCategoria(@Body CategoriaDTO categoriaDTO);
    @PUT("actualizar/{id}")
    Call<CategoriaDTO> actualizarCategoria(@Path("id") Long id, @Body CategoriaDTO categoriaDTO);
    @DELETE("eliminar/{id}")
    Call<Void> eliminarCategoria(@Path("id") Long id);
}
