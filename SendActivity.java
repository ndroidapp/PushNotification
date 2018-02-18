package com.example.noor.pushnotification;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity {

    private String mUserId;
    private String mUserName;
    private String mCurrentId;

    private TextView tvSendTo;
    private EditText etMessage;
    private Button btnSend;
    private FirebaseFirestore mFirestore;
    private ProgressBar progressBarSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        mFirestore = FirebaseFirestore.getInstance();
        mCurrentId = FirebaseAuth.getInstance().getUid();

        this.progressBarSend = findViewById(R.id.progressBarSend);
        this.tvSendTo = findViewById(R.id.tvSendTo);
        this.etMessage = findViewById(R.id.etMessage);
        this.btnSend = findViewById(R.id.btnSend);

        mUserId = getIntent().getExtras().getString("user_id");
        mUserName = getIntent().getExtras().getString("user_name");
        tvSendTo.setText("Send to "+mUserName);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    progressBarSend.setVisibility(View.VISIBLE);
                    Map<String, Object> notificationMessage=new HashMap<>();
                    notificationMessage.put("message",message);
                    notificationMessage.put("from",mCurrentId);
                    mFirestore.collection("users/"+mUserId+"/Notifications").add(notificationMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(SendActivity.this, "Notification Sent.", Toast.LENGTH_SHORT).show();
                            progressBarSend.setVisibility(View.INVISIBLE);
                            etMessage.setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SendActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBarSend.setVisibility(View.INVISIBLE);
                        }
                    });

                }else {
                    progressBarSend.setVisibility(View.INVISIBLE);
                    etMessage.setError("Please Enter Message!");
                }
            }
        });
    }
}
