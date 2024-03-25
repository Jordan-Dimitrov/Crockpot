package com.example.crockpot.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.crockpot.enums.CookingTime;
import com.example.crockpot.enums.FoodType;
import com.example.crockpot.enums.Spoilage;

@Entity(tableName = "recipe")
public class Recipe {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    public Spoilage getSpoils() {
        return spoils;
    }

    public void setSpoils(Spoilage spoils) {
        this.spoils = spoils;
    }

    public CookingTime getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(CookingTime cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getSideEffect() {
        return sideEffect;
    }

    public void setSideEffect(String sideEffect) {
        this.sideEffect = sideEffect;
    }

    public int getSanity() {
        return sanity;
    }

    public void setSanity(int sanity) {
        this.sanity = sanity;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @PrimaryKey
    private Long id;
    private String name;
    private FoodType type;
    private Spoilage spoils;
    @ColumnInfo(name = "cooking_time")
    private CookingTime cookingTime;
    private String asset;

    public Recipe(String name, FoodType type, Spoilage spoils, CookingTime cookingTime, String asset, String sideEffect, int sanity, int hunger, int health) {
        this.name = name;
        this.type = type;
        this.spoils = spoils;
        this.cookingTime = cookingTime;
        this.asset = asset;
        this.sideEffect = sideEffect;
        this.sanity = sanity;
        this.hunger = hunger;
        this.health = health;
    }

    @ColumnInfo(name = "side_effect")
    private String sideEffect;
    private int sanity;
    private int hunger;
    private int health;
}
