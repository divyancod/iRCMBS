package com.messed.ircmbs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;
import com.messed.ircmbs.Model.RestLoggedData;
import com.messed.ircmbs.Model.SignUpCall;
import com.messed.ircmbs.Model.UserPreference;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestMyAccount extends Fragment {


    public RestMyAccount() {
        // Required empty public constructor
    }

    EditText restname,ownername,restadderss,restemp,resttables,restemail,restphone;
    MaterialCheckBox cloud;
    MaterialButton updatebutton;
    private Call<SignUpCall> call;
    String cloudornot;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_rest_my_account, container, false);
        restname=view.findViewById(R.id.myacc_restname);
        ownername=view.findViewById(R.id.myacc_ownername);
        restadderss=view.findViewById(R.id.myacc_address);
        restemp=view.findViewById(R.id.myacc_numemployees);
        resttables=view.findViewById(R.id.myacc_numtables);
        restemail=view.findViewById(R.id.myacc_restemail);
        updatebutton=view.findViewById(R.id.myacc_nextbutton);
        restphone=view.findViewById(R.id.myacc_phone);
        cloud=view.findViewById(R.id.myacc_cloudornot);
        restemail.setEnabled(false);
        setDetails();
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
                updatebutton.setEnabled(false);
            }
        });
        return view;
    }
    
    private void setDetails()
    {
        UserPreference userPreference=new UserPreference(getActivity());
        restname.setText(""+userPreference.getRestname());
        ownername.setText(""+userPreference.getOwner());
        restadderss.setText(""+userPreference.getAddress());
        restemp.setText(""+userPreference.getEmployees());
        resttables.setText(""+userPreference.getTables());
        restemail.setText(""+FirebaseAuth.getInstance().getCurrentUser().getEmail());
        restphone.setText(""+userPreference.getPhone());
        String c=userPreference.getCloudornot();
        if(c.equals("1"))
            cloud.setChecked(true);
    }
    private void updateProfile()
    {
        if(call!=null)
            call.cancel();
        cloudornot="0";
        if(cloud.isChecked())
            cloudornot="1";
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        call=networkService.updateProfileCall(restname.getText().toString(),""+FirebaseAuth.getInstance().getUid(),ownername.getText().toString(),restadderss.getText().toString(),cloudornot,resttables.getText().toString(),restemp.getText().toString(),restphone.getText().toString());
        call.enqueue(new Callback<SignUpCall>() {
            @Override
            public void onResponse(Call<SignUpCall> call, Response<SignUpCall> response) {
                Log.e("TAG","Sucessfuly updated");
                Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_LONG).show();
                UserPreference nob=new UserPreference(getContext());
                nob.setRestname(restname.getText().toString());
                nob.setOwner(ownername.getText().toString());
                nob.setAddress(restadderss.getText().toString());
                nob.setEmployees(restemp.getText().toString());
                nob.setTables(resttables.getText().toString());
                nob.setPhone(restphone.getText().toString());
                nob.setCloudornot(cloudornot);
                updatebutton.setEnabled(true);
            }

            @Override
            public void onFailure(Call<SignUpCall> call, Throwable t) {
                Toast.makeText(getContext(),"Some Error Occured try again",Toast.LENGTH_LONG).show();
                Log.e("TAG",""+t);
                updatebutton.setEnabled(true);
            }
        });
    }
}