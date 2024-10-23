package com.example.notasrecordatorio.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.databinding.FragmentHomeBinding;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String selectedDate;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHomeMotivational;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // CalendarView para seleccionar la fecha
        CalendarView calendarView = binding.calendarView;
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Obtén una instancia de Calendar con la fecha seleccionada
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth); // Configura el año, mes y día

            // Formatea la fecha al formato deseado
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            selectedDate = dateFormat.format(calendar.getTime());

            // Mostrar el botón de confirmación
            binding.buttonConfirmAction.setVisibility(View.VISIBLE);
        });

        // Manejo del botón de confirmación
        binding.buttonConfirmAction.setOnClickListener(v -> {
            // Mostrar el layout de acciones
            binding.recordatorioActions.setVisibility(View.VISIBLE);

            binding.btnCreateNote.setVisibility(View.VISIBLE);
            binding.btnEditNote.setVisibility(View.VISIBLE);
            binding.btnDeleteNote.setVisibility(View.VISIBLE);
            binding.btnListNotes.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Acción confirmada para la fecha: " + selectedDate, Toast.LENGTH_SHORT).show();
        });

        // Manejo de acciones de botones de recordatorio
        binding.btnCreateNote.setOnClickListener(v -> {
            showConfirmationToast("Crear Nota");

            Bundle bundle = new Bundle();
            bundle.putString("fechaSeleccionada", selectedDate);

            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_notas, bundle);

        });

        binding.btnEditNote.setOnClickListener(v -> {
            showConfirmationToast("Editar Nota");
            // fragmento de editar nota
        });

        binding.btnDeleteNote.setOnClickListener(v -> {
            showConfirmationToast("Eliminar Nota");
            // fragmento de eliminar nota
        });

        binding.btnListNotes.setOnClickListener(v -> {
            showConfirmationToast("Listar Notas");
            // fragmento de listar notas
        });

        return root;
    }

    private void showConfirmationToast(String action) {
        Toast.makeText(getContext(), action + " para la fecha: " + selectedDate, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}