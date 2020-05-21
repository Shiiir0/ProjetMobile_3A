 package com.example.projetmobile_3a.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projetmobile_3a.R;
import com.example.projetmobile_3a.Singletons;
import com.example.projetmobile_3a.presentation.model.Character;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    private TextView textDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textDetail = findViewById(R.id.detail_txt);
        Intent intent = getIntent();
        String characterJson = intent.getStringExtra("DBKey");
        Character character = Singletons.getGson().fromJson(characterJson, Character.class);
        showDetail(character);

    }

    private void showDetail(Character character) {
        textDetail.setText(character.getName());
    }

}
