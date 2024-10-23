package com.example.notasrecordatorio.ui.usuario;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notasrecordatorio.Enum.BaseUrlEnum;
import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.network.cliente.ApiClient;
import com.example.notasrecordatorio.network.dto.UsuarioDTO;
import com.example.notasrecordatorio.network.interfaz.usuario.IUsuarioActualizar;
import com.example.notasrecordatorio.network.interfaz.usuario.IUsuarioEliminar;
import com.example.notasrecordatorio.network.service.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoFragment extends Fragment implements IUsuarioActualizar, IUsuarioEliminar {

    private RecyclerView recyclerView;
    private ListadoAdapter listadoAdapter;

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
        ApiService apiService = ApiClient.getRetrofit(BaseUrlEnum.BASE_URL_USUARIO).create(ApiService.class);
        Call<List<UsuarioDTO>> call = apiService.listarUsuarios();
        call.enqueue(new Callback<List<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<List<UsuarioDTO>> call, Response<List<UsuarioDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listadoAdapter = new ListadoAdapter(response.body());
                    listadoAdapter.setActualizar(ListadoFragment.this);
                    listadoAdapter.setEliminar(ListadoFragment.this);
                    recyclerView.setAdapter(listadoAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<UsuarioDTO>> call, Throwable t) {
                // Manejo del error
            }
        });
    }

    @Override
    public void actualizar(UsuarioDTO usuarioDTO) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuarioDTO", usuarioDTO);

        Navigation.findNavController(requireView()).navigate(R.id.nav_actualizo, bundle);
    }

    @Override
    public void eliminar(Long id) {
        EliminadoFragment eliminadoFragment = EliminadoFragment.newInstance(id);
        eliminadoFragment.show(getParentFragmentManager(), "eliminadoFragment");
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume(){
        super.onResume();
        loadUsuarios();
        if (listadoAdapter != null) {
            listadoAdapter.notifyDataSetChanged();
        }
    }
}

