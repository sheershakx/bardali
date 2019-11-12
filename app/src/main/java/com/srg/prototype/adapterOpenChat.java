package com.srg.prototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterOpenChat extends RecyclerView.Adapter<adapterOpenChat.ViewHolder> {
    // private String[] id;
    private ArrayList<String> message;
    private ArrayList<String> userid;


    // private String[] name;


    public adapterOpenChat(ArrayList<String> userid, ArrayList<String> message) {

        this.userid = userid;
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


        String Userid = userid.get(position);
        String Message = message.get(position);
        if (Userid != null && Userid.contentEquals(login.sessionid)) {

            holder.chatdisplay.setText("Me: " + Message);
        } else if (Userid != null && Userid.contentEquals(ItemDescription.suid)) {
            holder.chatdisplay.setText("Seller: " + Message);
        } else if (Userid != null) holder.chatdisplay.setText("*: " + Message);
        else Toast.makeText(context, "Message content in empty", Toast.LENGTH_SHORT).show();


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
