package com.example.notasrecordatorio.network.cliente;

import com.example.notasrecordatorio.Enum.BaseUrlEnum;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit getRetrofit(BaseUrlEnum baseUrlEnum) {
        String baseUrl;

        if (baseUrlEnum.getValue() == 1) {
            baseUrl = "http://192.168.18.160:8080/api/usuario/"; /* coloca tu ip */
        } else if (baseUrlEnum.getValue() == 2) {
            baseUrl = "http://192.168.18.160:8080/api/nota/"; /* coloca tu ip */
        } else if (baseUrlEnum.getValue() == 3) {
            baseUrl = "http://192.168.18.160:8080/api/categoria/"; /* coloca tu ip */
        } else {
            throw new IllegalArgumentException("La URL base no es válida: " + baseUrlEnum);
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
