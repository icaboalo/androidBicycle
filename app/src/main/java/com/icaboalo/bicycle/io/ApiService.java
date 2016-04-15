package com.icaboalo.bicycle.io;

import com.icaboalo.bicycle.io.model.BicycleApiModel;
import com.icaboalo.bicycle.io.model.TokenApiModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by icaboalo on 13/04/16.
 */
public interface ApiService {

    @POST(EndPoints.PATH_TOKEN)
    Call<TokenApiModel> getToken(@Body TokenApiModel tokenApiModel);

//    GET bicycle list
    @GET(EndPoints.PATH_BICYCLE)
    Call<ArrayList<BicycleApiModel>> getBicycleList(@Header("Authorization")String token);

//    POST a new bicycle
    @POST(EndPoints.PATH_BICYCLE)
    Call<BicycleApiModel> postBicycle(@Header("Authorization")String token, @Body BicycleApiModel bicycle);

}
