package com.example.notasrecordatorio.ui.registro;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.network.cliente.ApiClient;
import com.example.notasrecordatorio.network.service.ApiService;
import com.example.notasrecordatorio.network.dto.UsuarioDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroFragment extends Fragment {
    private EditText nombreEditText, emailEditText, contraseniaEditText;
    private Button registrarButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);
        nombreEditText = view.findViewById(R.id.f_registro_nombre);
        emailEditText = view.findViewById(R.id.f_registro_email);
        contraseniaEditText = view.findViewById(R.id.f_registro_contrasenia);
        registrarButton = view.findViewById(R.id.f_btn_registrar_usuario);

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String contrasenia = contraseniaEditText.getText().toString();

                if(TextUtils.isEmpty(nombre) || TextUtils.isEmpty(email) || TextUtils.isEmpty(contrasenia)) {
                    Toast.makeText(getActivity(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    registrarUsuario(nombre, email, contrasenia);
                }
            }
        });

        return view;
    }
    private void registrarUsuario(String nombre, String email, String contrasenia) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(nombre, email, contrasenia);

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<UsuarioDTO> call = apiService.registrarUsuario(usuarioDTO);

        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                    // Navegar a otra pantalla si es necesario
                } else {
                    Toast.makeText(getActivity(), "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                Toast.makeText(getActivity(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
