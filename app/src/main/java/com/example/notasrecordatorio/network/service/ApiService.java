package com.example.notasrecordatorio.network.service;

import com.example.notasrecordatorio.network.dto.LoginDTO;
import com.example.notasrecordatorio.network.dto.UsuarioDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("crear")
    Call<UsuarioDTO> registrarUsuario(@Body UsuarioDTO usuarioDTO);
    @POST("login")
    Call<UsuarioDTO> loginUsuario(@Body LoginDTO loginDTO);
    @GET("buscar")
    Call<List<UsuarioDTO>> listarUsuarios();
}
