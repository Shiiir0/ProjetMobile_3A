package com.example.projetmobile_3a;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.projetmobile_3a.data.DBApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static DBApi DBApiInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson() {
        if(gsonInstance == null) {
            gsonInstance = new GsonBuilder()
                                .setLenient()
                                .create();
        }
        return gsonInstance;
    }

    public static DBApi getDBApi() {
        if(DBApiInstance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            DBApiInstance = retrofit.create(DBApi.class);
        }
        return DBApiInstance;
    }

    public static SharedPreferences getSharedPreferencesInstance(Context context) {
        if(sharedPreferencesInstance == null) {
            sharedPreferencesInstance = context.getSharedPreferences("application_esiea", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }

}
