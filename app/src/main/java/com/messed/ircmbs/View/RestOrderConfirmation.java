package com.messed.ircmbs.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.messed.ircmbs.Model.AllStringHere;
import com.messed.ircmbs.R;
import com.messed.ircmbs.RestOrderAdapter;

import java.util.HashMap;
import java.util.Map;

public class RestOrderConfirmation extends AppCompatActivity {

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Menu").document("Rest-1");
        /*docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.e("TAG", "DocumentSnapshot data: " + document.getData());
                    }
                } else {
                    Log.e("TAG", "get failed with ", task.getException());
                }
            }
        });*/
        db.collection("Menu").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot document : task.getResult()) {
                    Log.e("Firestore", document.getId() + " => " + document.getId());
                }
            }
        });

    }
}
