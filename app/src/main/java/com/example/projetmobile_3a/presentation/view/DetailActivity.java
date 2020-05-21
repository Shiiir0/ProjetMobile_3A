 package com.example.projetmobile_3a.presentation.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projetmobile_3a.Constants;
import com.example.projetmobile_3a.R;
import com.example.projetmobile_3a.Singletons;
import com.example.projetmobile_3a.presentation.model.Character;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    private TextView textDetail;
    private ImageView imageDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageDetail = findViewById(R.id.image_Detail);
        textDetail = findViewById(R.id.detail_txt);
        Intent intent = getIntent();
        String characterJson = intent.getStringExtra("DBKey");
        Character character = Singletons.getGson().fromJson(characterJson, Character.class);
        showDetail(character);

    }

    @SuppressLint("SetTextI18n")
    private void showDetail(Character character) {
        if(character.getImage().charAt(0) == '.') {
            Glide.with(getApplicationContext()).load(Constants.BASE_URL + character.getImage()).into(imageDetail);
        }else Glide.with(getApplicationContext()).load(character.getImage()).into(imageDetail);

        textDetail.setText("\n \n \n Name : " + character.getName() +
                           "\n Gender : " + character.getGender() +
                           "\n Created : " + character.getCreated() +
                           "\n Origin : " + character.getOriginPlanet() +
                           "\n Series : " + character.getSeries() +
                           "\n Species : " + character.getSpecies() +
                           "\n Status : " + character.getStatus()
                            );
    }

}
