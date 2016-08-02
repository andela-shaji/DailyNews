package com.andela.suada.dailynews.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andela.suada.dailynews.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUserActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btSignup,btSignIn;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_user);
        getSupportActionBar().hide();

        // Firebase authentication instance
        firebaseAuth = FirebaseAuth.getInstance();

        // initialization
        etEmail = (EditText) findViewById(R.id.edEmailAddress);
        etPassword = (EditText) findViewById(R.id.edPassword);
        btSignup = (Button) findViewById(R.id.btSignup);
        btSignIn = (Button) findViewById(R.id.btSignIn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterUserActivity.this, RegisterUserActivity.this.getString(R.string.empty_email), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterUserActivity.this, RegisterUserActivity.this.getString(R.string.empty_passwprd), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(RegisterUserActivity.this, RegisterUserActivity.this.getString(R.string.minimum_password), Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterUserActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterUserActivity.this, RegisterUserActivity.this.getString(R.string.auth_failed) + task.getException(), Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(RegisterUserActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterUserActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
