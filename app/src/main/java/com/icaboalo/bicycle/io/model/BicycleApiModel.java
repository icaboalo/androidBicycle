package com.icaboalo.bicycle.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by icaboalo on 13/04/16.
 */
public class BicycleApiModel {

    public BicycleApiModel(String bicycleBrand, String bicycleModel, String bicycleTrack, String bicycleColor, String bicycleYear, String bicycleLatitude, String bicycleLongitude) {
        mBicycleBrand = bicycleBrand;
        mBicycleModel = bicycleModel;
        mBicycleTrack = bicycleTrack;
        mBicycleColor = bicycleColor;
        mBicycleYear = bicycleYear;
        mBicycleLatitude = bicycleLatitude;
        mBicycleLongitude = bicycleLongitude;
    }

    @SerializedName("id")
    int mBicycleId;

    @SerializedName("brand")
    String mBicycleBrand;

    @SerializedName("model")
    String mBicycleModel;

    @SerializedName("track")
    String mBicycleTrack;

    @SerializedName("color")
    String mBicycleColor;

    @SerializedName("year")
    String mBicycleYear;

    @SerializedName("latitude")
    String mBicycleLatitude;

    @SerializedName("longitude")
    String mBicycleLongitude;

    public int getBicycleId() {
        return mBicycleId;
    }

    public String getBicycleBrand() {
        return mBicycleBrand;
    }

    public String getBicycleModel() {
        return mBicycleModel;
    }

    public String getBicycleTrack() {
        return mBicycleTrack;
    }

    public String getBicycleColor() {
        return mBicycleColor;
    }

    public String getBicycleYear() {
        return mBicycleYear;
    }

    public String getBicycleLatitude() {
        return mBicycleLatitude;
    }

    public String getBicycleLongitude() {
        return mBicycleLongitude;
    }
}
