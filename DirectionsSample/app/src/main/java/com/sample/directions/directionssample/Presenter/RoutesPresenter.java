package com.sample.directions.directionssample.Presenter;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.patloew.rxlocation.RxLocation;
import com.sample.directions.directionssample.Model.LocationInfo;
import com.sample.directions.directionssample.Model.Route;
import com.sample.directions.directionssample.Model.RoutesModel;
import com.sample.directions.directionssample.Presenter.Retrofit.RetrofitClient;
import com.sample.directions.directionssample.Presenter.ViewControllers.RoutesView;
import com.sample.directions.directionssample.Presenter.ViewControllers.RoutesView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by macosx on 20/07/2017 AD.
 */

public class RoutesPresenter implements BasePresenter {

    private static final String TAG = RoutesPresenter.class.getSimpleName();
    private static RoutesPresenter routesPresenter;
    RoutesView routesView;

    public static synchronized RoutesPresenter getInstance(RoutesView routesView){
        if(routesPresenter == null){
            routesPresenter = new RoutesPresenter(routesView);
        }else {
            routesPresenter.routesView = routesView;
        }
        return routesPresenter;
    }

    private RoutesPresenter(RoutesView routesView){
        this.routesView = routesView;
    }


    @Override
    public void onStop() {
        
    }

    @Override
    public void onStart() {

    }

    public void getRoutes(LocationInfo source, LocationInfo destination, String key, Retrofit.Builder retrofit) {

        String origin = source.getLocation().getLatitude()+","+source.getLocation().getLongitude();
        String dest = destination.getLocation().getLatitude()+","+destination.getLocation().getLongitude();
        long time = new Date().getTime();
        String trafficModel = "best_guess";
        Log.d(TAG,"On getRoutes ");
        RetrofitClient.getRoutesApiService(retrofit).getRoutesModel(origin,dest,time,trafficModel,key,true).enqueue(
                new Callback<RoutesModel>() {
                    @Override
                    public void onResponse(Call<RoutesModel> call, Response<RoutesModel> response) {
                        Log.d(TAG,"On Response "+response.body());
                        Log.d(TAG,"On Response "+response.body().getRoutes());
                        Log.d(TAG,"On Response "+response.toString());
                        updateUI(response.body());
                    }

                    @Override
                    public void onFailure(Call<RoutesModel> call, Throwable t) {
                        Log.d(TAG,"On Response "+t.toString());
                        t.printStackTrace();
                    }
                }
        );
    }

    private void updateUI(RoutesModel routesModel) {
        if(routesModel.getStatus().equals("OK")){
            routesView.setUpRecyclerView(routesModel.getRoutes());
            routesView.setUpMap(routesModel.getRoutes().get(0));
        }else{
            routesView.responseFailed();
        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),(((double) lng / 1E5)));
//            Log.d(TAG,"Poly "+p);
            poly.add(p);
        }

        return poly;
    }


    public PolylineOptions getPolylineOptions(String encodedLatLngs)  {
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        List<LatLng> latLngs = decodePoly(encodedLatLngs);
//        Log.d(TAG,"LatLngs "+latLngs.toString());
        options.addAll(latLngs);
//        Log.d(TAG,"PolyOptions "+options.toString());
        return options;
    }

    public void routeClicked(List<Route> routesList, int adapterPosition) {
        routesView.setUpMap(routesList.get(adapterPosition));
    }
}
