package com.messed.ircmbs.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.R;
import com.messed.ircmbs.RestOrderAdapter;
import com.messed.ircmbs.ViewModel.RestOrderViewModel;

import java.util.List;

public class FragHomeScreen extends Fragment {

    RecyclerView recyclerView;
    String title[] = {"Menu", "Billing", "Order", "Employee", "Live Table", "Table Reservation", "Online Orders", "Display Order Number", "Sales Records"};
    int images[]={R.drawable.pizza,R.drawable.bill,R.drawable.order,R.drawable.employee,R.drawable.livetable,R.drawable.reservation,R.drawable.onlineorder,R.drawable.displayorder,R.drawable.sales};

    public FragHomeScreen() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.frag_home_screen, container, false);

       //-------------------------------------------------------
        recyclerView=view.findViewById(R.id.rest_home_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RestaurantHomeAdapter restaurantHomeAdapter=new RestaurantHomeAdapter(getActivity(),title,images);
        recyclerView.setAdapter(restaurantHomeAdapter);
        //-----------------------------------------------------------

        return view;
    }
}
