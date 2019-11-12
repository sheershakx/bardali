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

public class adapterLog extends RecyclerView.Adapter<adapterLog.ViewHolder> {
    private ArrayList<String> log;


    public adapterLog(ArrayList<String> log) {
        this.log = log;



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.loglayout, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
         String logmsg = log.get(position);
        //id[position];

        holder.logmessage.setText(logmsg);



    }

    @Override
    public int getItemCount() {
        return log.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView logmessage;

        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            logmessage = itemView.findViewById(R.id.log_txtview);

            relativeLayout = itemView.findViewById(R.id.relativelayout_log);
        }
    }
}
