package com.example.notasrecordatorio.ui.categoria;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.notasrecordatorio.Enum.BaseUrlEnum;
import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.databinding.FragmentCategoriaBinding;
import com.example.notasrecordatorio.network.cliente.ApiClient;
import com.example.notasrecordatorio.network.dto.CategoriaDTO;
import com.example.notasrecordatorio.network.service.CategoriaService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private FragmentCategoriaBinding binding;
    private EditText categoryNameEditText;
    private Button saveCategoryButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoriaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        categoryNameEditText = root.findViewById(R.id.categoryNameEditText);
        saveCategoryButton = root.findViewById(R.id.saveCategoryButton);

        saveCategoryButton.setOnClickListener(v -> {
            String categoryName = categoryNameEditText.getText().toString();
            if (!categoryName.isEmpty()) {
                saveCategory(categoryName);
            } else {
                Toast.makeText(getContext(), "Ingresa un nombre para la categoría", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void saveCategory(String nombreCategoria) {
        CategoriaService categoriaService = ApiClient.getRetrofit(BaseUrlEnum.BASE_URL_CATEGORIA).create(CategoriaService.class);
        CategoriaDTO categoriaDTO = new CategoriaDTO(null, nombreCategoria);
        Call<CategoriaDTO> call = categoriaService.registrarCategoria(categoriaDTO);

        call.enqueue(new Callback<CategoriaDTO>() {
            @Override
            public void onResponse(Call<CategoriaDTO> call, Response<CategoriaDTO> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Categoría creada exitosamente", Toast.LENGTH_SHORT).show();
                    categoryNameEditText.setText("");
                } else {
                    Toast.makeText(getContext(), "Error al crear la categoría", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoriaDTO> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}