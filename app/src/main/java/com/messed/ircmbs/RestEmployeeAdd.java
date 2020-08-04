package com.messed.ircmbs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.messed.ircmbs.Model.SignUpCall;
import com.messed.ircmbs.Model.UserPreference;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestEmployeeAdd extends AppCompatActivity {

    EditText name,post,address,phone,salary,govtid;
    MaterialButton button;
    Call<SignUpCall> call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_employee_add);
        name=findViewById(R.id.employee_fullname);
        post=findViewById(R.id.employee_post);
        address=findViewById(R.id.employee_address);
        phone=findViewById(R.id.employee_phone);
        salary=findViewById(R.id.employee_salary);
        govtid=findViewById(R.id.employee_govid);
        button=findViewById(R.id.add_employee);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();
            }
        });
    }
    private void addEmployee()
    {
        String empname=name.getText().toString();
        String emppost=post.getText().toString();
        String empaddress=address.getText().toString();
        String empphone=phone.getText().toString();
        String empsalary=salary.getText().toString();
        String empgovtid=govtid.getText().toString();
        UserPreference userPreference=new UserPreference(getBaseContext());
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        call=networkService.addEmployee(userPreference.getResid(),empname,emppost,empsalary,empgovtid,empaddress,empphone);
        call.enqueue(new Callback<SignUpCall>() {
            @Override
            public void onResponse(Call<SignUpCall> call, Response<SignUpCall> response) {
                button.setEnabled(false);
                Toast.makeText(getBaseContext(),"Employee Added Successfully",Toast.LENGTH_LONG).show();
                name.setEnabled(false);
                post.setEnabled(false);
                address.setEnabled(false);
                phone.setEnabled(false);
                salary.setEnabled(false);
                govtid.setEnabled(false);
            }

            @Override
            public void onFailure(Call<SignUpCall> call, Throwable t) {
                Log.e("TAG",""+t);
            }
        });
        button.setEnabled(false);
    }
}