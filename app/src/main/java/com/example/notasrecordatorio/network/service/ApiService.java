package com.example.notasrecordatorio.network.service;

import com.example.notasrecordatorio.network.dto.LoginDTO;
import com.example.notasrecordatorio.network.dto.UsuarioDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("login")
    Call<UsuarioDTO> loginUsuario(@Body LoginDTO loginDTO);
    @GET("buscar")
    Call<List<UsuarioDTO>> listarUsuarios();
    @POST("crear")
    Call<UsuarioDTO> registrarUsuario(@Body UsuarioDTO usuarioDTO);
    @PUT("actualizar/{id}")
    Call<UsuarioDTO> actualizarUsuario(@Path("id") Long id, @Body UsuarioDTO usuarioDTO);
    @DELETE("eliminar/{id}")
    Call<Void> eliminarUsuario(@Path("id") Long id);

}