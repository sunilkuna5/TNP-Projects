package com.sample.directions.directionssample.Presenter.Retrofit;

import com.sample.directions.directionssample.Model.RoutesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by macosx on 20/07/2017 AD.
 */

public interface RoutesApiService {

    @GET("/maps/api/directions/json")
    Call<RoutesModel> getRoutesModel(@Query("origin") String origin,
                                     @Query("destination") String destination,
                                     @Query("departure_time") long departure_time,
                                     @Query("traffic_model") String traffic_model,
                                     @Query("key") String key,
                                     @Query("alternatives") boolean alternatives
    );
}
