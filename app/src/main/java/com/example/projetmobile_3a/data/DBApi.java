package com.example.projetmobile_3a.data;

import com.example.projetmobile_3a.presentation.model.Character;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DBApi {
    @GET("api/character")
    Call<List<Character>> getRestDragonBallResponse();

}
