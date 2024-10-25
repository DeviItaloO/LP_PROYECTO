package com.example.notasrecordatorio.network.service;

import com.example.notasrecordatorio.network.dto.RecordatorioDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RecordatorioService {
    @POST("crear")
    Call<RecordatorioDTO> crearRecordatorio(@Body RecordatorioDTO recordatorioDTO);
}
