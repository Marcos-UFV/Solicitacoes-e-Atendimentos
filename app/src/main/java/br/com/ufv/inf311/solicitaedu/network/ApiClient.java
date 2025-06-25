package br.com.ufv.inf311.solicitaedu.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://crmufvgrupo4.apprubeus.com.br";
    private static Retrofit retrofit = null;
    public static Retrofit getClientRx() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
