package com.messed.ircmbs.ViewModel;

import com.messed.ircmbs.Model.MenuList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MenuCall {
////http://divyanshu123.000webhostapp.com/apicall.php
    @GET("/try/rest_menu_fetch.php")
    Call<List<MenuList>> getMenu();
}
