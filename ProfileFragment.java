package com.example.noor.pushnotification;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private Button btnLogout;
    private CircleImageView circleProfilePicture;
    private TextView tvName;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String mUserId;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();

        mUserId=mAuth.getCurrentUser().getUid();

        this.tvName = view.findViewById(R.id.tvName);
        this.circleProfilePicture = view.findViewById(R.id.circleProfilePicture);
        this.btnLogout = view.findViewById(R.id.btnLogout);

        mFirestore.collection("users").document(mUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String user_name = documentSnapshot.getString("name");
                String user_image = documentSnapshot.getString("image");

                tvName.setText(user_name);
                RequestOptions placeHolder =new RequestOptions();
                placeHolder.placeholder(R.mipmap.ic_launcher_round);
                Glide.with(container.getContext()).load(user_image).into(circleProfilePicture);

            }
        });

        this.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intentLogin = new Intent(container.getContext(),LoginActivity.class);
                startActivity(intentLogin);
            }
        });
        return view;
    }

}
