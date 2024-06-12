package com.example.crockpot.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crockpot.R;
import com.example.crockpot.models.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UploadPostActivity extends AppCompatActivity {

    private ImageView postPic;
    public Uri ImageUri;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private EditText content;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        mAuth = FirebaseAuth.getInstance();

        postPic = findViewById(R.id.postPic);
        Button btnPost = findViewById(R.id.btnPostt);
        Button btnBack = findViewById(R.id.btnBack);
        content = findViewById(R.id.editTextPostt);

        Intent goToHomeActivity = new Intent(this, MainActivity.class);
        btnBack.setOnClickListener(v -> {
            startActivity(goToHomeActivity);
        });

        btnPost.setOnClickListener(v -> {
            choosePicture();
        });

    }

    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            ImageUri = data.getData();
            postPic.setImageURI(ImageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomKey);

        riversRef.putFile(ImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(UploadPostActivity.this, "Image uploaded successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadPostActivity.this, "Image upload failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        Post post = new Post(content.getText().toString(), randomKey);

        FirebaseUser user = mAuth.getCurrentUser();

        db.collection("userz")
                .document(user.getUid())
                .collection("posts")
                .document(post.getPostId())
                .set(post)
                .addOnSuccessListener(aVoid ->
                {
                    System.out.println("User successfully uploaded!");
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadPostActivity.this, "Failed to save post",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
