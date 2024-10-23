package com.example.notasrecordatorio.ui.usuario;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.notasrecordatorio.Enum.BaseUrlEnum;
import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.network.cliente.ApiClient;
import com.example.notasrecordatorio.network.dto.UsuarioDTO;
import com.example.notasrecordatorio.network.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActualizoFragment extends Fragment {
    private UsuarioDTO usuarioDTO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try{
            View view = inflater.inflate(R.layout.fragment_actualizo, container, false);
            EditText nombre_edit_text = view.findViewById(R.id.f_actualizo_nombre);
            EditText email_edit_text = view.findViewById(R.id.f_actualizo_email);
            EditText contrasenia_edit_text = view.findViewById(R.id.f_actualizo_contrasenia);
            Button btnActualizar = view.findViewById(R.id.f_btn_actualizar_usuario);

            if (getArguments() != null) {
                usuarioDTO = (UsuarioDTO) getArguments().getSerializable("usuarioDTO");
            }

            nombre_edit_text.setText(usuarioDTO.getNombre());
            email_edit_text.setText(usuarioDTO.getEmail());
            contrasenia_edit_text.setText(usuarioDTO.getContrasenia());

            btnActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombre = nombre_edit_text.getText().toString();
                    String email = email_edit_text.getText().toString();
                    String contrasenia = contrasenia_edit_text.getText().toString();

                    if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(email) || TextUtils.isEmpty(contrasenia)) {
                        Toast.makeText(getActivity(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    } else {
                        actualizarUsuario(nombre, email, contrasenia);
                    }
                }
            });
            return view;
        }catch(Exception e){
            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return new View(getActivity());//vista vacia
        }
    }

    private void actualizarUsuario(String nombre, String email, String contrasenia) {
        try {
            UsuarioDTO usuarioActualizado = new UsuarioDTO(nombre, email, contrasenia);
            ApiService apiService = ApiClient.getRetrofit(BaseUrlEnum.BASE_URL_USUARIO).create(ApiService.class);

            Call<UsuarioDTO> call = apiService.actualizarUsuario(usuarioDTO.getId(), usuarioActualizado);

            call.enqueue(new Callback<UsuarioDTO>() {
                @Override
                public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(requireView()).navigate(R.id.nav_listado);
                    } else {
                        Toast.makeText(getActivity(), "Error al actualizar usuario", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception e){
            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}