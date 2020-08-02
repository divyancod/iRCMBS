package com.messed.ircmbs.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.messed.ircmbs.R;

import java.util.ArrayList;

public class RestLiveOrderAdapter extends RecyclerView.Adapter<RestLiveOrderAdapter.RestLiveOrderViewHolder> {
    Context context;
    ArrayList<String> tables;

    public RestLiveOrderAdapter(Context context, ArrayList<String> tables)
    {
        this.context=context;
        this.tables=tables;
    }

    @NonNull
    @Override
    public RestLiveOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.live_order_adapter,parent,false);
        return new RestLiveOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestLiveOrderViewHolder holder, int position) {
        holder.t1.setText("Table - "+tables.get(position));

    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    public class RestLiveOrderViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView t1;
        public RestLiveOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.livetableno);
        }
    }
}
