package com.example.crockpot.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crockpot.R;

public class ViewHolderPost extends RecyclerView.ViewHolder {
    ImageView postImage;
    TextView postContent;

    public ViewHolderPost(@NonNull View itemView) {
        super(itemView);
        postImage = itemView.findViewById(R.id.postImage);
        postContent = itemView.findViewById(R.id.postContent);
    }
}