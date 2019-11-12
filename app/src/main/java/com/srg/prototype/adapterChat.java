package com.srg.prototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterChat extends RecyclerView.Adapter<adapterChat.ViewHolder> {
    // private String[] id;
    private ArrayList<String> message;
    private ArrayList<String> sentid;
    private ArrayList<String> recvid;

    // private String[] name;


    public adapterChat(ArrayList<String> sentid,ArrayList<String> recvid,ArrayList<String> message) {
        this.sentid = sentid;
        this.recvid = recvid;
        this.message = message;





    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chatlayout, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();

       
        String Sentid = sentid.get(position);
        String Recvid = recvid.get(position);
        String Message = message.get(position);
        if (Sentid.contentEquals(login.sessionid) && Recvid.contentEquals(chat.senderID)) {

            holder.chatdisplay.setText("Me: "+ Message );
        }
        else holder.chatdisplay.setText(chat.sendername+": "+ Message );








      //  holder.chatdisplay.setText(msgreceived);

    }

    @Override
    public int getItemCount() {
        return message.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView chatdisplay;


        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chatdisplay = itemView.findViewById(R.id.textviewChat);
            relativeLayout = itemView.findViewById(R.id.layoutchat);
        }
    }
}
