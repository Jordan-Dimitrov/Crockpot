package com.example.crockpot.adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crockpot.R;
import com.example.crockpot.RecipeManager;
import com.example.crockpot.db.Recipe;
import com.example.crockpot.models.RecipeDto;

import java.util.List;

public class RecyclerViewRecipe extends RecyclerView.Adapter<ViewHolderRecipe> {
    public RecipeManager recipeManager;
    public List<Recipe> recipes;
    public RecyclerViewRecipe(RecipeManager recipeManager){
        this.recipeManager = recipeManager;
        recipes = recipeManager.getRecipes();
    }
    @NonNull
    @Override
    public ViewHolderRecipe onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_info_layout, parent, false);
        return new ViewHolderRecipe(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecipe holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.setRecipeFontSprite(recipe.getAsset());

        holder.name.setText(recipe.getName());
        holder.health.setText(recipe.getHealth() + "");
        holder.sanity.setText(recipe.getSanity() + "");
        holder.hunger.setText(recipe.getHunger() + "");
        holder.sideEffect.setText(recipe.getSideEffect());
        holder.cookingTime.setText(recipe.getCookingTime().name());
        holder.spoils.setText(recipe.getSpoils().name());
        holder.type.setText(recipe.getType().name());
        holder.button.setText("Delete");

        holder.button.setOnClickListener(v -> {
            recipeManager.deleteRecipe(recipe);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
