package com.icaboalo.bicycle.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by icaboalo on 13/04/16.
 */
public class TokenApiModel {

    public TokenApiModel(String username, String password) {
        mUsername = username;
        mPassword = password;
    }

    @SerializedName("token")
    String mToken;

    @SerializedName("username")
    String mUsername;

    @SerializedName("password")
    String mPassword;

    public String getToken() {
        return mToken;
    }
}
