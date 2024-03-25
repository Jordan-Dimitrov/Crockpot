package com.example.crockpot.models;

public class StatsDto {
    private int sanity;
    private int hunger;
    private int health;

    public StatsDto(int sanity, int hunger, int health) {
        this.sanity = sanity;
        this.hunger = hunger;
        this.health = health;
    }
    public int getSanity(){
        return sanity;
    }
    public void setSanity(int value){
        sanity = value;
    }
    public int getHunger(){
        return hunger;
    }
    public void setHunger(int value){
        hunger = value;
    }
    public int getHealth(){
        return health;
    }
    public void setHealth(int value){
        health = value;
    }
}
