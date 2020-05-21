package com.example.projetmobile_3a.presentation.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.projetmobile_3a.Constants;
import com.example.projetmobile_3a.data.DBApi;
import com.example.projetmobile_3a.presentation.model.Character;
import com.example.projetmobile_3a.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;


    public MainController(MainActivity view, Gson gson, SharedPreferences sharedPreferences) {
        this.view = view;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;

    }

    public void onStart() {

        List<Character> CharacterList = getDataFromCache();
        if(CharacterList != null) {
            view.showList(CharacterList);
        }else makeApiCall();
    }

    private void makeApiCall() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        DBApi DBAPI = retrofit.create(DBApi.class);

        Call<List<Character>> call = DBAPI.getRestDragonBallResponse();
        call.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                if(response.isSuccessful() && response.body() != null) {

                    List<Character> CharacterList = response.body();
                    saveList(CharacterList);
                    view.showList(CharacterList);
                    //Toast.makeText(getApplicationContext(), "API Success", Toast.LENGTH_SHORT).show();
                }else{
                    view.ShowError();
                }
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                view.ShowError();
            }
        });
    }

    private void saveList(List<Character> CharacterList) {
        String jsonString = gson.toJson(CharacterList);
        sharedPreferences
                .edit()
                .putString("jsonCharacterList", jsonString)
                .apply();

        Toast.makeText(view.getApplicationContext(), "List saved", Toast.LENGTH_SHORT).show();
    }

    private List<Character> getDataFromCache() {
        String jsonCharacter = sharedPreferences.getString("jsonCharacterList", null);

        if(jsonCharacter == null) {
            return null;
        }else {
            Type listType = new TypeToken<List<Character>>() {}.getType();
            return gson.fromJson(jsonCharacter, listType);
        }

    }

    public void onItemClick(Character character) {

    }

    public void onButtonAClick() {

    }

    public void onButtonBClick() {

    }

}
