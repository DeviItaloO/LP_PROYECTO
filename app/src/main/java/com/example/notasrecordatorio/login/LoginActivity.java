package com.example.notasrecordatorio.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notasrecordatorio.Enum.BaseUrlEnum;
import com.example.notasrecordatorio.MainActivity;
import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.network.cliente.ApiClient;
import com.example.notasrecordatorio.network.dto.LoginDTO;
import com.example.notasrecordatorio.network.dto.UsuarioDTO;
import com.example.notasrecordatorio.network.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_login);

            EditText email_edit_text = findViewById(R.id.et_login_correo);
            EditText contrasenia_edit_text = findViewById(R.id.et_login_contrasenia);
            Button btnLogin = findViewById(R.id.btn_Login);
            Button btnRegistrar = findViewById(R.id.btn_registrar);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String correo = email_edit_text.getText().toString();
                    String contrasenia = contrasenia_edit_text.getText().toString();

                    if(TextUtils.isEmpty(correo) || TextUtils.isEmpty(contrasenia))
                    {
                        Toast.makeText(LoginActivity.this,
                                "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    }else {
                        loginUsuario(correo, contrasenia);
                    }
                }
            });

            btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                    startActivity(intent);
                }

            });
        }catch (Exception e){
            Toast.makeText(LoginActivity.this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void loginUsuario(String correo, String contrasenia) {
        try {
            LoginDTO loginDTO = new LoginDTO(correo, contrasenia);
            ApiService apiService = ApiClient.getRetrofit(BaseUrlEnum.BASE_URL_USUARIO).create(ApiService.class);
            Call<UsuarioDTO> call = apiService.loginUsuario(loginDTO);
            call.enqueue(new Callback<UsuarioDTO>() {
                @Override
                public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        String nombre = response.body().getNombre();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("nombre", nombre);
                        intent.putExtra("correo", correo);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Credenciales Incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}