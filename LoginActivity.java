package com.example.noor.pushnotification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private EditText etLoginUsername;
    private EditText etLoginPassword;
    private Button btnLogin;
    private Button btnLoginRegister;
    private ProgressBar progressBarLogin;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        this.progressBarLogin = findViewById(R.id.progressBarLogin);
        this.btnLoginRegister = findViewById(R.id.btnLoginRegister);
        this.btnLogin = findViewById(R.id.btnLogin);
        this.etLoginPassword = findViewById(R.id.etLoginPassword);
        this.etLoginUsername = findViewById(R.id.etLoginUsername);
        this.tvWelcome = findViewById(R.id.tvWelcome);

        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentRegister=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=etLoginUsername.getText().toString();
                String password=etLoginPassword.getText().toString();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                    progressBarLogin.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                sendToMainActivity();
                                progressBarLogin.setVisibility(View.INVISIBLE);
                            }else {
                                Toast.makeText(LoginActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBarLogin.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                }else {
                    Toast.makeText(LoginActivity.this, "Error: Please Fill All Field", Toast.LENGTH_SHORT).show();
                    progressBarLogin.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void sendToMainActivity() {
        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intentMain);
        finish();
    }
}
