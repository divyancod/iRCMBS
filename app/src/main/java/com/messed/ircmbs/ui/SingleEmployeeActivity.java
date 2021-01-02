package com.messed.ircmbs.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.messed.ircmbs.Model.Employee;
import com.messed.ircmbs.Model.SignUpCall;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;
import com.messed.ircmbs.R;
import com.messed.ircmbs.databinding.ActivitySingleEmployeeBinding;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleEmployeeActivity extends AppCompatActivity {

    ActivitySingleEmployeeBinding binding;
    Employee employee;
    Uri imageUri;
    Uri picture;
    public static final String EMPLOYEE="employeekey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_single_employee);
        employee=(Employee) getIntent().getSerializableExtra(EMPLOYEE);
        setData();
        binding.updateBtn.setOnClickListener(v -> uploadProfilePic());
        binding.empProfilePic.setOnClickListener(v -> {
            new LargePictureView(getApplicationContext(),v,employee.getEmpProfilePic());
        });
        binding.profilepiceditbtn.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT).setType("image/*");
            startActivityForResult(intent, 1);
        });
    }
    private void setData()
    {
        binding.employeeFullname.setText(employee.getEmpName());
        binding.employeePost.setText(employee.getEmpPost());
        binding.employeeSalary.setText(employee.getEmpSalary());
        binding.employeeGovid.setText(employee.getEmpGovtID());
        binding.employeeAddress.setText(employee.getEmpAddress());
        binding.employeePhone.setText(employee.getEmpPhone());
        Glide.with(getApplicationContext()).load(employee.getEmpProfilePic()).placeholder(R.drawable.templogo).error(R.drawable.templogo).into(binding.empProfilePic);
    }
    private void updateEmployeeDetails(String profilepic)
    {
        String name=binding.employeeFullname.getText().toString();
        String post=binding.employeePost.getText().toString();
        String salary=binding.employeeSalary.getText().toString();
        String govid=binding.employeeGovid.getText().toString();
        String address=binding.employeeAddress.getText().toString();
        String phone=binding.employeePhone.getText().toString();
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        Call<List<Employee>> call=networkService.allInOneSingleEmployee("1",employee.getEmpID(),name,post,salary,govid,address,phone,profilepic);
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.e("TAG",""+t);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageUri = data.getData();
            startCrop(imageUri);
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri uri = UCrop.getOutput(data);
            binding.empProfilePic.setImageURI(uri);
        }
    }
    private void startCrop(@NonNull Uri uri) {
        String des = "pic" + System.currentTimeMillis() + ".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), des)));
        uCrop.withAspectRatio(1, 1);
        uCrop.withMaxResultSize(450, 450);
        uCrop.withOptions(getCropOptions());
        uCrop.start(SingleEmployeeActivity.this);
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

    private void uploadProfilePic() {
        if (picture == null) {
            updateEmployeeDetails(null);
            return;
        }
        final File file = new File(picture.getPath());
        NetworkService networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        Call<SignUpCall> callll = networkService.tester(MultipartBody.Part.createFormData("file", file.getName(), fbody));
        callll.enqueue(new Callback<SignUpCall>() {
            @Override
            public void onResponse(Call<SignUpCall> call, Response<SignUpCall> response) {
                updateEmployeeDetails("http://divyanshu123.000webhostapp.com/uploads/"+file.getName());
            }

            @Override
            public void onFailure(Call<SignUpCall> call, Throwable t) {
                Log.e("uploadProfilePic() - >", "" + t);
            }
        });
    }
}