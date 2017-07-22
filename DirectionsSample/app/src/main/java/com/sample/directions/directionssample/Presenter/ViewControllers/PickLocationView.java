package com.sample.directions.directionssample.Presenter.ViewControllers;

/**
 * Created by macosx on 19/07/2017 AD.
 */

public interface PickLocationView extends BaseView {

    void askForDestination();
    void askForSource();
    void startRouteActivity();
    void updateAddress(String s);
}
