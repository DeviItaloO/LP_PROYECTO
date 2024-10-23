package com.example.notasrecordatorio.ui.notas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notasrecordatorio.Enum.BaseUrlEnum;
import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.network.cliente.ApiClient;
import com.example.notasrecordatorio.network.dto.NotaDTO;
import com.example.notasrecordatorio.network.dto.UsuarioDTO;
import com.example.notasrecordatorio.network.service.NotaService;
import com.example.notasrecordatorio.network.utils.SessionUsuario;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotasFragment extends Fragment {

    private EditText etTitulo;
    private EditText etDescripcion;
    private Spinner spinnerEstado, spinnerCategoria;
    private Button btnGuardarNota;
    private String fechaSeleccionada;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fechaSeleccionada = getArguments().getString("fechaSeleccionada");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notas, container, false); // Reemplaza con tu layout

        // Obtener referencias a las vistas
        etTitulo = view.findViewById(R.id.et_titulo);
        etDescripcion = view.findViewById(R.id.et_descripcion);
        spinnerEstado = view.findViewById(R.id.spinner_estado);
        btnGuardarNota = view.findViewById(R.id.btn_guardar_nota);
        spinnerCategoria = view.findViewById(R.id.spinner_categoria);

        btnGuardarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = etTitulo.getText().toString();
                String descripcion = etDescripcion.getText().toString();
                String estado = spinnerEstado.getSelectedItem().toString();

                if (fechaSeleccionada == null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    fechaSeleccionada = dateFormat.format(Calendar.getInstance().getTime());
                }

                int posicionCategoria = spinnerCategoria.getSelectedItemPosition();
                String[] categoriasIds = getResources().getStringArray(R.array.categorias_nota_ids);

                // Obtener el ID del usuario de SessionUsuario
                SessionUsuario sessionUsuario = new SessionUsuario(getActivity());
                UsuarioDTO currentUser = sessionUsuario.getUsuarioDTO();
                Long idUsuario = currentUser.getId();
                Long idCategoria = Long.parseLong(categoriasIds[posicionCategoria]);
                String fechaRecordatorio = fechaSeleccionada;

                NotaDTO notaDTO = new NotaDTO(titulo, descripcion, fechaRecordatorio, estado, idUsuario, idCategoria);

                if (currentUser != null) {
                    Long usuarioId = currentUser.getId();
                    notaDTO.setIdUsuario(usuarioId);

                    crearNota(notaDTO);
                } else {
                    // Manejar el caso en que el usuario no esté logueado (redirigir al login, mostrar un mensaje, etc.)
                    Toast.makeText(getActivity(), "Debes iniciar sesión para crear una nota", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void crearNota(NotaDTO notaDTO) {
        try {
            NotaService notaService = ApiClient.getRetrofit(BaseUrlEnum.BASE_URL_NOTAS).create(NotaService.class);
            Call<NotaDTO> call = notaService.crearNota(notaDTO);

            call.enqueue(new Callback<NotaDTO>() {
                @Override
                public void onResponse(Call<NotaDTO> call, Response<NotaDTO> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Nota creada exitosamente", Toast.LENGTH_SHORT).show();
                        // Navegar a otra pantalla o actualizar la lista de notas
                    } else {
                        Toast.makeText(getActivity(), "Error al crear la nota", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NotaDTO> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}