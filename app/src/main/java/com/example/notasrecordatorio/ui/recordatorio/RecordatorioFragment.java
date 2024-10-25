package com.example.notasrecordatorio.ui.recordatorio;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.Toast;

import com.example.notasrecordatorio.Enum.BaseUrlEnum;
import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.databinding.FragmentRecordatorioBinding;
import com.example.notasrecordatorio.network.Notification.AlarmReceiver;
import com.example.notasrecordatorio.network.cliente.ApiClient;
import com.example.notasrecordatorio.network.dto.NotaDTO;
import com.example.notasrecordatorio.network.dto.RecordatorioDTO;
import com.example.notasrecordatorio.network.service.RecordatorioService;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordatorioFragment extends Fragment {

    private FragmentRecordatorioBinding binding;
    private EditText tituloEditText, descripcionEditText;
    private Button crearRecordatorioButton;
    private TextClock textClock;
    private long idNota;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecordatorioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tituloEditText = root.findViewById(R.id.tituloEditText);
        descripcionEditText = root.findViewById(R.id.descripcionEditText);
        crearRecordatorioButton = root.findViewById(R.id.crearRecordatorioButton);
        textClock = root.findViewById(R.id.textClock);
        //textClock.setEditable(true);
        // Obtén el ID de la nota de los argumentos del Fragment
        if (getArguments() != null) {
            idNota = getArguments().getLong("idNota");
        }

        crearRecordatorioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String titulo = tituloEditText.getText().toString();
            String descripcion = descripcionEditText.getText().toString();
            NotaDTO notaDTO = new NotaDTO(idNota=1);

            String timeString = textClock.getText().toString();

                Calendar calendar = Calendar.getInstance();
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    Date date = dateFormat.parse(timeString);
                    calendar.setTime(date);
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Error al analizar la hora", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Formatea la fecha y hora para el RecordatorioDTO
                SimpleDateFormat recordatorioDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                String fechaRecordatorio = recordatorioDateFormat.format(calendar.getTime());

                RecordatorioDTO recordatorioDTO = new RecordatorioDTO(titulo, descripcion, fechaRecordatorio, "Pendiente", notaDTO);
                Log.d("recordatorioDTO ", new GsonBuilder().setPrettyPrinting().create().toJson(new RecordatorioDTO(titulo, descripcion, fechaRecordatorio, "Pendiente", notaDTO)));
                crearRecordatorio(recordatorioDTO);
            }
        });

        return root;
    }

    private void crearRecordatorio(RecordatorioDTO recordatorioDTO) {
        RecordatorioService recordatorioService = ApiClient.getRetrofit(BaseUrlEnum.BASE_URL_RECORDATORIO).create(RecordatorioService.class);
        Call<RecordatorioDTO> call = recordatorioService.crearRecordatorio(recordatorioDTO);

        call.enqueue(new Callback<RecordatorioDTO>() {
            @Override
            public void onResponse(Call<RecordatorioDTO> call, Response<RecordatorioDTO> response) {
                try {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Recordatorio creado exitosamente", Toast.LENGTH_SHORT).show();

                        // Programar la alarma
                        programarAlarma(recordatorioDTO.getFechaRecordatorio());

                    } else {
                        throw new Exception("Error en la respuesta: " + response.code());
                    }
                }catch (Exception e){
                    Log.e("RecordatorioFragment", "Error al crear el recordatorio: " + response.code() + "error: " + e);
                }
            }
            @Override
            public void onFailure(Call<RecordatorioDTO> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("ScheduleExactAlarm")
    private void programarAlarma(String fechaRecordatorio) {
        try {
            // 1. Parsear la fecha del recordatorio
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            Date fechaRecordatorioDate = dateFormat.parse(fechaRecordatorio);

            // 2. Crear un Calendar con la fecha del recordatorio
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaRecordatorioDate);

            // 3. Crear un Intent para la notificación
            Intent notificationIntent = new Intent(requireContext(), AlarmReceiver.class);
            notificationIntent.putExtra("titulo", tituloEditText.getText().toString()); // O el título que desees
            notificationIntent.putExtra("descripcion", descripcionEditText.getText().toString()); // O la descripción que desees

            // 4. Crear un PendingIntent para la notificación
            PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            // 5. Programar la notificación con AlarmManager
            AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
            Log.d("RecordatorioFragment", "Alarma programada para: " + calendar.getTimeInMillis());
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Error al analizar la fecha del recordatorio", Toast.LENGTH_SHORT).show();
        }
    }

    // ... (onDestroyView) ...

}