package com.example.projetmobile_3a;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DBApi {
    @GET("api/character")
    Call<List<Character>> getRestDragonBallResponse();

}
