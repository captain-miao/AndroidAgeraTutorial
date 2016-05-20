package com.github.captain_miao.agera.tutorial.http;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceFactory {
    public static String BASE_URL = "https://api.github.com";
    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @param baseUrl REST baseUrl url
     * @return retrofit service with defined endpoint
     */
    public static <T> T createService(final Class<T> clazz, final String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit.create(clazz);
    }

    public static <T> T createService(final Class<T> clazz) {

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build();

        return retrofit.create(clazz);
    }
}
