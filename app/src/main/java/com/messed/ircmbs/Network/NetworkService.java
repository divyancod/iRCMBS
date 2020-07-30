package com.messed.ircmbs.Network;

import androidx.annotation.NonNull;

import com.messed.ircmbs.Model.RestLoggedData;
import com.messed.ircmbs.Model.SignUpCall;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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

}
