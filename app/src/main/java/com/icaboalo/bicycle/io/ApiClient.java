package com.icaboalo.bicycle.io;

import retrofit2.Retrofit;

/**
 * Created by icaboalo on 13/04/16.
 */
public class ApiClient {

    public static ApiService mApiService;

    public static ApiService getApiService() {
        if (mApiService == null){
            Retrofit nRetrofit = new Retrofit.Builder()
                    .baseUrl(EndPoints.BASE_URL)
                    .build();
            mApiService = nRetrofit.create(ApiService.class);
        }
        return mApiService;
    }

}
