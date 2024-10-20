package com.example.notasrecordatorio.ui.registro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.network.cliente.ApiClient;
import com.example.notasrecordatorio.network.dto.UsuarioDTO;
import com.example.notasrecordatorio.network.service.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoFragment extends Fragment {

    private RecyclerView recyclerView;
    private UsuarioAdapter usuarioAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado, container, false);
        recyclerView = view.findViewById(R.id.rvUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadUsuarios();
        return view;
    }

    private void loadUsuarios() {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<List<UsuarioDTO>> call = apiService.listarUsuarios();
        call.enqueue(new Callback<List<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<List<UsuarioDTO>> call, Response<List<UsuarioDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usuarioAdapter = new UsuarioAdapter(response.body());
                    recyclerView.setAdapter(usuarioAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<UsuarioDTO>> call, Throwable t) {
                // Manejo del error
            }
        });
    }
}

