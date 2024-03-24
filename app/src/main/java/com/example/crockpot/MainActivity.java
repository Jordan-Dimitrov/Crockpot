package com.example.crockpot;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.crockpot.models.RecipesResponse;
import com.example.crockpot.web.CrockpotApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://dont-starve-together-api.xyz/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CrockpotApiClient starWarsApiClient = retrofit.create(CrockpotApiClient.class);

        Call<RecipesResponse> call = starWarsApiClient.getRecipesPaged(1);
        call.enqueue(new Callback<RecipesResponse>() {
            @Override
            public void onResponse(Call<RecipesResponse> call, Response<RecipesResponse> response) {
                if (response.isSuccessful()) {
                    RecipesResponse swResponse = response.body();
                    Log.d(TAG, "Response: " + new Gson().toJson(swResponse));
                    Log.d(TAG, "Response: " + swResponse.getResults());
                } else {
                    Log.e(TAG, "Failed to get cat facts: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RecipesResponse> call, Throwable t) {
                Log.e(TAG, "Error getting sw response: " + t.getMessage());
            }
        });

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}