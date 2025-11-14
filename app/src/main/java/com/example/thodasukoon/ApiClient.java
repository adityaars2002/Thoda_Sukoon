package com.example.thodasukoon;

import java.util.concurrent.TimeUnit;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiClient {
    private static final String BASE_URL = "https://thoda-sukoon-server.onrender.com/";
    // Remove the static retrofit instance for the token-based client
    // private static Retrofit retrofit = null;

    // This method will now build a new client every time it's called.
    // This is necessary because the token can change.
    public static Retrofit getClient(String token) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(interceptor);
        clientBuilder.connectTimeout(60, TimeUnit.SECONDS);
        clientBuilder.readTimeout(60, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(60, TimeUnit.SECONDS);

        // Add Authorization header if token exists
        if (token != null && !token.isEmpty()) {
            clientBuilder.addInterceptor(chain -> chain.proceed(
                    chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build()
            ));
        }

        // Always build a new Retrofit instance for token-based requests.
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build();
    }

    // Keep the singleton for the non-token instance if you use it.
    private static Retrofit noTokenInstance = null;
    public static Retrofit getInstance() {
        if (noTokenInstance == null) {
            // ... (rest of the getInstance method is fine)
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();

            noTokenInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return noTokenInstance;
    }
}
