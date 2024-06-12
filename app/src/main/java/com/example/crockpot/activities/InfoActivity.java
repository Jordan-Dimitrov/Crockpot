package com.example.crockpot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crockpot.R;
import com.google.firebase.auth.FirebaseAuth;

public class InfoActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mAuth = FirebaseAuth.getInstance();

        Button btnShowSaved = findViewById(R.id.btnBack);
        Intent goToHomeActivity = new Intent(this, MainActivity.class);
        btnShowSaved.setOnClickListener(v -> {
            startActivity(goToHomeActivity);
        });

        TextView email = findViewById(R.id.editTextEmail2);
        email.setText(mAuth.getCurrentUser().getEmail());

    }
}