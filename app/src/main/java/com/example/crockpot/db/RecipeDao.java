package com.example.crockpot.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert
    void insertRecipe(Recipe recipe);

    @Query(value = "SELECT * FROM recipe;")
    List<Recipe> getAll();

    @Query(value = "SELECT * FROM recipe WHERE name = :name;")
    List<Recipe> getByName(String name);

    @Query(value = "SELECT * FROM recipe ORDER BY spoils")
    List<Recipe> getBySpoilage();
}
