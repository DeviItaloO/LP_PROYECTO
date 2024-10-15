package com.example.notasrecordatorio.ui.notas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notasrecordatorio.databinding.FragmentNotasBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotasFragment extends Fragment {

    private FragmentNotasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotasViewModel notasViewModel =
                new ViewModelProvider(this).get(NotasViewModel.class);

        binding = FragmentNotasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotas;
        notasViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Recupera la fecha desde el Bundle
        Bundle fecha = getArguments();
        if (fecha != null) {
            String fechaSeleccionada = fecha.getString("fechaSeleccionada", "No seleccionaste una fecha");
            textView.setText("Fecha seleccionada: " + fechaSeleccionada);
        } else{
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String fechaHoy = formatoFecha.format(new Date());

            textView.setText("Fecha seleccionada: " + fechaHoy);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}