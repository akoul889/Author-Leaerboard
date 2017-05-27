package com.quintype.autholeaderboards;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeaderboardApiClient {

    private static final String TAG = LeaderboardApiClient.class.getName();
    private static AnalyticsApiService analyticsApiService;
    private static ApiService apiService;

    public static AnalyticsApiService getAnalyticsApiService() {
        if (analyticsApiService == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

//        TimberLoggingInterceptor timberLoggingInterceptor =  new TimberLoggingInterceptor();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://prod-analytics.qlitics.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
            analyticsApiService = retrofit.create(AnalyticsApiService.class);
        }
        return analyticsApiService;
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

//        TimberLoggingInterceptor timberLoggingInterceptor =  new TimberLoggingInterceptor();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.thequint.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
