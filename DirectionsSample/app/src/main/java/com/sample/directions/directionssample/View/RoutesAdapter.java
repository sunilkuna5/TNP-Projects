package com.sample.directions.directionssample.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.directions.directionssample.Model.Route;
import com.sample.directions.directionssample.Presenter.RoutesPresenter;
import com.sample.directions.directionssample.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macosx on 20/07/2017 AD.
 */

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.MyViewHolder> {

    private List<Route> routesList;
    private RoutesPresenter routesPresenter;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView time;
        public TextView distance;
        int position;

        public void setPosition(int position) {
            this.position = position;
        }

        public MyViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.time);
            distance = view.findViewById(R.id.distance);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            routesPresenter.routeClicked(routesList,position);
        }
    }


    public RoutesAdapter(List<Route> routesList, RoutesPresenter routesPresenter) {
        this.routesList = routesList;
        this.routesPresenter = routesPresenter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Route route = routesList.get(position);
        holder.time.setText(route.getLegs().get(0).getDuration().getText());
        holder.distance.setText(route.getLegs().get(0).getDistance().getText());
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return routesList.size();
    }
}