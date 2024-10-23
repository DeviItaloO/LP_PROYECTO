package com.example.notasrecordatorio.network.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.notasrecordatorio.network.dto.UsuarioDTO;
import com.google.gson.Gson;

public class SessionUsuario {
    private static final String SESSION_NOMBRE_USUARIO = "user_session";
    private static final String KEY_USUARIO = "user_data";

    private SharedPreferences information;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SessionUsuario(Context contexto) {
        information = contexto.getSharedPreferences(SESSION_NOMBRE_USUARIO, Context.MODE_PRIVATE);
        editor = information.edit();
        gson = new Gson();
    }

    public void saveUsuario(UsuarioDTO usuarioDTO) {
        String usuarioJson = gson.toJson(usuarioDTO);
        editor.putString(KEY_USUARIO, usuarioJson);
        editor.apply();
    }

    public UsuarioDTO getUsuarioDTO() {
        String usuarioJson = information.getString(KEY_USUARIO, null);
        if (usuarioJson != null) {
            return gson.fromJson(usuarioJson, UsuarioDTO.class);
        }
        return null;
    }
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
