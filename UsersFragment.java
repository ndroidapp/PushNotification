package com.example.noor.pushnotification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {


    private RecyclerView recyclerUsersList;
    private List<Users> usersList;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    FirebaseFirestore mFirestore;

    public UsersFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();

        usersList.clear();
        mFirestore.collection("users").addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange doc:documentSnapshots.getDocumentChanges()){
                    if (doc.getType()==DocumentChange.Type.ADDED){
                        String user_id=doc.getDocument().getId();
                        Users users=doc.getDocument().toObject(Users.class).withId(user_id);
                        usersList.add(users);
                        usersRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        mFirestore=FirebaseFirestore.getInstance();

        this.recyclerUsersList =  view.findViewById(R.id.recyclerUsersList);

        usersList=new ArrayList<>();
        usersRecyclerAdapter=new UsersRecyclerAdapter(container.getContext(),usersList);
        recyclerUsersList.setHasFixedSize(true);
        recyclerUsersList.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerUsersList.setAdapter(usersRecyclerAdapter);

        return view;
    }

}
