package com.sample.directions.directionssample.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sample.directions.directionssample.Model.LocationInfo;
import com.sample.directions.directionssample.Model.Route;
import com.sample.directions.directionssample.Presenter.Retrofit.RetrofitClient;
import com.sample.directions.directionssample.Presenter.RoutesPresenter;
import com.sample.directions.directionssample.Presenter.ViewControllers.RoutesView;
import com.sample.directions.directionssample.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutesActivity extends AppCompatActivity implements OnMapReadyCallback, RoutesView {

    private static final String TAG = RoutesActivity.class.getSimpleName();
    GoogleMap googleMap;

    @BindView(R.id.routes_recycler_view)
    RecyclerView recyclerView;

    RoutesPresenter routesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        ButterKnife.bind(this);
        routesPresenter = RoutesPresenter.getInstance(this);
        init();
    }

    private void init() {
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LocationInfo source = getIntent().getParcelableExtra(getString(R.string.source));
        LocationInfo destination = getIntent().getParcelableExtra(getString(R.string.destination));
        Log.d(TAG,source.toString()+destination.toString());
        routesPresenter.getRoutes(source,destination,getString(R.string.google_maps_key), RetrofitClient.getRetrofitInstance(getBaseContext()));
    }
//08042840000
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void responseFailed() {

    }

    @Override
    public void setUpMap(Route route) {

        googleMap.clear();
        googleMap.addPolyline(routesPresenter.getPolylineOptions( route.getOverviewPolyline().getPoints()));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(route.getLegs().get(0).getStartLocation().getLat(),route.getLegs().get(0).getStartLocation().getLng())).icon(BitmapDescriptorFactory.fromResource(R.drawable.measle_blue)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(route.getLegs().get(0).getEndLocation().getLat(),route.getLegs().get(0).getEndLocation().getLng())));
        LatLng northEast = new LatLng(route.getBounds().getNortheast().getLat(),route.getBounds().getNortheast().getLng());
        LatLng southEast = new LatLng(route.getBounds().getSouthwest().getLat(),route.getBounds().getSouthwest().getLng());
        LatLngBounds latLngBounds = new LatLngBounds(southEast,northEast);
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (30 * scale + 0.5f);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds,pixels));
    }

    @Override
    public void setUpRecyclerView(List<Route> routes) {
        RoutesAdapter adapter = new RoutesAdapter(routes,routesPresenter);
        recyclerView.setAdapter(adapter);
    }
}