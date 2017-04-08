package com.xr45labs.uworkers.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xr45 on 2/04/17.
 */

public class RetrofitConnection {
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public DataInterface connectRetrofit (String service){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(service)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(DataInterface.class);

    }
}
