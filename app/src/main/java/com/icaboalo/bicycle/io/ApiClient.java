package com.icaboalo.bicycle.io;

import retrofit2.Retrofit;

/**
 * Created by icaboalo on 13/04/16.
 */
public class ApiClient {

    ApiService mApiService;

    public ApiService getApiService() {
        if (mApiService == null){
            Retrofit nRetrofit = new Retrofit.Builder()
                    .baseUrl(EndPoints.BASE_URL)
                    .build();
            mApiService = nRetrofit.create(ApiService.class);
        }
        return mApiService;
    }

}
