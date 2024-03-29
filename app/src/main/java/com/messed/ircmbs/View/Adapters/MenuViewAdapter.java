package com.messed.ircmbs.View.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.R;

import java.util.List;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class MenuViewAdapter extends RecyclerView.Adapter<MenuViewAdapter.MenuViewViewHolder> {
    Context context;
    List<MenuList> menuList;

    public MenuViewAdapter(List<MenuList> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
        Log.e("constructor", "MenuViewAdapter: " );
    }

    @NonNull
    @Override
    public MenuViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.menu_recycler_adapter,parent,false);
        return new MenuViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewViewHolder holder, int position) {
        Log.e("Adapter", "onBindViewHolder: " );
        holder.t1.setText(""+menuList.get(position).getItems());
        holder.t2.setText("Rs."+menuList.get(position).getPrice());
        holder.ratingBar.setRating(Float.parseFloat(menuList.get(position).getRating()));
        holder.t3.setText(""+menuList.get(position).getItemdesc());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MenuViewViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3;
        RatingBar ratingBar;
        public MenuViewViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.menuitemname);
            t2=itemView.findViewById(R.id.menuitemprice);
            ratingBar=itemView.findViewById(R.id.ratingbar);
            t3=itemView.findViewById(R.id.itemdesc);
        }
    }
}
