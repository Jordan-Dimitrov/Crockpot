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

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        EditText email = findViewById(R.id.editTextEmailReg);
        EditText pass1 = findViewById(R.id.editTextPasswordReg1);
        EditText pass2 = findViewById(R.id.editTextPasswordReg2);

        Button btnShowSaved = findViewById(R.id.go_to_login);
        Intent goToHomeActivity = new Intent(this, LoginActivity.class);
        btnShowSaved.setOnClickListener(v -> {
            startActivity(goToHomeActivity);
        });

        Button btnReg = findViewById(R.id.register);
        btnReg.setOnClickListener(v ->
        {
            String emailStr = email.getText().toString();
            String pass1Str = pass1.getText().toString();
            String pass2Str = pass2.getText().toString();

            if(pass1Str.equals(pass2Str) && pass1Str.isEmpty()){
                Toast.makeText(this, "Enter valid passwords", Toast.LENGTH_SHORT).show();
                return;
            }

            if(emailStr.isEmpty()){
                Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(emailStr, pass1Str)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(RegisterActivity.this, "Account created",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }

                        private void updateUI(FirebaseUser user) {

                        }
                    });
        });
    }
}