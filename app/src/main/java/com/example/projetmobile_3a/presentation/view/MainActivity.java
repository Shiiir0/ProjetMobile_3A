package com.example.projetmobile_3a.presentation.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.projetmobile_3a.R;

import com.example.projetmobile_3a.Singletons;
import com.example.projetmobile_3a.presentation.controller.MainController;
import com.example.projetmobile_3a.presentation.model.Character;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new MainController(this, Singletons.getGson(), Singletons.getSharedPreferencesInstance(getApplicationContext()));
        controller.onStart();

    }

    public void showList(List<Character> listCharacter) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



            // define an adapter


        listAdapter mAdapter = new listAdapter(listCharacter, new listAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Character item) {
                controller.onItemClick(item);
            }
        });
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

    public void ShowError() {
        Toast.makeText(getApplicationContext(), "Api Error", Toast.LENGTH_SHORT).show();
    }

    public void navigateToDetails(Character character) {
        Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
        myIntent.putExtra("DBKey", Singletons.getGson().toJson(character));
        MainActivity.this.startActivity(myIntent);

    }
}
