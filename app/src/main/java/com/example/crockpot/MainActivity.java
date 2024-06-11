package com.example.crockpot;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.crockpot.adapter.RecyclerViewRecipeDto;
import com.example.crockpot.db.AppDatabase;
import com.example.crockpot.models.Post;
import com.example.crockpot.models.RecipeDto;
import com.example.crockpot.models.RecipesResponse;
import com.example.crockpot.web.CrockpotApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private int page = 1;
    private static final String dbName = "crockpot-new";
    private static final String sharedPrefrencesKey = "currentPage";
    private static final String sharedPrefrencesName = "Pages";
    private static final String url = "https://dont-starve-together-api.xyz/api/";
    public static AppDatabase appDatabase;
    private static final int maxPages = 3;
    private RecyclerView recyclerView;
    public static RecipeManager recipeManager;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private void setAdapter(List<RecipeDto> recipeDtos){
        RecyclerViewRecipeDto viewRecipeDto = new RecyclerViewRecipeDto(recipeDtos, recipeManager);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewRecipeDto);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, dbName)
                .allowMainThreadQueries()
                .build();

        Button btnNext = findViewById(R.id.btnNext);
        Button btnPrev = findViewById(R.id.btnPrev);

        Button btnShowSaved = findViewById(R.id.btnShowSaved);
        Intent goToHomeActivity = new Intent(this, FavoritesActivity.class);
        btnShowSaved.setOnClickListener(v -> {
            startActivity(goToHomeActivity);
        });

        Button btnInfo = findViewById(R.id.btnInfo);
        Intent gotoInfo = new Intent(this, InfoActivity.class);
        btnInfo.setOnClickListener(v -> {
            startActivity(gotoInfo);
        });

        Button btnUpload = findViewById(R.id.btnUpload);
        Intent goToUpload = new Intent(this, UploadPostActivity.class);
        btnUpload.setOnClickListener(v -> {
            startActivity(goToUpload);
        });

        Button btnPosts = findViewById(R.id.btnPosts);
        Intent goToPosts = new Intent(this, PostsActivity.class);
        btnPosts.setOnClickListener(v -> {
            startActivity(goToPosts);
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CrockpotApiClient crockpotApiClient = retrofit.create(CrockpotApiClient.class);

        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefrencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int currPage = sharedPreferences.getInt(sharedPrefrencesKey, 1);
        page = currPage;

        recipeManager = new RecipeManager(appDatabase);

        Log.e(TAG, recipeManager.getRecipes().size() + "");

        getRecipeDtos(page, crockpotApiClient, editor);

        btnNext.setOnClickListener(v -> {
            if(page == maxPages){
                return;
            }
            page++;
            getRecipeDtos(page, crockpotApiClient, editor);
        });

        btnPrev.setOnClickListener(v -> {
            if (page == 1) {
                return;
            }
            page--;
            getRecipeDtos(page, crockpotApiClient, editor);
        });

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void getPosts(){
        db.collection("userz").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String userId = document.getId();
                            Map<String, Object> userData = document.getData();
                            String userName = userData.get("email").toString();

                            db.collection("userz").document(userId).collection("posts").get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (QueryDocumentSnapshot postDocument : queryDocumentSnapshots) {
                                                Post post = postDocument.toObject(Post.class);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void getRecipeDtos(int page, CrockpotApiClient crockpotApiClient, SharedPreferences.Editor editor)
    {
        crockpotApiClient.getRecipesPaged(page).enqueue(new Callback<RecipesResponse>() {
            @Override
            public void onResponse(Call<RecipesResponse> call, Response<RecipesResponse> response) {
                if (response.isSuccessful()) {
                    editor.putInt(sharedPrefrencesKey, page);
                    editor.apply();
                    RecipesResponse swResponse = response.body();
                    List<RecipeDto> recipeDtos = swResponse.getResults();
                    setAdapter(recipeDtos);
                } else {
                    Log.e(TAG, "Failed to get recipes: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RecipesResponse> call, Throwable t) {
                Log.e(TAG, "Error getting recipes response: " + t.getMessage());
            }
        });
    }
}