package com.example.crockpot.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crockpot.R;
import com.example.crockpot.adapter.RecyclerViewPost;
import com.example.crockpot.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PostsActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private List<Post> postList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        postList = new ArrayList<Post>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Button btnInfo = findViewById(R.id.btnBack);
        Intent gotoInfo = new Intent(this, MainActivity.class);
        btnInfo.setOnClickListener(v -> {
            startActivity(gotoInfo);
        });

        loadAllPosts();
    }

    private void loadAllPosts(){
        db.collectionGroup("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            postList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Post post = document.toObject(Post.class);
                                postList.add(post);
                            }
                            RecyclerViewPost postAdapter = new RecyclerViewPost(postList);
                            recyclerView.setAdapter(postAdapter);
                            Log.d(TAG, "Posts loaded successfully");
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void loadPosts() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            db.collection("userz")
                    .document(user.getUid())
                    .collection("posts")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Post post = document.toObject(Post.class);
                                    postList.add(post);
                                }
                            }
                            RecyclerViewPost postAdapter = new RecyclerViewPost(postList);
                            recyclerView.setAdapter(postAdapter);
                            Log.w(TAG, "AAA");

                        }
                    });
        }
    }
}