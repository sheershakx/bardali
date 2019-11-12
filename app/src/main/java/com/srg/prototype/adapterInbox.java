package com.srg.prototype;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterInbox extends RecyclerView.Adapter<adapterInbox.ViewHolder> {
    // private String[] id;
    private ArrayList<String> senderId;
    private ArrayList<String> recvId;
    private ArrayList<String> username;
    private ArrayList<String> message;
  //  private ArrayList<String> recvname;


    public adapterInbox(ArrayList<String> senderId,ArrayList<String> recvId,ArrayList<String> username,ArrayList<String> message) {
        this.senderId = senderId;
        this.recvId = recvId;
        this.username = username;
        this.message = message;
      //  this.recvname = recvname;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.inboxlayout, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        final String name = username.get(position);
        final String msg = message.get(position);
        final String senderID = senderId.get(position);
        final String recvID = recvId.get(position);
      //  final String recvName = recvname.get(position);
        //id[position];
        holder.username.setText(name);
        holder.message.setText(msg);
        final String recvName=holder.username.getText().toString();


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, chat.class);
//
                intent.putExtra("senderID", senderID);
                intent.putExtra("sendername",name);
                intent.putExtra("recvname",recvName);
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return username.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView message;


        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.layoutinbox);
            username=itemView.findViewById(R.id.txtviewibname);
            message=itemView.findViewById(R.id.txtviewibmsg);

        }
    }
}
