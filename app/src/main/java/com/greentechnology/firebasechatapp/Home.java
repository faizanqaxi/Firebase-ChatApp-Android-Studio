package com.greentechnology.firebasechatapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greentechnology.firebasechatapp.Adapter.UserListAdapter;
import com.greentechnology.firebasechatapp.Model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {


    RecyclerView recyclerView;
    List<UserInfo> userInfoList;
    DatabaseReference userRef;
    UserListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        userInfoList = new ArrayList<>();


        userRef = FirebaseDatabase.getInstance().getReference("UsersInformation");


        recyclerView = (RecyclerView) findViewById(R.id.usersListRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInfoList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    UserInfo currentUser = dataSnapshot1.getValue(UserInfo.class);

                    currentUser.setuId(dataSnapshot1.getKey());
                    if(!currentUser.getuId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        userInfoList.add(currentUser);
                    }
                }
                adapter = new UserListAdapter(getApplicationContext(), userInfoList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
