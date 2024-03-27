package com.example.crockpot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crockpot.adapter.RecyclerViewRecipe;
import com.example.crockpot.db.Recipe;

import java.util.List;

public class Favorites extends AppCompatActivity {
    private RecyclerView recyclerView;
    private void setAdapter() {
        RecyclerViewRecipe recyclerViewRecipe = new RecyclerViewRecipe(MainActivity.recipeManager);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewRecipe);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.recyclerViewFavorites);
        Button btnBack = findViewById(R.id.btnBack);

        Intent goBack = new Intent(this, MainActivity.class);

        btnBack.setOnClickListener(v -> {
            startActivity(goBack);
        });

        setAdapter();
    }
}