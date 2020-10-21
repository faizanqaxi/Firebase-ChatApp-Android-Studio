package com.greentechnology.firebasechatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.greentechnology.firebasechatapp.ChatActivity;
import com.greentechnology.firebasechatapp.Model.UserInfo;
import com.greentechnology.firebasechatapp.R;

import java.util.List;
import java.util.Random;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    Context context;
    List<UserInfo> userInfoList;

    public UserListAdapter(Context context, List<UserInfo> userInfoList) {
        this.context = context;
        this.userInfoList = userInfoList;
    }

    @NonNull
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(context).inflate(R.layout.single_user_list_item, viewGroup, false);

        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder viewHolder, final int position) {

        viewHolder.userName.setText(userInfoList.get(position).getUserName());
        viewHolder.userPhone.setText(userInfoList.get(position).getUserPhone());

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        viewHolder.cardBg.setBackgroundColor(color);

        viewHolder.userCharacter.setText(""+userInfoList.get(position).getUserName().charAt(0));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+userInfoList.get(position).getuId(),  Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("userName", userInfoList.get(position).getUserName());
                intent.putExtra("uid", userInfoList.get(position).getuId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardBg;
        TextView userName, userPhone, userCharacter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.userNameS);
            userPhone = (TextView) itemView.findViewById(R.id.userPhoneS);
            cardBg = (CardView) itemView.findViewById(R.id.cartBg);
            userCharacter = (TextView) itemView.findViewById(R.id.userCharacter);

        }
    }
}
