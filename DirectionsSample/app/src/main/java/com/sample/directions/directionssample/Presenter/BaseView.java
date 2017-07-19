package com.sample.directions.directionssample.Presenter;

import android.content.Context;

/**
 * Created by macosx on 19/07/2017 AD.
 */

public interface BaseView {

    void showProgress();
    void hideProgress();

    Context getContext();
}
