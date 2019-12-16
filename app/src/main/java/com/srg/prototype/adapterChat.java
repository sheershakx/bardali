package com.srg.prototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterChat extends RecyclerView.Adapter<adapterChat.ViewHolder> {
    // private String[] id;
    private ArrayList<String> message;
    private ArrayList<String> sentid;
    private ArrayList<String> recvid;

    // private String[] name;


    public adapterChat(ArrayList<String> sentid, ArrayList<String> recvid, ArrayList<String> message) {
        this.sentid = sentid;
        this.recvid = recvid;
        this.message = message;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chatlayoutother, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();


        String Sentid = sentid.get(position);
        String Recvid = recvid.get(position);
        String Message = message.get(position);
        if (Sentid.contentEquals(login.sessionid) && Recvid.contentEquals(chat.senderID)) {

            holder.cardViewo.setVisibility(View.GONE);
            // holder.cardViewm.setVisibility(View.VISIBLE);
            holder.chatdisplayme.setText("  " + Message + "  ");
        } else {
            holder.cardViewm.setVisibility(View.GONE);
            //    holder.cardViewo.setVisibility(View.VISIBLE);
            holder.chatdisplayother.setText("  " + Message + "  ");
        }


        //  holder.chatdisplay.setText(msgreceived);

    }

    @Override
    public int getItemCount() {
        return message.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView chatdisplayother;
        TextView chatdisplayme;
        CardView cardViewo;
        CardView cardViewm;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chatdisplayother = itemView.findViewById(R.id.textviewChatOther);
            chatdisplayme = itemView.findViewById(R.id.textviewChatMe);
            cardViewo = itemView.findViewById(R.id.cardview_chatLayoutO);
            cardViewm = itemView.findViewById(R.id.cardview_chatLayoutM);
        }
    }
}
