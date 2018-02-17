package com.example.noor.pushnotification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private android.widget.TextView tvWelcome;
    private android.widget.EditText etLoginName;
    private android.widget.EditText etLoginPassword;
    private android.widget.Button btnLogin;
    private android.widget.Button btnLoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.btnLoginRegister = (Button) findViewById(R.id.btnLoginRegister);
        this.btnLogin = (Button) findViewById(R.id.btnLogin);
        this.etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        this.etLoginName = (EditText) findViewById(R.id.etLoginName);
        this.tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
    }
}
