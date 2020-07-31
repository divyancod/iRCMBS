package com.messed.ircmbs.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.messed.ircmbs.Model.AllStringHere;
import com.messed.ircmbs.R;

import java.util.ArrayList;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class RestOrderConfirmationAdapter extends RecyclerView.Adapter<RestOrderConfirmationAdapter.RestOrderConfirmViewHolder>  {
    private Context context;
    private ArrayList<Integer> orderid,itemposition;
    public RestOrderConfirmationAdapter(Context context, ArrayList<Integer> orderid,ArrayList<Integer> itemposition)
    {
        this.context=context;
        this.orderid=orderid;
        this.itemposition=itemposition;

    }
    @NonNull
    @Override
    public RestOrderConfirmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_confirm_adapter,parent,false);
        return new RestOrderConfirmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestOrderConfirmViewHolder holder, int position) {
            holder.t1.setText(AllStringHere.menuList.get(itemposition.get(position)).getItems());
            int price=Integer.parseInt(AllStringHere.menuList.get(itemposition.get(position)).getPrice())*orderid.get(position);
            holder.t2.setText(""+price);
            holder.t3.setText("x"+orderid.get(position));
    }

    @Override
    public int getItemCount() {
        return orderid.size();
    }

    public class RestOrderConfirmViewHolder extends  RecyclerView.ViewHolder{
        MaterialTextView t1,t2,t3;
        public RestOrderConfirmViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.itemname);
            t2=itemView.findViewById(R.id.itemprice);
            t3=itemView.findViewById(R.id.itemquan);
        }
    }
}
