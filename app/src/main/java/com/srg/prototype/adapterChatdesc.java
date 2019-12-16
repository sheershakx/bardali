package com.srg.prototype;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterChatdesc extends RecyclerView.Adapter<adapterChatdesc.ViewHolder> {
    // private String[] id;
    private ArrayList<String> message;
    private ArrayList<String> sentid;
    private ArrayList<String> recvid;

    // private String[] name;


    public adapterChatdesc(ArrayList<String> sentid, ArrayList<String> recvid, ArrayList<String> message) {
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();


        String Sentid = sentid.get(position);
        String Recvid = recvid.get(position);
        String Message = message.get(position).trim();
        if (Sentid.contentEquals(login.sessionid) && Recvid.contentEquals(ItemDescription.suid)) {
           holder.cardViewo.setVisibility(View.GONE);
          // holder.cardViewm.setVisibility(View.VISIBLE);
            holder.chatdisplayme.setText("  " + Message + "  ");

        } else if (Sentid.contentEquals(ItemDescription.suid) && Recvid.contentEquals(login.sessionid)) {
            holder.cardViewm.setVisibility(View.GONE);
        //    holder.cardViewo.setVisibility(View.VISIBLE);
            holder.chatdisplayother.setText("  " + Message + "  ");


        }

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
