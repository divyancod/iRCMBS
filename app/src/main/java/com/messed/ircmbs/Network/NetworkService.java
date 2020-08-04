package com.messed.ircmbs.Network;

import androidx.annotation.NonNull;

import com.messed.ircmbs.Model.EmpDataModel;
import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.Model.RestLoggedData;
import com.messed.ircmbs.Model.SaleModel;
import com.messed.ircmbs.Model.SignUpCall;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */

public interface NetworkService {
    @POST("/try/rest_signup.php")
    @FormUrlEncoded
    Call<SignUpCall> signupCall(
            @Field("restname") String restname,
            @Field("restuid") String restuid,
            @Field("owner") String owner,
            @Field("address") String address,
            @Field("cloudkitchen") String cloud,
            @Field("tables") String tables,
            @Field("employees") String employees);

    @POST("/try/rest_profile.php")
    @FormUrlEncoded
    Call<RestLoggedData> restDataCall(
            @Field("restuid") String restuid);

    @GET("/try/rest_menu_fetch.php")
    Call<List<MenuList>> getAllMenu();

    @POST("/try/rest_order_history.php")
    @FormUrlEncoded
    Call<SignUpCall> restOrders(
            @Field("items") String items,
            @Field("total") String total,
            @Field("table") String table,
            @Field("resid") String resid);

    @POST("/try/rest_order_history_get.php")
    @FormUrlEncoded
    Call<List<SaleModel>> salesList(
            @Field("resid") String resid);

    @POST("/try/rest_employee_add.php")
    @FormUrlEncoded
    Call<SignUpCall> addEmployee(
            @Field("resid") String resid,
            @Field("empname") String name,
            @Field("emppost") String post,
            @Field("empsalary") String salary,
            @Field("empgovid") String govid,
            @Field("empaddress") String address,
            @Field("empphone") String empphone);
    @POST("/try/rest_employee_get.php")
    @FormUrlEncoded
    Call<List<EmpDataModel>> getEmployee(
            @Field("resid") String resid);
}
