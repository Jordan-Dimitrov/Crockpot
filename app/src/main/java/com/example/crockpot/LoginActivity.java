package com.example.crockpot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        Button btnShowSaved = findViewById(R.id.go_to_register);
        Intent goToHomeActivity = new Intent(this, RegisterActivity.class);
        btnShowSaved.setOnClickListener(v -> {
            startActivity(goToHomeActivity);
        });

        Button btnLogin = findViewById(R.id.login);
        Intent goToHomeActivity2 = new Intent(this, MainActivity.class);
        EditText email = findViewById(R.id.editTextEmail);
        EditText pass = findViewById(R.id.editTextPassword);

        btnLogin.setOnClickListener(v -> {

            String emailStr = email.getText().toString();
            String passStr = pass.getText().toString();

            mAuth.signInWithEmailAndPassword(emailStr, passStr)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(goToHomeActivity2);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }
}
