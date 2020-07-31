package com.messed.ircmbs.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.messed.ircmbs.Model.AllStringHere;
import com.messed.ircmbs.R;
import com.messed.ircmbs.RestOrderAdapter;
import com.messed.ircmbs.RestOrderConfirmationAdapter;
import com.messed.ircmbs.View.LoginActivities.LoginChoice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        taxtv.setText(""+tax);
        tax=total+tax;
        totaltv.setText(""+tax);
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
