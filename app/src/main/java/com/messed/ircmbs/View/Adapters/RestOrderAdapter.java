package com.messed.ircmbs.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.messed.ircmbs.Model.AllStringHere;
import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.R;

import java.util.List;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class RestOrderAdapter extends RecyclerView.Adapter<RestOrderAdapter.RestOrderViewHolder> {
    Context context;
    List<MenuList> menuList;
    public static int ordercount[];

    public RestOrderAdapter(Context context, List<MenuList> menuList) {
        this.context = context;
        this.menuList = menuList;
        ordercount = new int[menuList.size()];
        for (int i = 0; i < ordercount.length; i++)
            ordercount[i] = 0;
        AllStringHere.menuList=menuList;
    }

    @NonNull
    @Override
    public RestOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rest_order_adapter, parent, false);
        return new RestOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RestOrderViewHolder holder, final int position) {
        holder.t1.setText(menuList.get(position).getItems());
        holder.t2.setText("Rs." + menuList.get(position).getPrice());
        holder.buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordercount[position]++;
                holder.t3.setText("" + ordercount[position]);
            }
        });

        holder.buttonsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ordercount[position] > 0) {
                    ordercount[position]--;
                    holder.t3.setText(""+ordercount[position]);
                    if(ordercount[position]==0)
                        holder.t3.setText(null);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class RestOrderViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView t1, t2, t3;
        MaterialButton buttonadd, buttonsub;

        public RestOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.order_menu_item);
            t2 = itemView.findViewById(R.id.order_menu_price);
            t3 = itemView.findViewById(R.id.counttextview);
            buttonadd = itemView.findViewById(R.id.materialButton1);
            buttonsub = itemView.findViewById(R.id.materialButton2);
        }
    }
}
