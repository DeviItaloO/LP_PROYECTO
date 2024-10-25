package com.example.notasrecordatorio.network.cliente;

import static com.example.notasrecordatorio.network.cliente.security.IpHelper.PUBLIC_IP;
import com.example.notasrecordatorio.Enum.BaseUrlEnum;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit getRetrofit(BaseUrlEnum baseUrlEnum) {
        String baseUrl;
        String publicIp = PUBLIC_IP;
        if (baseUrlEnum.getValue() == 1) {
            baseUrl = "http://"+ PUBLIC_IP +":8080/api/usuario/";
        } else if (baseUrlEnum.getValue() == 2) {
            baseUrl = "http://"+ PUBLIC_IP +":8080/api/nota/";
        } else if (baseUrlEnum.getValue() == 3) {
            baseUrl = "http://"+ PUBLIC_IP +":8080/api/categoria/";
        } else if (baseUrlEnum.getValue() == 4) {
            baseUrl = "http://"+ PUBLIC_IP +":8080/api/recordatorio/";
        }else if (baseUrlEnum.getValue() == 5) {
            baseUrl = "http://"+ PUBLIC_IP +":8080/api/comentario/";
        }else {
            throw new IllegalArgumentException("La URL base no es válida: " + baseUrlEnum);
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
