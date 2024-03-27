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
import com.example.crockpot.models.RecipeDto;

import java.util.List;

public class RecyclerViewRecipeDto extends RecyclerView.Adapter<ViewHolderRecipe> {
    public RecipeManager recipeManager;
    private List<RecipeDto> recipeDtoList;
    public RecyclerViewRecipeDto(List<RecipeDto> recipeDtoList, RecipeManager recipeManager){
        this.recipeManager = recipeManager;
        this.recipeDtoList = recipeDtoList;
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
        RecipeDto recipeDto = recipeDtoList.get(position);
        holder.setRecipeFontSprite(recipeDto.getAsset());

        holder.name.setText(recipeDto.getName());
        holder.health.setText(recipeDto.getStats().getHealth() + "");
        holder.sanity.setText(recipeDto.getStats().getSanity() + "");
        holder.hunger.setText(recipeDto.getStats().getHunger() + "");
        holder.sideEffect.setText(recipeDto.getSideEffect());
        holder.cookingTime.setText(recipeDto.getCookingTime());
        holder.spoils.setText(recipeDto.getSpoils());
        holder.type.setText(recipeDto.getType());

        holder.button.setOnClickListener(v -> {
            Log.w(TAG, "BUTTON SAVE IS CLICKED FOR " + recipeDto.getName());
            recipeManager.insertRecipe(recipeDto);
        });
    }

    @Override
    public int getItemCount() {
        return recipeDtoList.size();
    }
}
