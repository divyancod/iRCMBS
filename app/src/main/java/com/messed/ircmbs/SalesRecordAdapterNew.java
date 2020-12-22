package com.messed.ircmbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SalesRecordAdapterNew extends PagedListAdapter<SalesRecordModel, SalesRecordAdapterNew.SalesRecordHolder> {

    public static final DiffUtil.ItemCallback<SalesRecordModel> diffcall = new DiffUtil.ItemCallback<SalesRecordModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull SalesRecordModel oldItem, @NonNull SalesRecordModel newItem) {
            return oldItem==newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull SalesRecordModel oldItem, @NonNull SalesRecordModel newItem) {
            return true;
        }
    };
    Context context;
    public SalesRecordAdapterNew(Context context) {
        super(diffcall);
        this.context = context;
    }
    @NonNull
    @Override
    public SalesRecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sales_adapter,parent,false);
        return new SalesRecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesRecordHolder holder, int position) {
        holder.t1.setText("Table no- "+getItem(position).getTableno());
        holder.t2.setText(""+getItem(position).getTotalprice());
        String d=getItem(position).getTime();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
            SimpleDateFormat formatter5=new SimpleDateFormat("E,dd MMM yyyy HH:mm:ss");
            String s= formatter5.format(date);
            holder.t3.setText(""+s);
        }catch(Exception e) {
        }
        //holder.t3.setText(""+getItem(position).getTime());
    }

    public class SalesRecordHolder extends RecyclerView.ViewHolder{

        MaterialTextView t1,t2,t3;
        public SalesRecordHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.sales_table);
            t2=itemView.findViewById(R.id.sales_price);
            t3=itemView.findViewById(R.id.sales_date);
        }
    }
}
