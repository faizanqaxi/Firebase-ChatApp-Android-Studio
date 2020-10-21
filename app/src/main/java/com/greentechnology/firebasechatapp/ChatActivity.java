package com.greentechnology.firebasechatapp;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greentechnology.firebasechatapp.Adapter.MessageListAdapter;
import com.greentechnology.firebasechatapp.Model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    String currentUserName = "";
    String selectedUID = "";
    EditText messageEt;
    ImageView sendMessageBtn;
    DatabaseReference chatRef;
    MessageListAdapter adapter;
    List<Message> messageList;
    RecyclerView messageListRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageList = new ArrayList<>();

        sendMessageBtn = (ImageView) findViewById(R.id.sendBtn);
        messageEt = (EditText) findViewById(R.id.messageET);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        messageListRecyclerView = (RecyclerView) findViewById(R.id.chatListRecyclerView);
        messageListRecyclerView.setHasFixedSize(true);
        messageListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatRef = FirebaseDatabase.getInstance().getReference("ChatInformation");

        if(getIntent() != null){
            currentUserName = getIntent().getStringExtra("userName");
            selectedUID = getIntent().getStringExtra("uid");
            toolbar.setTitle(currentUserName);
        }


        chatRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                messageList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    messageList.add(dataSnapshot1.getValue(Message.class));
                }

                adapter = new MessageListAdapter(ChatActivity.this,messageList);
                messageListRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(messageEt.getText().toString())){

                    Message sender = new Message(messageEt.getText().toString(), selectedUID, FirebaseAuth.getInstance().getCurrentUser().getUid(), "sender" );
                    chatRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(sender);

                    Message receiver = new Message(messageEt.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid(), selectedUID, "receiver" );

                    chatRef.child(selectedUID).push().setValue(receiver);

                    messageEt.setText("");

                }
            }
        });
    }


}
