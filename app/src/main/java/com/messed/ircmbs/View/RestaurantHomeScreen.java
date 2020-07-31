package com.messed.ircmbs.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.messed.ircmbs.Model.MenuDataBase;
import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.Model.RestLoggedData;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;
import com.messed.ircmbs.R;
import com.messed.ircmbs.Model.UserPreference;
import com.messed.ircmbs.View.LoginActivities.LoginChoice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class RestaurantHomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth firebaseAuth;
    private Call<RestLoggedData> call;
    private Call<List<MenuList>> menuCall;
    TextView navdrawer_title;
    static final String TAG="RestHome";
    private UserPreference nob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_home_screen);
        toolbar=findViewById(R.id.toolbar_home);
        drawerLayout=findViewById(R.id.drawer_home);
        navigationView=findViewById(R.id.navbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        firebaseAuth=FirebaseAuth.getInstance();
        View headerview=navigationView.getHeaderView(0);
        navdrawer_title=headerview.findViewById(R.id.nav_drawer_textfield);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_draw_open, R.string.nav_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.restframelayout, new FragHomeScreen()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        nob=new UserPreference(getBaseContext());
        if(UserPreference.getInit(getBaseContext())==0)
        {
            getRestData();
            fetchMenu();
        }
        navdrawer_title.setText(nob.getRestname());
//        FirebaseDatabase database;
//        DatabaseReference reference;
//        database = FirebaseDatabase.getInstance();
//        reference = database.getReference(firebaseAuth.getUid());
//        //String id =reference.push().getKey();
//        reference=database.getReference(firebaseAuth.getUid()).child("Table");
//        reference.setValue("","");
    }

    private void getRestData()
    {
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        call=networkService.restDataCall(firebaseAuth.getUid());
        call.enqueue(new Callback<RestLoggedData>() {
            @Override
            public void onResponse(Call<RestLoggedData> call, Response<RestLoggedData> response) {
                Log.e("TAG","Getting Rest Data From Server");
                nob.setData(getBaseContext(),response.body());
                navdrawer_title.setText(response.body().getRestname());
            }

            @Override
            public void onFailure(Call<RestLoggedData> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();

        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.restframelayout, new FragHomeScreen()).commit();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.logout:
                firebaseAuth.signOut();
                nob.delete();
                MenuDataBase menuDataBase=new MenuDataBase(this);
                menuDataBase.deleteTable();
                finish();
                startActivity(new Intent(getBaseContext(), LoginChoice.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void fetchMenu()
    {
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        menuCall=networkService.getAllMenu();
        menuCall.enqueue(new Callback<List<MenuList>>() {
            @Override
            public void onResponse(Call<List<MenuList>> call, Response<List<MenuList>> response) {
                for(int i=0;i<response.body().size();i++)
                    Log.e("TAG",""+response.body().get(i).getItems());
                MenuDataBase nob=new MenuDataBase(getBaseContext());
                nob.addMenu(response.body());
            }

            @Override
            public void onFailure(Call<List<MenuList>> call, Throwable t) {

            }
        });
    }
}
