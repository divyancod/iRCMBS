package com.messed.ircmbs.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestOrderConfirmation extends AppCompatActivity {

    public static final String TAG = "RESTORDERCONF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_order_confirmation);
        /*int order[]= RestOrderAdapter.ordercount;
        for(int i=0;i<order.length;i++) {
            Log.e("RestConfirmation", "onCreate: " + order[i]);
            if(order[i]!=0)
            Log.e("TAG", "onCreate: "+AllStringHere.menuList.get(i).getItems());
        }*/
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
       /* Map<String,Object> map=new HashMap<>();
        map.put("Name","hello world");
        map.put("PhoneNo",currentUser.getPhoneNumber());
        map.put("Address","RB II 671 / D A Road");
        Map<String,String> menu=new HashMap<>();
        menu.put("Panner","20");
        menu.put("chicken","50");
        map.put("Menu",menu);
        firestore.collection("Resturants").document(currentUser.getUid()).set(map);*/
       /*firestore.collection("Resturants").document(currentUser.getUid())
               .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                   @Override
                   public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                       Log.e(TAG, "onEvent: "+documentSnapshot.get("Menu"));

                   }
               });*/
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    }
}
