package com.greentechnology.firebasechatapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greentechnology.firebasechatapp.Model.Message;
import com.greentechnology.firebasechatapp.R;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageStyle> {

    Context context;
    List<Message> messageList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageListAdapter.MessageStyle onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_message_layout, viewGroup, false);

        return new MessageStyle(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListAdapter.MessageStyle messageStyle, int i) {

        if(messageList.get(i).getType().equals("sender")){
            messageStyle.senderTv.setVisibility(View.VISIBLE);
            messageStyle.senderTv.setText(messageList.get(i).getMessage());
            messageStyle.receiverTv.setVisibility(View.GONE);
        }else{
            messageStyle.senderTv.setVisibility(View.GONE);
            messageStyle.receiverTv.setVisibility(View.VISIBLE);
            messageStyle.senderTv.setVisibility(View.GONE);
            messageStyle.receiverTv.setText(messageList.get(i).getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageStyle extends RecyclerView.ViewHolder {

        TextView senderTv, receiverTv;
        public MessageStyle(@NonNull View itemView) {
            super(itemView);
            senderTv = (TextView) itemView.findViewById(R.id.senderTV);
            receiverTv = (TextView) itemView.findViewById(R.id.receiverTV);
        }
    }
}
