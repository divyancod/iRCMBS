package com.messed.ircmbs.View.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.messed.ircmbs.Model.SaleModel;
import com.messed.ircmbs.R;

import java.util.List;

public class SalesRecordAdapter extends RecyclerView.Adapter<SalesRecordAdapter.SalesRecordViewHolder>  {

    Context context;
    List<SaleModel> saleModelList;
    public SalesRecordAdapter(Context context, List<SaleModel> saleModelList)
    {
        this.context=context;
        this.saleModelList=saleModelList;
    }
    @NonNull
    @Override
    public SalesRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sales_adapter,parent,false);
        return new SalesRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesRecordViewHolder holder, final int position) {
        holder.t1.setText("Table no- "+saleModelList.get(position).getTableno());
        holder.t2.setText(""+saleModelList.get(position).getTotalprice());
        holder.t3.setText(""+saleModelList.get(position).getTime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO sales details list to be added here
                Log.e("TAG",""+saleModelList.get(position).getTotalitems());
            }
        });
    }

    @Override
    public int getItemCount() {
        return saleModelList.size();
    }

    public class SalesRecordViewHolder extends RecyclerView.ViewHolder{
        MaterialTextView t1,t2,t3;
        MaterialCardView cardView;
        public SalesRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.sales_table);
            t2=itemView.findViewById(R.id.sales_price);
            t3=itemView.findViewById(R.id.sales_date);
            cardView=itemView.findViewById(R.id.sales_cardlayout);
        }
    }
}
