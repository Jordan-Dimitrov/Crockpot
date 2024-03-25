package com.example.crockpot.models;

public class RecipeDto {
    private String name;
    private String type;
    private String spoils;
    private String cookingTime;
    private String asset;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpoils() {
        return spoils;
    }

    public void setSpoils(String spoils) {
        this.spoils = spoils;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
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

    public StatsDto getStats() {
        return stats;
    }

    public void setStats(StatsDto statsDto) {
        this.stats = statsDto;
    }

    private String sideEffect;
    private StatsDto stats;

    public RecipeDto(String name, String type, String spoils, String cookingTime, String asset, String sideEffect, StatsDto statsDto) {
        this.name = name;
        this.type = type;
        this.spoils = spoils;
        this.cookingTime = cookingTime;
        this.asset = asset;
        this.sideEffect = sideEffect;
        this.stats = statsDto;
    }
}
