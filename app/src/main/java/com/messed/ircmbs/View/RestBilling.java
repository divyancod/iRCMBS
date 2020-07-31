package com.messed.ircmbs.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.messed.ircmbs.MenuDataBase;
import com.messed.ircmbs.Model.AllStringHere;
import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.Model.SignUpCall;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;
import com.messed.ircmbs.R;
import com.messed.ircmbs.RestOrderBillAdapter;
import com.messed.ircmbs.UserPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */

public class RestBilling extends AppCompatActivity {

    AppCompatSpinner spinner;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseDatabase database;
    HashMap<String,String> tablelist;
    RecyclerView recyclerView;
    HashMap<String,String> orderMenu;
    ArrayList<String> menuSno;
    MaterialTextView totalbill,taxbill;
    MaterialCardView cardViewTotal,cardViewList;
    MaterialButton button;
    private Call<SignUpCall> call;
    String currtable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_billing);
        spinner=findViewById(R.id.rest_billing_spinner);
        spinner.setPrompt("Title");
        toolbar=findViewById(R.id.toolbar_all);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        itemcount=new ArrayList<>();
        button=findViewById(R.id.materialButton4);
        totalbill=findViewById(R.id.ordertotal_bill);
        taxbill=findViewById(R.id.ordertax_bill);
        recyclerView=findViewById(R.id.order_recycler_bill);
        cardViewTotal=findViewById(R.id.cardLayout3);
        cardViewTotal.setVisibility(View.INVISIBLE);
        cardViewList=findViewById(R.id.cardLayout2);
        cardViewList.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        tablelist=new HashMap<>();
        spinner.setEnabled(false);
        button.setEnabled(false);
        getTables();
        UserPreference nob=new UserPreference(getBaseContext());
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, new AllStringHere().getRestOrderSpinner(Integer.parseInt(nob.getTables())));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String key=position+"";
                if(position>0) {
                    if(tablelist.containsKey(key))
                    {
                        button.setEnabled(true);
                        Log.e("Tag","Existes");
                        currtable = key;
                        getOrderItems(key);
                    }else
                    {
                        button.setEnabled(false);
                        cardViewList.setVisibility(View.INVISIBLE);
                        cardViewTotal.setVisibility(View.INVISIBLE);
                        Snackbar.make(view,"No order found on this table",Snackbar.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","Generating Bill");
                sendOrderData();
                if(!currtable.isEmpty()) {
                    database.getReference(firebaseAuth.getUid()).child("Table").child(currtable).removeValue();
                    Snackbar.make(v,"Kindly check payments",Snackbar.LENGTH_LONG).show();
                    button.setEnabled(false);
                }
            }
        });
    }

    private void getTables()
    {
        reference = database.getReference(firebaseAuth.getUid()).child("Table");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    tablelist.put(dataSnapshot1.getKey(),"0");
                }
                spinner.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    ArrayList<String> itemcount;
    private void getOrderItems(String tableno)
    {
        reference = database.getReference(firebaseAuth.getUid()).child("Table").child(tableno).child("Order");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("TAG",""+dataSnapshot);
                menuSno=new ArrayList<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    itemcount.add(dataSnapshot1.getValue().toString());
                    //Log.e("TAG",""+dataSnapshot1.getKey()+"<<->>"+dataSnapshot1.getValue());
                    menuSno.add(dataSnapshot1.getKey());
                }
                MenuDataBase menuDataBase=new MenuDataBase(getBaseContext());
                List<MenuList> itemOrdered= menuDataBase.getItem(menuSno);
                RestOrderBillAdapter adapter=new RestOrderBillAdapter(getBaseContext(),itemOrdered,itemcount);
                cardViewList.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(adapter);
                cardViewTotal.setVisibility(View.VISIBLE);
                int total=0;
                for(int i=0;i<itemOrdered.size();i++)
                {
                    int tempprice=Integer.parseInt(itemOrdered.get(i).getPrice());
                    int tempcount=Integer.parseInt(itemcount.get(i));
                    total=(tempprice*tempcount)+total;
                }
                double tax=total*(0.18);
                taxbill.setText("Rs. "+tax);
                tax=tax+total;
                totalbill.setText("Rs. "+tax);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void sendOrderData()
    {
        UserPreference userPreference=new UserPreference(getBaseContext());
        String items="";
        for(int i=0;i<menuSno.size();i++)
        {
            items=items+"(";
            items=items+menuSno.get(i)+"-"+itemcount.get(i);
            items=items+")";
        }
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        call=networkService.restOrders(items,totalbill.getText().toString(),userPreference.getResid());
        call.enqueue(new Callback<SignUpCall>() {
            @Override
            public void onResponse(Call<SignUpCall> call, Response<SignUpCall> response) {
                Log.e("TAG","Call successfull");
            }

            @Override
            public void onFailure(Call<SignUpCall> call, Throwable t) {

            }
        });
    }
}
