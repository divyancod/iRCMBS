package com.messed.ircmbs.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.messed.ircmbs.MiscNoteActivity;
import com.messed.ircmbs.Model.MenuDataBase;
import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.Model.RestLoggedData;
import com.messed.ircmbs.Model.SignUpCall;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;
import com.messed.ircmbs.R;
import com.messed.ircmbs.Model.UserPreference;
import com.messed.ircmbs.RestMyAccount;
import com.messed.ircmbs.SalesRecord;
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
    private Call<SignUpCall> userCheckCall;
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
            userCheckCal();
        }else
        {
            //printMenu();
            userCheckCal();
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
            case R.id.nav_myacc:
                getSupportFragmentManager().beginTransaction().replace(R.id.restframelayout, new RestMyAccount()).commit();
                break;
            case R.id.nav_logout:
                signOut();
                break;
            case R.id.nav_earning:
                Toast.makeText(this,"Coming Soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_note:
                startActivity(new Intent(RestaurantHomeScreen.this, MiscNoteActivity.class));
                break;
            case R.id.contactus:
                String url = "http://divyanshu123.000webhostapp.com/menuadd.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
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
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void fetchMenu()
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait fetching the default menu from server");
        progressDialog.show();
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        menuCall=networkService.getAllMenu();
        menuCall.enqueue(new Callback<List<MenuList>>() {
            @Override
            public void onResponse(Call<List<MenuList>> call, Response<List<MenuList>> response) {
                for(int i=0;i<response.body().size();i++)
                    Log.e("TAG",""+response.body().get(i).getRating());
                MenuDataBase nob=new MenuDataBase(getBaseContext());
                nob.addMenu(response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<MenuList>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    private void printMenu()
    {
        MenuDataBase nob=new MenuDataBase(getBaseContext());
        List<MenuList> menu=nob.getAllItems();
        for(int i=0;i<menu.size();i++)
        {
            Log.e("TAG","====="+menu.get(i).getItems()+"="+menu.get(i).getPrice()+"="+menu.get(i).getRating());
        }
    }
    private void signOut()
    {
        firebaseAuth.signOut();
        nob.delete();
        MenuDataBase menuDataBase=new MenuDataBase(this);
        menuDataBase.deleteTable();
        finish();
        startActivity(new Intent(getBaseContext(), LoginChoice.class));
    }
    private void userCheckCal()
    {
        String resid= firebaseAuth.getCurrentUser().getUid();
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        userCheckCall=networkService.userCheck(resid);
        userCheckCall.enqueue(new Callback<SignUpCall>() {
            @Override
            public void onResponse(Call<SignUpCall> call, Response<SignUpCall> response) {
                Log.e("tag",""+response.body().getStatus());
                UserPreference nob=new UserPreference(RestaurantHomeScreen.this);
                if(response.body().getError().equals("1"))
                {
                    nob.setAccountStatus(RestaurantHomeScreen.this,response.body().getError());
                    Snackbar.make(findViewById(android.R.id.content),"Account not verified contact developer at divyanfun@gmail.com",Snackbar.LENGTH_INDEFINITE).show();
                }else
                {
                    nob.setAccountStatus(RestaurantHomeScreen.this,response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<SignUpCall> call, Throwable t) {
                Log.e("tag",""+t);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
