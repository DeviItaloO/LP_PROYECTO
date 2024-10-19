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

import com.example.notasrecordatorio.MainActivity;
import com.example.notasrecordatorio.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText email_edit_text = findViewById(R.id.login_correo);
        EditText contrasenia_edit_text = findViewById(R.id.login_contrasenia);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = email_edit_text.getText().toString();
                String contrasenia = contrasenia_edit_text.getText().toString();

                if(TextUtils.isEmpty(correo) || TextUtils.isEmpty(contrasenia))
                {
                    Toast.makeText(LoginActivity.this,
                                    "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                }
                if (correo.equals("italo@gmail.com") && contrasenia.equals("123456") ||
                    correo.equals("admin@gmail.com") && contrasenia.equals("123456") ||
                    correo.equals("julio@gmail.com") && contrasenia.equals("123456"))
                {
                    Toast.makeText(LoginActivity.this,
                                    "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                    String nombre = correo.substring(0, correo.indexOf("@"));

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("correo", correo);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this,
                                    "Credenciales Incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}