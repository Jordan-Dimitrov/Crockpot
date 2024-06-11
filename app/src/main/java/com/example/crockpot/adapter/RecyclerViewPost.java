package com.example.crockpot.adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crockpot.R;
import com.example.crockpot.models.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Context;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewPost extends RecyclerView.Adapter<ViewHolderPost> {
    private List<Post> postList;
    private Context context;
    public RecyclerViewPost(List<Post> postList) {
        this.postList = postList;
    }
    @NonNull
    @Override
    public ViewHolderPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new ViewHolderPost(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPost holder, int position) {
        Post post = postList.get(position);
        holder.postContent.setText(post.getContent());

        StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("images/" + post.getImageName());
        Log.w(TAG, "IMAGER " + imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                Log.d("Image", imageUrl);
                Picasso.get().load(imageUrl).into(holder.postImage);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
