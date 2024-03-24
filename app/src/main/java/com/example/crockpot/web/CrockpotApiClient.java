package com.example.crockpot.web;

import com.example.crockpot.models.RecipesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CrockpotApiClient {
    @GET("crockpot-recipes")
    Call<RecipesResponse> getRecipesPaged(@Query("page") int page);
}
