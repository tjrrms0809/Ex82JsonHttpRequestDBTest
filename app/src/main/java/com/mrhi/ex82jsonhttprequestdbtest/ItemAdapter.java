package com.mrhi.ex82jsonhttprequestdbtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Item> items;

    public ItemAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.recycler_item, parent, false);

        VH holder= new VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh= (VH)holder;

        Item item= items.get(position);
        vh.tvName.setText(item.getName());
        vh.tvDate.setText(item.getDate());
        vh.tvMsg.setText(item.getMsg());

        Glide.with(context).load(item.getImgPath()).into(vh.iv);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvDate;
        TextView tvMsg;
        ImageView iv;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvName= itemView.findViewById(R.id.tv_name);
            tvDate= itemView.findViewById(R.id.tv_date);
            tvMsg= itemView.findViewById(R.id.tv_msg);
            iv= itemView.findViewById(R.id.iv);

        }
    }
}
