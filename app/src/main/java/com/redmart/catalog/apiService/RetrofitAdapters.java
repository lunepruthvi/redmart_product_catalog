package com.redmart.catalog.apiService;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.redmart.catalog.BuildConfig;
import com.redmart.catalog.appconfig.AppConfig;
import com.redmart.catalog.utils.AppConstants;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAdapters {


    private static RedMartApi redMartApi;

    public static RedMartApi getWebServiceInterface() {
        if (redMartApi != null) {
            return redMartApi;
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(AppConstants.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getValidatedClient())
                .build();

        redMartApi = restAdapter.create(RedMartApi.class);
        return redMartApi;
    }


    private static OkHttpClient getValidatedClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
        }
        httpClient.readTimeout(AppConfig.DEFAULT_NETWORK_CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        httpClient.connectTimeout(AppConfig.DEFAULT_NETWORK_CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        return httpClient.build();
    }


}
