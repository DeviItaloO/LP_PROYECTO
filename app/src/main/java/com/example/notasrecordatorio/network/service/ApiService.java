package com.example.notasrecordatorio.network.service;

import com.example.notasrecordatorio.network.dto.UsuarioDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("registrar")
    Call<UsuarioDTO> registrarUsuario(@Body UsuarioDTO usuarioDTO);
}
