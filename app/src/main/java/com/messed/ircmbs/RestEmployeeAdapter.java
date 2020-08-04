package com.messed.ircmbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.messed.ircmbs.Model.EmpDataModel;

import java.util.List;

import io.opencensus.trace.export.SampledSpanStore;

public class RestEmployeeAdapter extends RecyclerView.Adapter<RestEmployeeAdapter.RestEmployeeViewHolder> {

    Context context;
    List<EmpDataModel> empList;
    public RestEmployeeAdapter(Context context, List<EmpDataModel> empList)
    {
        this.context=context;
        this.empList=empList;
    }
    @NonNull
    @Override
    public RestEmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.employee_list_adapter,parent,false);
        return new RestEmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestEmployeeViewHolder holder, int position) {
        holder.t1.setText(""+empList.get(position).getEmpname());
        holder.t2.setText(""+empList.get(position).getEmppost());
        holder.t3.setText("Rs. "+empList.get(position).getEmpsalary());
    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class RestEmployeeViewHolder extends RecyclerView.ViewHolder{
        MaterialTextView t1,t2,t3;
        public RestEmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.emp_name);
            t2=itemView.findViewById(R.id.emp_post);
            t3=itemView.findViewById(R.id.emp_salary);
        }
    }
}
