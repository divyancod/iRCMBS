package com.messed.ircmbs.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.messed.ircmbs.R;
import com.messed.ircmbs.RestOrder;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class RestaurantHomeAdapter extends RecyclerView.Adapter<RestaurantHomeAdapter.RestaurantViewHolder> {
    Context context;
    String title[];
    int images[];

    public RestaurantHomeAdapter(Context context, String[] title, int[] images) {
        this.context = context;
        this.title = title;
        this.images = images;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.rest_home_screen_adapter,parent,false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, final int position) {
        holder.t1.setText(title[position]);
        holder.i1.setImageResource(images[position]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switch (position)
               {
                   case 0 : context.startActivity(new Intent(context,MenuViewActivity.class));
                   break;
                   case 1 : context.startActivity(new Intent(context,RestBilling.class));
                   break;
                   case 2: context.startActivity(new Intent(context, RestOrder.class));
                   break;
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView t1;
        ImageView i1;
        MaterialCardView cardView;
        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.adaptertextView);
            i1=itemView.findViewById(R.id.adapterimageView);
            cardView=itemView.findViewById(R.id.adaptercardview);
        }
    }
}
