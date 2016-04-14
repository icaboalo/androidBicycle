package com.icaboalo.bicycle.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.icaboalo.bicycle.R;
import com.icaboalo.bicycle.io.model.BicycleApiModel;

import java.util.ArrayList;

/**
 * Created by icaboalo on 13/04/16.
 */
public class BicycleRecyclerAdapter extends RecyclerView.Adapter<BicycleRecyclerAdapter.BicycleViewHolder> {

    Context mContext;
    ArrayList<BicycleApiModel> mBicycleList;
    LayoutInflater mInflater;

    public BicycleRecyclerAdapter(Context context, ArrayList<BicycleApiModel> bicycleList) {
        mContext = context;
        mBicycleList = bicycleList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BicycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_bicycle_list, parent, false);
        BicycleViewHolder nViewHolder = new BicycleViewHolder(view, R.id.brand, R.id.model, R.id.track, R.id.color, R.id.year, R.id.maps_button);
        return nViewHolder;
    }

    @Override
    public void onBindViewHolder(BicycleViewHolder holder, int position) {
        BicycleApiModel bicycle = mBicycleList.get(position);
        holder.setBrand(bicycle.getBicycleBrand());
        holder.setModel(bicycle.getBicycleModel());
        holder.setColor(bicycle.getBicycleColor());
        holder.setTrack(bicycle.getBicycleTrack());
        holder.setYear(bicycle.getBicycleYear());
    }

    @Override
    public int getItemCount() {
        return mBicycleList.size();
    }

    class BicycleViewHolder extends RecyclerView.ViewHolder{

        TextView mBrand, mModel, mTrack, mColor, mYear;
        Button mMapsButton;

        public BicycleViewHolder(View itemView, int brandId, int modelId, int trackId, int colorId, int yearId, int mapsButtonId) {
            super(itemView);
            mBrand = (TextView) itemView.findViewById(brandId);
            mModel= (TextView) itemView.findViewById(modelId);
            mTrack = (TextView) itemView.findViewById(trackId);
            mColor = (TextView) itemView.findViewById(colorId);
            mYear = (TextView) itemView.findViewById(yearId);
            mMapsButton = (Button) itemView.findViewById(mapsButtonId);
        }

        public void setBrand(String brand) {
            mBrand.setText(brand);
        }

        public void setModel(String model) {
            mModel.setError(model);
        }

        public void setTrack(String track) {
            mTrack.setText(track);
        }

        public void setColor(String color) {
            mColor.setText(color);
        }

        public void setYear(String year) {
            mYear.setText(year);
        }
    }
}