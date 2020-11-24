package com.messed.ircmbs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.messed.ircmbs.Model.RestLoggedData;
import com.messed.ircmbs.Model.SignUpCall;
import com.messed.ircmbs.Model.UserPreference;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AccountSettings extends AppCompatActivity {

    EditText restname, ownername, restadderss, restemp, resttables, restemail, restphone;
    MaterialCheckBox cloud;
    MaterialButton updatebutton;
    private Call<SignUpCall> call;
    String cloudornot;
    Toolbar toolbar;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    CircleImageView profilepic;
    String currentProfilepic;
    FloatingActionButton profilepicedit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        toolbar = findViewById(R.id.toolbar_all);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        profilepic = findViewById(R.id.profilepic);
        restname = findViewById(R.id.myacc_restname);
        ownername = findViewById(R.id.myacc_ownername);
        restadderss = findViewById(R.id.myacc_address);
        restemp = findViewById(R.id.myacc_numemployees);
        resttables = findViewById(R.id.myacc_numtables);
        restemail = findViewById(R.id.myacc_restemail);
        updatebutton = findViewById(R.id.myacc_nextbutton);
        restphone = findViewById(R.id.myacc_phone);
        cloud = findViewById(R.id.myacc_cloudornot);
        profilepicedit = findViewById(R.id.profilepiceditbtn);
        restemail.setEnabled(false);
        setDetails();
        updatebutton.setOnClickListener(v -> {
            updatebutton.setEnabled(false);
            updateProfile();
            updatebutton.setEnabled(false);
        });
        profilepic.setOnClickListener(v -> new LargePictureView(getApplicationContext(), v, currentProfilepic));
        profilepicedit.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT).setType("image/*");
            startActivityForResult(intent, 1);
        });
    }

    private void updateProfilePicURL(String filename) {
        UserPreference userPreference = new UserPreference(getApplicationContext());
        NetworkService networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        Call<SignUpCall> updatecall = networkService.updateProfilePic(userPreference.getResid(), filename);
        updatecall.enqueue(new Callback<SignUpCall>() {
            @Override
            public void onResponse(Call<SignUpCall> call, Response<SignUpCall> response) {
                Log.e("TAG", "Update Profile PIC URL");
                setUpdatedData();
                userPreference.setProfilepic(filename);
                picture=null;
            }

            @Override
            public void onFailure(Call<SignUpCall> call, Throwable t) {
                Log.e("TAG", "Update Profile PIC URL" + t);
            }
        });
    }

    private void uploadProfilePic() {
        if (picture == null) {
            return;
        }
        final File file = new File(picture.getPath());
        NetworkService networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        Call<SignUpCall> callll = networkService.tester(MultipartBody.Part.createFormData("file", file.getName(), fbody));
        callll.enqueue(new Callback<SignUpCall>() {
            @Override
            public void onResponse(Call<SignUpCall> call, Response<SignUpCall> response) {
                updateProfilePicURL(file.getName());
            }

            @Override
            public void onFailure(Call<SignUpCall> call, Throwable t) {
                Log.e("uploadProfilePic() - >", "" + t);
            }
        });
    }


    private void setDetails() {
        UserPreference userPreference = new UserPreference(this);
        restname.setText("" + userPreference.getRestname());
        ownername.setText("" + userPreference.getOwner());
        restadderss.setText("" + userPreference.getAddress());
        restemp.setText("" + userPreference.getEmployees());
        resttables.setText("" + userPreference.getTables());
        restemail.setText("" + FirebaseAuth.getInstance().getCurrentUser().getEmail());
        restphone.setText("" + userPreference.getPhone());
        currentProfilepic = userPreference.getProfilePic();
        Glide.with(getApplicationContext()).load(userPreference.getProfilePic()).placeholder(R.drawable.templogo).error(R.drawable.templogo).into(profilepic);
        String c = userPreference.getCloudornot();
        if (c.equals("1"))
            cloud.setChecked(true);
    }


    private void updateProfile() {
        if (call != null)
            call.cancel();
        cloudornot = "0";
        if (cloud.isChecked())
            cloudornot = "1";
        NetworkService networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        call = networkService.updateProfileCall(restname.getText().toString(), "" + FirebaseAuth.getInstance().getUid(), ownername.getText().toString(), restadderss.getText().toString(), cloudornot, resttables.getText().toString(), restemp.getText().toString(), restphone.getText().toString());
        call.enqueue(new Callback<SignUpCall>() {
            @Override
            public void onResponse(Call<SignUpCall> call, Response<SignUpCall> response) {
                Log.e("TAG", "Sucessfuly updated");
                Toast.makeText(AccountSettings.this, "Profile Updated", Toast.LENGTH_LONG).show();
                UserPreference nob = new UserPreference(AccountSettings.this);
                nob.setRestname(restname.getText().toString());
                nob.setOwner(ownername.getText().toString());
                nob.setAddress(restadderss.getText().toString());
                nob.setEmployees(restemp.getText().toString());
                nob.setTables(resttables.getText().toString());
                nob.setPhone(restphone.getText().toString());
                nob.setCloudornot(cloudornot);
                uploadProfilePic();
                updatebutton.setEnabled(true);
            }

            @Override
            public void onFailure(Call<SignUpCall> call, Throwable t) {
                Toast.makeText(AccountSettings.this, "Some Error Occured try again", Toast.LENGTH_LONG).show();
                Log.e("TAG", "" + t);
                updatebutton.setEnabled(true);
            }
        });
    }

    Uri picture;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageUri = data.getData();
            startCrop(imageUri);
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri uri = UCrop.getOutput(data);
            profilepic.setImageURI(uri);
        }
    }

    private void startCrop(@NonNull Uri uri) {
        String des = "pic" + System.currentTimeMillis() + ".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), des)));
        uCrop.withAspectRatio(1, 1);
        uCrop.withMaxResultSize(450, 450);
        uCrop.withOptions(getCropOptions());
        uCrop.start(AccountSettings.this);
        picture = Uri.fromFile(new File(getCacheDir(), des));
    }

    private UCrop.Options getCropOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);
        options.setCompressionQuality(50);
        options.setToolbarTitle("Crop Your Image");
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        return options;
    }

    private void setUpdatedData() {
        final UserPreference preference = new UserPreference(getApplicationContext());
        NetworkService networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        Call<RestLoggedData> restDataCall = networkService.restDataCall(FirebaseAuth.getInstance().getUid());
        restDataCall.enqueue(new Callback<RestLoggedData>() {
            @Override
            public void onResponse(Call<RestLoggedData> call, Response<RestLoggedData> response) {
                preference.setData(getBaseContext(), response.body());
                Log.e("TAG", "setUpdatedData--->");
            }

            @Override
            public void onFailure(Call<RestLoggedData> call, Throwable t) {
                Log.e("TAG", "setUpdatedData--->" + t);
            }
        });
    }
}