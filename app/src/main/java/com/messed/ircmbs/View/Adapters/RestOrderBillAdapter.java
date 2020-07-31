package com.messed.ircmbs.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.R;

import java.util.ArrayList;
import java.util.List;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class RestOrderBillAdapter extends RecyclerView.Adapter<RestOrderBillAdapter.RestOrderBillViewHolder> {
    Context context;
    List<MenuList> menuListList;
    ArrayList<String> itemQuan;
    public RestOrderBillAdapter(Context context, List<MenuList> menuListList,ArrayList<String> itemQuan)
    {
        this.context=context;
        this.menuListList=menuListList;
        this.itemQuan=itemQuan;
    }
    @NonNull
    @Override
    public RestOrderBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_confirm_adapter,parent,false);
        return new RestOrderBillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestOrderBillViewHolder holder, int position) {
        holder.t1.setText(""+menuListList.get(position).getItems());
        holder.t3.setText("x"+itemQuan.get(position));
        int price=Integer.parseInt(menuListList.get(position).getPrice())*Integer.parseInt(itemQuan.get(position));
        holder.t2.setText(""+price);

    }

    @Override
    public int getItemCount() {
        return menuListList.size();
    }

    public class RestOrderBillViewHolder extends RecyclerView.ViewHolder{
        MaterialTextView t1,t2,t3;
        public RestOrderBillViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.itemname);
            t2=itemView.findViewById(R.id.itemprice);
            t3=itemView.findViewById(R.id.itemquan);
        }
    }
}
