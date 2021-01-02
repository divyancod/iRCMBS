package com.messed.ircmbs.Network;

import com.messed.ircmbs.Model.Employee;
import com.messed.ircmbs.Model.EmployeeRecordResponse;
import com.messed.ircmbs.Model.EmpDataModel;
import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.Model.RestLoggedData;
import com.messed.ircmbs.Model.SaleModel;
import com.messed.ircmbs.Model.SignUpCall;
import com.messed.ircmbs.Model.SalesRecordModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */

public interface NetworkService {
    @POST("/rest_signup.php")
    @FormUrlEncoded
    Call<SignUpCall> signupCall(
            @Field("restname") String restname,
            @Field("restuid") String restuid,
            @Field("owner") String owner,
            @Field("address") String address,
            @Field("cloudkitchen") String cloud,
            @Field("tables") String tables,
            @Field("employees") String employees,
            @Field("phone") String phone,
            @Field("email") String email
    );

    @POST("/rest_profile.php")
    @FormUrlEncoded
    Call<RestLoggedData> restDataCall(
            @Field("restuid") String restuid);

    @GET("/rest_menu_fetch.php")
    Call<List<MenuList>> getAllMenu();

    @POST("/rest_order_history.php")
    @FormUrlEncoded
    Call<SignUpCall> restOrders(
            @Field("items") String items,
            @Field("total") String total,
            @Field("table") String table,
            @Field("resid") String resid);

    @POST("/rest_order_history_get.php")
    @FormUrlEncoded
    Call<List<SaleModel>> salesList(
            @Field("resid") String resid);

    @POST("/rest_employee_add_new.php")
    @FormUrlEncoded
    Call<SignUpCall> addEmployee(
            @Field("resid") String resid,
            @Field("empname") String name,
            @Field("emppost") String post,
            @Field("empsalary") String salary,
            @Field("empgovid") String govid,
            @Field("empaddress") String address,
            @Field("empphone") String empphone);

    @POST("/rest_employee_get.php")
    @FormUrlEncoded
    Call<List<EmpDataModel>> getEmployee(
            @Field("resid") String resid);

    @POST("/usercheck.php")
    @FormUrlEncoded
    Call<SignUpCall> userCheck(@Field("userid") String custid);

    @POST("/update_rest_details.php")
    @FormUrlEncoded
    Call<SignUpCall> updateProfileCall(
            @Field("restname") String restname,
            @Field("restuid") String restuid,
            @Field("owner") String owner,
            @Field("address") String address,
            @Field("cloudkitchen") String cloud,
            @Field("tables") String tables,
            @Field("employees") String employees,
            @Field("phone") String phone
    );
    @Multipart
    @POST("/uptest.php?upload")
    Call<SignUpCall> tester(@Part MultipartBody.Part userProfileImage);

    @POST("/update_profile_pic.php")
    @FormUrlEncoded
    Call<SignUpCall> updateProfilePic(@Field("userid") String custid,@Field("profilepic")String profilepic);

    @POST("/rest_employee_update_new.php")
    @FormUrlEncoded
    Call<EmployeeRecordResponse> allInOneEmployee(@Field("requireupdate") String requireupdate,
                                                  @Field("resid") String resid,
                                                  @Field("empid") String empid,
                                                  @Field("empname") String name,
                                                  @Field("emppost") String post,
                                                  @Field("empsalary") String salary,
                                                  @Field("empgovid") String govid,
                                                  @Field("empaddress") String address,
                                                  @Field("empphone") String empphone);
    @POST("/rest_employee_update_new.php")
    @FormUrlEncoded
    Call<List<Employee>> allInOneSingleEmployee(@Field("requireupdate") String requireupdate,
                                    @Field("empid") String empid,
                                    @Field("empname") String name,
                                    @Field("emppost") String post,
                                    @Field("empsalary") String salary,
                                    @Field("empgovid") String govid,
                                    @Field("empaddress") String address,
                                    @Field("empphone") String empphone, @Field("empprofilepic")String profilepic);

    @GET("/test.php")
    Call<List<SalesRecordModel>> getSales(@QueryMap Map<String,String> map);
}
