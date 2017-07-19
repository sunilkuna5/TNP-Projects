package com.sample.directions.directionssample.Presenter;

import android.location.Address;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.patloew.rxlocation.RxLocation;
import com.sample.directions.directionssample.Model.LocationInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.SafeObserver;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by macosx on 19/07/2017 AD.
 */

public class PickLocationPresenter implements BasePresenter {


    CompositeDisposable disposable = new CompositeDisposable();
    PickLocationView pickLocationView;
    LocationInfo source,destination;
    static PickLocationPresenter pickLocationPresenter;
    RxLocation rxLocation;
    boolean confirmedSource = false;
    boolean confirmedDestination = false;

    private final String SRC = "Origin";
    private final String DEST = "Destination";

    public static synchronized PickLocationPresenter getInstance(PickLocationView pickLocationView,RxLocation rxLocation){
        if(pickLocationPresenter == null){
            pickLocationPresenter = new PickLocationPresenter(pickLocationView,rxLocation);
        }else {
            pickLocationPresenter.pickLocationView = pickLocationView;
            pickLocationPresenter.rxLocation = rxLocation;
        }
        return pickLocationPresenter;
    }

    private PickLocationPresenter(PickLocationView pickLocationView, RxLocation rxLocation){
        this.pickLocationView = pickLocationView;
        this.rxLocation = rxLocation;
    }

    private boolean isSourceAvailable(){
        return source!=null;
    }

    private boolean isDestinationAvailable(){
        return destination!=null;
    }

    public void gotLocation(){
        if(isSourceAvailable() && !isDestinationAvailable()){
            confirmedSource = true;
            pickLocationView.askForDestination();
        }else if(isSourceAvailable() && isDestinationAvailable()) {
            confirmedDestination = true;
            confirmedSource = true;
            pickLocationView.startRouteActivity();
        }else{
            confirmedSource = false;
            confirmedDestination = false;
            pickLocationView.askForSource();
        }
    }

    void startSearchForAddress(String address){

    }

    @Override
    public void onStop() {
        pickLocationView =null;
        if(!disposable.isDisposed())
            disposable.clear();
    }

    @Override
    public void onStart() {

    }


    public void getAddress(Location location){
        setSourceAndDestinationAddress(location);
        rxLocation.geocoding().fromLocation(location).toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Address>(){

                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    disposable.add(d);
                }

                @Override
                public void onNext(@NonNull Address address) {
                    StringBuffer addressText = new StringBuffer();
                    final int maxAddressLineIndex = address.getMaxAddressLineIndex();

                    for(int i=0; i<=Math.min(2,maxAddressLineIndex); i++) {
                        addressText.append(address.getAddressLine(i));
                        if(i != maxAddressLineIndex) { addressText.append("\n"); }
                    }
                    pickLocationView.setAddress(addressText.toString());
                    Log.d("Address",addressText.toString());
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            });
    }

    private void setSourceAndDestinationAddress(Location location) {
        if(confirmedSource)
            destination = new LocationInfo(location);
        else
            source = new LocationInfo(location);
    }

    public String getTitle() {
        if(confirmedSource)
            return DEST;
        else
            return SRC;
    }


    public boolean isSourceConfirmed() {
        return confirmedSource;
    }
}
