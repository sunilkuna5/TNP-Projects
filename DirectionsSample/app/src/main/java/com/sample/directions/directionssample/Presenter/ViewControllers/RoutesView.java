package com.sample.directions.directionssample.Presenter.ViewControllers;

import com.sample.directions.directionssample.Model.Route;

import java.util.List;

/**
 * Created by macosx on 20/07/2017 AD.
 */

public interface RoutesView  extends BaseView{


    void responseFailed();

    void setUpMap(Route route);

    void setUpRecyclerView(List<Route> routes);

}
