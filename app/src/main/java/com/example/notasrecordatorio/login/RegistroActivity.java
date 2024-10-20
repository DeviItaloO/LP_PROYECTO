package com.example.notasrecordatorio.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import android.util.Log;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.network.cliente.ApiClient;
import com.example.notasrecordatorio.network.service.ApiService;
import com.example.notasrecordatorio.network.dto.UsuarioDTO;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText nombreEditText, emailEditText, contraseniaEditText;
    private Button registrarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_registro);

        nombreEditText = findViewById(R.id.registro_nombre);
        emailEditText = findViewById(R.id.registro_email);
        contraseniaEditText = findViewById(R.id.registro_contrasenia);
        registrarButton = findViewById(R.id.btn_registrar_usuario);

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String contrasenia = contraseniaEditText.getText().toString();

                if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(email) || TextUtils.isEmpty(contrasenia)) {
                    Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    registrarUsuario(nombre, email, contrasenia);
                }
            }
        });
    }
    private void registrarUsuario (String nombre, String email, String contrasenia){
        try {
            UsuarioDTO usuarioDTO = new UsuarioDTO(nombre, email, contrasenia);
            Log.d("RegistroActivity", "UsuarioDTO: " + new Gson().toJson(usuarioDTO));
            ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
            Log.d("RegistroActivity", "Base URL: " + ApiClient.getRetrofit().baseUrl().toString());
            Call<UsuarioDTO> call = apiService.registrarUsuario(usuarioDTO);
            call.enqueue(new Callback<UsuarioDTO>() {
                @Override
                public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                    Log.d("RegistroActivity", "Response Code: " + response.code());
                    Log.d("RegistroActivity", "Response Body: " + response.body());
                    if (response.isSuccessful()) {
                        Log.d("RegistroActivity", "Usuario registrado exitosamente");
                        Toast.makeText(RegistroActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e("RegistroActivity", "Error al registrar usuario: " + response.errorBody().toString());
                        Toast.makeText(RegistroActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                    Log.e("RegistroActivity", "Error en la conexión: " + t.getMessage(), t);
                    Toast.makeText(RegistroActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("RegistroActivity", "Exception in registrarUsuario", e);
            Toast.makeText(RegistroActivity.this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}