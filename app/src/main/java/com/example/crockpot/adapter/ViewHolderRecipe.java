package com.example.crockpot.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crockpot.R;
import com.example.crockpot.models.StatsDto;
import com.squareup.picasso.Picasso;

public class ViewHolderRecipe extends RecyclerView.ViewHolder {

    public ViewHolderRecipe(@NonNull View itemView) {
        super(itemView);
        sprite = itemView.findViewById(R.id.charImg);
        name = itemView.findViewById(R.id.txtName);
        type = itemView.findViewById(R.id.txtType);
        spoils = itemView.findViewById(R.id.txtSpoils);
        cookingTime = itemView.findViewById(R.id.txtCookingTime);
        sideEffect = itemView.findViewById(R.id.txtSideEffect);
        sanity = itemView.findViewById(R.id.txtSanity);
        hunger = itemView.findViewById(R.id.txtHunger);
        health = itemView.findViewById(R.id.txtHealth);
        button = itemView.findViewById(R.id.btnSaveCharacter);
    }

    public void setRecipeFontSprite(String url){
        Picasso.get().load(url).into(sprite);
    }

    public ImageView sprite;
    public TextView name;
    public TextView type;
    public TextView spoils;
    public TextView cookingTime;
    public TextView sideEffect;
    public TextView sanity;
    public TextView hunger;
    public TextView health;
    public Button button;
}
