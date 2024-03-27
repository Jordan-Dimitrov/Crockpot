package com.example.crockpot;

import com.example.crockpot.db.AppDatabase;
import com.example.crockpot.db.Recipe;
import com.example.crockpot.db.RecipeDao;
import com.example.crockpot.enums.CookingTime;
import com.example.crockpot.enums.FoodType;
import com.example.crockpot.enums.Spoilage;
import com.example.crockpot.models.RecipeDto;

import java.util.List;

public class RecipeManager
{
    private RecipeDao recipeDao;

    public RecipeManager(AppDatabase appDatabase){
        recipeDao = appDatabase.recipeDao();
    }

    public void insertRecipe(RecipeDto recipeDto){
        String cookingTimeValue = recipeDto.getCookingTime().replaceAll("\\s", "");
        String foodType = recipeDto.getType().replaceAll("\\s", "");
        String spoilage = recipeDto.getSpoils().replaceAll("\\s", "");

        Recipe recipe = new Recipe(recipeDto.getName(),
                FoodType.valueOf(foodType),
                Spoilage.valueOf(spoilage),
                CookingTime.valueOf(cookingTimeValue),
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
        recipeDao.deleteRecipe(recipe.getId());

        return true;
    }

    public boolean recipeIsPresent(String name){
        List<Recipe> recipes = recipeDao.getByName(name);

        if(recipes.size() == 0){
            return false;
        }

        return true;
    }

}
