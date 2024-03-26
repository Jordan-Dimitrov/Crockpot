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
import com.example.crockpot.models.RecipeDto;
import com.example.crockpot.web.CrockpotApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private int page = 1;
    public static AppDatabase appDatabase;
    public RecipeManager recipeManager;
    public static void setAdapter(List<RecipeDto> recipeDtos){
        Log.e(TAG, recipeDtos.size() + " 11123");
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

        recipeManager = new RecipeManager(appDatabase, crockpotApiClient, editor);

        Log.e(TAG, recipeManager.getRecipes().size() + " ");

        recipeManager.getRecipeDtos(1);

        Log.e(TAG, " 1111 ");

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