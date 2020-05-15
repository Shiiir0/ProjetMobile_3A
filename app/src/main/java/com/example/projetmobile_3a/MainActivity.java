package com.example.projetmobile_3a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences SharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //creer le stockage cache
        SharedPreferences = getSharedPreferences("application_esiea", Context.MODE_PRIVATE);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Character> CharacterList = getDataFromCache();
        if(CharacterList != null) {
            showList(CharacterList);
        }else makeApiCall();

    }

    private List<Character> getDataFromCache() {
        String jsonCharacter = SharedPreferences.getString("jsonCharacterList", null);

        if(jsonCharacter == null) {
            return null;
        }else {
            Type listType = new TypeToken<List<Character>>() {}.getType();
            return gson.fromJson(jsonCharacter, listType);
        }

    }

    private void showList(List<Character> listCharacter) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



            // define an adapter


        listAdapter mAdapter = new listAdapter(listCharacter);
        recyclerView.setAdapter(mAdapter);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static final String BASE_URL = "https://dragon-ball-api.herokuapp.com/";

    private void makeApiCall() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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
                    showList(CharacterList);
                    //Toast.makeText(getApplicationContext(), "API Success", Toast.LENGTH_SHORT).show();
                }else{
                    ShowError();
                }
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                ShowError();
            }
        });
    }

    private void saveList(List<Character> CharacterList) {
        String jsonString = gson.toJson(CharacterList);
        SharedPreferences
                .edit()

                .putString("jsonCharacterList", jsonString)
                .apply();

        Toast.makeText(getApplicationContext(), "List saved", Toast.LENGTH_SHORT).show();
    }

    private void ShowError() {
        Toast.makeText(getApplicationContext(), "Api Error", Toast.LENGTH_SHORT).show();
    }

}
