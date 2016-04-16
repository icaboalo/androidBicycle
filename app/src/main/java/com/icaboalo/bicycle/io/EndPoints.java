package com.icaboalo.bicycle.io;

/**
 * Created by icaboalo on 13/04/16.
 */
public class EndPoints {
    public static final String BASE_URL = "https://bicycle-network.herokuapp.com/api/";
    public static final String PATH_VERSION = "v1/";

    //    USER PATHS
    //Path for to obtain user token
    public static final String PATH_TOKEN = "token-auth/";

    //    BICYCLE PATHS
    //Path to get list of bicycles
    public static final String PATH_BICYCLE = "bicycle/";


    //Path to get specific object
    public static final String PATH_ID = "{id}/";

    public static final String ENDPOINT = BASE_URL + PATH_VERSION;

}
