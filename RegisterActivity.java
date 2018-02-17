package com.example.noor.pushnotification;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ProgressBar progressBarRegister;
    private TextView tvRegister;
    private EditText etRegisterName;
    private EditText etRegisterEmail;
    private EditText etRegisterPassword;
    private Button btnRegister;
    private Button btnRegisterLogin;
    private CircleImageView circleProfileImage;
    private Uri imageUri;

    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageUri=null;
        mStorage = FirebaseStorage.getInstance().getReference().child("images");
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        this.progressBarRegister = findViewById(R.id.progressBarRegister);
        this.circleProfileImage = findViewById(R.id.circleProfileImage);
        this.btnRegisterLogin = findViewById(R.id.btnRegisterLogin);
        this.btnRegister = findViewById(R.id.btnRegister);
        this.etRegisterPassword = findViewById(R.id.etRegisterPassword);
        this.etRegisterEmail = findViewById(R.id.etRegisterEmail);
        this.etRegisterName = findViewById(R.id.etRegisterName);
        this.tvRegister = findViewById(R.id.tvRegister);

        btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        circleProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("iamge/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri!=null){
                    progressBarRegister.setVisibility(View.VISIBLE);
                    final String name=etRegisterName.getText().toString();
                    String email=etRegisterEmail.getText().toString();
                    String password=etRegisterPassword.getText().toString();

                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && ! TextUtils.isEmpty(password)){
                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull final Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    final String user_id=mAuth.getCurrentUser().getUid();
                                    StorageReference user_profile=mStorage.child(user_id+".jpg");
                                    user_profile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {
                                            if (uploadTask.isSuccessful()){
                                                String download_url=uploadTask.getResult().getDownloadUrl().toString();
                                                Map<String, Object> userMap=new HashMap<>();
                                                userMap.put("name",name);
                                                userMap.put("image", download_url);
                                                mFirestore.collection("users").document(user_id).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        progressBarRegister.setVisibility(View.INVISIBLE);
                                                        sendtoMainActivity();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressBarRegister.setVisibility(View.INVISIBLE);
                                                        Toast.makeText(RegisterActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }else {
                                                progressBarRegister.setVisibility(View.INVISIBLE);
                                                Toast.makeText(RegisterActivity.this, "Error: "+uploadTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else {
                                    progressBarRegister.setVisibility(View.INVISIBLE);
                                    Toast.makeText(RegisterActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }


                }
            }
        });
    }

    private void sendtoMainActivity() {
        Intent intentMainActivity=new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intentMainActivity);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE){
            imageUri=data.getData();
            circleProfileImage.setImageURI(imageUri);
        }
    }
}
