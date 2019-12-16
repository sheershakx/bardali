package com.srg.prototype;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterNewsfeed extends RecyclerView.Adapter<adapterNewsfeed.ViewHolder> {
    // private String[] id;
    private ArrayList<String> id;
    private ArrayList<String> name;
    private ArrayList<String> unit;
    // private String[] name;
    private ArrayList<String> quantity;

    private ArrayList<String> total;
    private ArrayList<String> imageurl;

    public adapterNewsfeed(ArrayList<String> id, ArrayList<String> name, ArrayList<String> unit, ArrayList<String> quantity, ArrayList<String> total, ArrayList<String> imageurl) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.total = total;
        this.imageurl = imageurl;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.newsfeellayout, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        final String itemid = id.get(position);
        //id[position];
        String itemname = name.get(position);
        String itemunit = unit.get(position);
        //name[position];
        String itemquantity = quantity.get(position);
        String itemtotal = total.get(position);
        String photourl = imageurl.get(position);

        holder.itemName.setText(itemname);
        holder.itemQuantity.setText(itemquantity + " " + itemunit);
        holder.itemTotal.setText(itemtotal);
        if (photourl != null && !TextUtils.isEmpty(photourl))
            Picasso.get().load(photourl).into(holder.itemImage);


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDescription.class);

                intent.putExtra("itemid", itemid);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemQuantity;
        TextView itemTotal;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            itemTotal = itemView.findViewById(R.id.itemTotal);
            relativeLayout = itemView.findViewById(R.id.layoutfeed);
        }
    }
}
