package com.icaboalo.bicycle.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by icaboalo on 13/04/16.
 */
public class BicycleApiModel {

    @SerializedName("id")
    int mBicycleId;

    @SerializedName("brand")
    String mBicycleBrand;
}
