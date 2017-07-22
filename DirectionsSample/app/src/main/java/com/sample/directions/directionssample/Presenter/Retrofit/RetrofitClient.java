package com.sample.directions.directionssample.Presenter.Retrofit;

import android.content.Context;

import com.sample.directions.directionssample.Model.RoutesModel;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.Nullable;
import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;

/**
 * Created by macosx on 20/07/2017 AD.
 */

public class RetrofitClient {

    private static final String DIRECTIONS_URL = "https://maps.googleapis.com/";
    private static final long CONNECTION_TIMEOUT = 15000;
    private static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;

    /**
     * Get Retrofit Instance
     */
    public static Retrofit.Builder getRetrofitInstance(Context context) {
        return new Retrofit.Builder()
                .client(getOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static RoutesApiService getRoutesApiService(Retrofit.Builder retrofitBuilder) {
        return retrofitBuilder.baseUrl(DIRECTIONS_URL).build()
                .create(RoutesApiService.class);
    }
    private static okhttp3.OkHttpClient getOkHttpClient(Context context) {
        Interceptor headerAuthorizationInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Headers headers = request.headers().newBuilder().build();
                request = request.newBuilder().headers(headers).build();
                return chain.proceed(request);
            }
        };

        okhttp3.OkHttpClient.Builder okClientBuilder = new okhttp3.OkHttpClient.Builder();
//        okClientBuilder.addInterceptor(headerAuthorizationInterceptor);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BASIC);
        okClientBuilder.addInterceptor(httpLoggingInterceptor);
        final @Nullable File baseDir = context.getCacheDir();
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, "HttpResponseCache");
            okClientBuilder.cache(new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE));
        }
        okClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        okClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        okClientBuilder.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        return okClientBuilder.build();
    }
}
