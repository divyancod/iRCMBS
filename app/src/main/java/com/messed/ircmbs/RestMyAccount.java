package com.messed.ircmbs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.messed.ircmbs.Model.UserPreference;


public class RestMyAccount extends Fragment {


    public RestMyAccount() {
        // Required empty public constructor
    }

    EditText restname,ownername,restadderss,restemp,resttables;
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
        setDetails();
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
    }
}