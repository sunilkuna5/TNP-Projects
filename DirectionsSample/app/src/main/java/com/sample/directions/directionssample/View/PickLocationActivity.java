package com.sample.directions.directionssample.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.patloew.rxlocation.RxLocation;
import com.sample.directions.directionssample.Model.LocationInfo;
import com.sample.directions.directionssample.Presenter.PickLocationPresenter;
import com.sample.directions.directionssample.Presenter.ViewControllers.PickLocationView;
import com.sample.directions.directionssample.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickLocationActivity extends AppCompatActivity implements PickLocationView, OnMapReadyCallback {

    private static final String TAG = PickLocationActivity.class.getSimpleName();
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.textSwitcher)
    TextSwitcher textSwitcher;

    @BindView(R.id.searchBox)
    EditText searchBox;

    @BindView(R.id.address)
    TextView address;

    PickLocationPresenter presenter;

    GoogleMap googleMap;
    Marker marker;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    int PLACE_PICKER_REQUEST = 0;

    private RxLocation rxLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_location);
        rxLocation = new RxLocation(this);
        rxLocation.setDefaultTimeout(15000, TimeUnit.MILLISECONDS);
        if(savedInstanceState == null)
            presenter = PickLocationPresenter.newInstance(this,rxLocation);
        else
            presenter = PickLocationPresenter.getInstance(this,rxLocation);
        ButterKnife.bind(this);
        init();

    }

    private void init() {

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(PickLocationActivity.this);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(24);
                myText.setBackgroundColor(Color.GRAY);
                myText.setTypeface(null, Typeface.BOLD_ITALIC);
                myText.setTextColor(Color.BLUE);
                return myText;
            }
        });

        setSwitcherText();
        Animation in = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);

        // set the animation type of textSwitcher
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);

        searchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlacesActivity();
            }
        });
    }

    private void setSwitcherText() {
        if(presenter.isSourceConfirmed())
            textSwitcher.setText(getString(R.string.askDestination));
        else
            textSwitcher.setText(getString(R.string.askSource));
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void askForDestination() {
        setSwitcherText();
        initiateMap();
    }

    @Override
    public void askForSource() {
        setSwitcherText();
        initiateMap();
    }

    @Override
    public void startRouteActivity() {
        Intent intent = new Intent(PickLocationActivity.this,RoutesActivity.class);
        intent.putExtra(getString(R.string.source),presenter.getSource());
        intent.putExtra(getString(R.string.destination),presenter.getDestination());
        startActivity(intent);
        finish();
    }

    @Override
    public void updateAddress(String s) {
        address.setText(s);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        initiateMap();
    }

    private void initiateMap() {
        googleMap.clear();
        marker = googleMap.addMarker(new MarkerOptions().position(googleMap.getCameraPosition().target));
        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                marker.setPosition(googleMap.getCameraPosition().target);
                Location location = presenter.getLocation(marker.getPosition(),"MarkerAddress");
                presenter.getAddress(location);
            }
        });

        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                marker.setPosition(googleMap.getCameraPosition().target);
                address.setText(getString(R.string.loading));
            }
        });

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(0,0),0,0,0)));
    }

    @OnClick(R.id.locationOk)
    void onLocationOk(){
        presenter.gotLocation();
    }


    @OnClick(R.id.googlePlaces)
    void getFromGooglePlaces(){

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    void openPlacesActivity(){
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Location location = presenter.getLocation(place.getLatLng(),place.getName().toString());
                LocationInfo locationInfo = new LocationInfo(location,place.getAddress().toString());
                presenter.setLocationInfo(locationInfo);
                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }else if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this,data);
                Location location = presenter.getLocation(place.getLatLng(),place.getName().toString());
                LocationInfo locationInfo = new LocationInfo(location,place.getAddress().toString());
                presenter.setLocationInfo(locationInfo);
                Log.i(TAG, "Place: " + place.getName());
            }
        }
    }

}
