package com.example.crockpot;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.crockpot.db.AppDatabase;
import com.example.crockpot.db.Recipe;
import com.example.crockpot.db.RecipeDao;
import com.example.crockpot.enums.CookingTime;
import com.example.crockpot.enums.FoodType;
import com.example.crockpot.enums.Spoilage;
import com.example.crockpot.models.RecipeDto;
import com.example.crockpot.models.RecipesResponse;
import com.example.crockpot.web.CrockpotApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private int page = 1;
    public static AppDatabase appDatabase;
    private void loadRecipes(CrockpotApiClient crockpotApiClient, SharedPreferences.Editor editor){
        Call<RecipesResponse> call = crockpotApiClient.getRecipesPaged(page);
        call.enqueue(new Callback<RecipesResponse>() {
            @Override
            public void onResponse(Call<RecipesResponse> call, Response<RecipesResponse> response) {
                if (response.isSuccessful()) {
                    editor.putInt("currentPage", page);
                    editor.apply();
                    RecipesResponse swResponse = response.body();

                    for (RecipeDto element : swResponse.getResults()){
                        RecipeDao recipeDao = appDatabase.recipeDao();

                        Recipe recipe = new Recipe(element.getName(),
                                FoodType.valueOf(element.getType()),
                                Spoilage.valueOf(element.getSpoils()),
                                CookingTime.valueOf(element.getCookingTime()),
                                element.getAsset(),
                                element.getSideEffect(),
                                element.getStats().getSanity(),
                                element.getStats().getHunger(),
                                element.getStats().getHealth()
                                );
                        recipeDao.insertRecipe(recipe);
                        break;
                    }
                } else {
                    Log.e(TAG, "Failed to get cat facts: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RecipesResponse> call, Throwable t) {
                Log.e(TAG, "Error getting sw response: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "crockpot-new")
                .allowMainThreadQueries()
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://dont-starve-together-api.xyz/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CrockpotApiClient crockpotApiClient = retrofit.create(CrockpotApiClient.class);

        SharedPreferences sharedPreferences = getSharedPreferences("Pages", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int currPage = sharedPreferences.getInt("currentPage", 1);
        page = currPage;

        loadRecipes(crockpotApiClient, editor);

        RecipeDao dao = appDatabase.recipeDao();
        List<Recipe> existingCharacters = dao.getAll();
        Log.e(TAG, "Sisaadsadasdads: " + existingCharacters.size());

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