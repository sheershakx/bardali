package com.srg.prototype;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterActivity extends RecyclerView.Adapter<adapterActivity.ViewHolder> {

    private ArrayList<String> itemid;
    private ArrayList<String> itemname;
    private ArrayList<String> image;
    private ArrayList<String> status;


    public adapterActivity(ArrayList<String> itemid, ArrayList<String> itemname, ArrayList<String> image, ArrayList<String> status) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.image = image;
        this.status = status;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.myactivity_layout, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        final String Itemid = itemid.get(position);
        final String Itemname = itemname.get(position);
        final String Image = image.get(position);
        final String Status = status.get(position);


        holder.itemName.setText(Itemname);
        Picasso.get().load(Image).into(holder.Imageview);

        if (Status.equals("1")){
            holder.textView.setText("Item name:"+" (SOLD)");
        } else holder.textView.setText("Item name:");

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDescription.class);

                intent.putExtra("itemid", Itemid);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemid.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Imageview;
        TextView itemName;
        TextView textView;

        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Imageview = itemView.findViewById(R.id.image_activity);
            itemName = itemView.findViewById(R.id.item_activity);
            textView = itemView.findViewById(R.id.textview_name);
            relativeLayout = itemView.findViewById(R.id.activity_relative);
        }
    }
}
