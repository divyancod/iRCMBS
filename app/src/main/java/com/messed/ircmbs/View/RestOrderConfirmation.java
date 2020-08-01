package com.messed.ircmbs.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.messed.ircmbs.Model.AllStringHere;
import com.messed.ircmbs.R;
import com.messed.ircmbs.View.Adapters.RestOrderAdapter;
import com.messed.ircmbs.View.Adapters.RestOrderConfirmationAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class RestOrderConfirmation extends AppCompatActivity {

    public static final String TAG = "RESTORDERCONF";
    Toolbar toolbar;
    RecyclerView recyclerView;
    MaterialTextView totaltv,taxtv,orderTable;
    MaterialButton pushbutton;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseDatabase database;
    HashMap<String,String> menufire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_order_confirmation);
        toolbar=findViewById(R.id.toolbar_all);
        recyclerView=findViewById(R.id.order_recycler);
        totaltv=findViewById(R.id.ordertotal);
        taxtv=findViewById(R.id.ordertax);
        orderTable=findViewById(R.id.OrderFrom);
        pushbutton=findViewById(R.id.push_button);
        firebaseAuth=FirebaseAuth.getInstance();
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        final long orderfrom=getIntent().getLongExtra("tableno",-1);
        Log.e("T",""+orderfrom);
        if(orderfrom==0)
        {
            orderTable.setText("Spot Order");
        }else
        {
            orderTable.setText("Table No - "+orderfrom);
        }
        int orderid[]= RestOrderAdapter.ordercount;
        final ArrayList<Integer> order=new ArrayList<>();
        ArrayList<Integer> itemposition=new ArrayList<>();
         menufire=new HashMap<>();
        int total=0;
        float tax;
        for(int i=0;i<orderid.length;i++) {
            if (orderid[i] != 0) {
                order.add(orderid[i]);
                itemposition.add(i);
                total = total + Integer.parseInt(AllStringHere.menuList.get(i).getPrice()) * orderid[i];
                menufire.put("" +i, "" + orderid[i]);
//                Log.e("TAG",""+i);
//                Log.e("RestConfirmation", "onCreate: " + orderid[i]);
//                Log.e("TAG",""+Integer.parseInt(AllStringHere.menuList.get(i).getPrice())*orderid[i]);
//                Log.e("TAG", "onCreate: " + AllStringHere.menuList.get(i).getItems());
            }
        }
        tax=(float)(total*18)/100;
        taxtv.setText("Rs. "+tax);
        tax=total+tax;
        totaltv.setText("Rs. "+tax);
        final RestOrderConfirmationAdapter nob=new RestOrderConfirmationAdapter(this,order,itemposition);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(nob);
        pushbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference(firebaseAuth.getUid());
                //String id =reference.push().getKey();
                reference=database.getReference(firebaseAuth.getUid()).child("Table").child(""+orderfrom).child("Order");
                reference.setValue(menufire);
                //reference=database.getReference(firebaseAuth.getUid()).child("Table").child(""+orderfrom).child("TableNo");
                //reference.setValue(orderfrom);
                Snackbar.make(v,"Order Pushed To Kitchen\nThis Screen Will close after 5 Seconds",Snackbar.LENGTH_LONG).show();
                pushbutton.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },5000);
            }
        });
    }
}
