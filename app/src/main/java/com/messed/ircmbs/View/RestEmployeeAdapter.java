package com.messed.ircmbs.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.messed.ircmbs.Model.Employee;
import com.messed.ircmbs.R;
import com.messed.ircmbs.ui.SingleEmployeeActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RestEmployeeAdapter extends RecyclerView.Adapter<RestEmployeeAdapter.RestEmployeeViewHolder> {

    Context context;
    List<Employee> employee;

    public RestEmployeeAdapter(Context context, List<Employee> employee) {
        this.context = context;
        this.employee = employee;
    }


    @NonNull
    @Override
    public RestEmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.employee_list_adapter, parent, false);
        return new RestEmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestEmployeeViewHolder holder, final int position) {
        holder.t1.setText("" + employee.get(position).getEmpName());
        holder.t2.setText("" + employee.get(position).getEmpPost());
        holder.t3.setText("Rs. " + employee.get(position).getEmpSalary());
        Glide.with(context).load(employee.get(position).getEmpProfilePic()).placeholder(R.drawable.templogo).error(R.drawable.templogo).into(holder.empprofilepic);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SingleEmployeeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(SingleEmployeeActivity.EMPLOYEE,employee.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employee.size();
    }

    public class RestEmployeeViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView t1, t2, t3;
        MaterialCardView cardView;
        CircleImageView empprofilepic;

        public RestEmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.emp_name);
            t2 = itemView.findViewById(R.id.emp_post);
            t3 = itemView.findViewById(R.id.emp_salary);
            cardView = itemView.findViewById(R.id.employee_cardlayout);
            empprofilepic = itemView.findViewById(R.id.emp_pic);
        }
    }
}
