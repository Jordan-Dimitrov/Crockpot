package com.example.crockpot;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crockpot.adapter.RecyclerViewRecipeDto;
import com.example.crockpot.db.AppDatabase;
import com.example.crockpot.db.Recipe;
import com.example.crockpot.db.RecipeDao;
import com.example.crockpot.enums.CookingTime;
import com.example.crockpot.enums.FoodType;
import com.example.crockpot.enums.Spoilage;
import com.example.crockpot.models.RecipeDto;
import com.example.crockpot.models.RecipesResponse;
import com.example.crockpot.web.CrockpotApiClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeManager
{
    private AppDatabase appDatabase;
    private CrockpotApiClient crockpotApiClient;
    private SharedPreferences.Editor editor;
    private RecipeDao recipeDao;

    public RecipeManager(AppDatabase appDatabase, CrockpotApiClient apiClient, SharedPreferences.Editor editor){
        this.appDatabase = appDatabase;
        this.editor = editor;
        this.crockpotApiClient = apiClient;
        recipeDao = appDatabase.recipeDao();
    }

    public void insertRecipe(RecipeDto recipeDto){
        Recipe recipe = new Recipe(recipeDto.getName(),
                FoodType.valueOf(recipeDto.getType()),
                Spoilage.valueOf(recipeDto.getSpoils()),
                CookingTime.valueOf(recipeDto.getCookingTime()),
                recipeDto.getAsset(),
                recipeDto.getSideEffect(),
                recipeDto.getStats().getSanity(),
                recipeDto.getStats().getHunger(),
                recipeDto.getStats().getHealth()
        );
        recipeDao.insertRecipe(recipe);
    }

    public List<Recipe> getRecipes(){
        return recipeDao.getAll();
    }

    public boolean deleteRecipe(Recipe recipe){
        List<Recipe> recipes = recipeDao.getAll();

        if(!recipes.contains(recipe)){
            return false;
        }

        recipeDao.deleteRecipe(recipe);

        return true;
    }

}
