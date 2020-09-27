package com.messed.ircmbs.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.messed.ircmbs.Model.RestLoggedData;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class UserPreference {
    public  SharedPreferences preferences;
    private int init;
    private String resid;
    private String restname;
    private String owner;
    private String address;
    private String cloudornot;
    private String tables;
    private String employees;
    public UserPreference(Context context)
    {
        preferences=context.getSharedPreferences("MySharedPref",0);
    }
    public void setData(Context context, RestLoggedData restLoggedData)
    {
        if(preferences==null)
            preferences=context.getSharedPreferences("MySharedPref",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("restname",restLoggedData.getRestname());
        editor.putString("owner",restLoggedData.getOwner());
        editor.putString("resid",restLoggedData.getResid());
        editor.putString("address",restLoggedData.getAddress());
        editor.putString("cloudornot",restLoggedData.getCloudornot());
        editor.putString("tables",restLoggedData.getTables());
        editor.putString("employees",restLoggedData.getEmployees());
        editor.putInt("init",1);
        editor.commit();
    }
    public void setAccountStatus(Context context,String status)
    {
        //1- deactive 0-active
        if(preferences!=null)
        {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("accountstatus",status);
            editor.commit();
        }
    }
    public String getAccountStatus()
    {
        return preferences.getString("accountstatus","0");
    }
    public static int getInit(Context context)
    {
        return context.getSharedPreferences("MySharedPref",0).getInt("init",0);
    }

    public String getResid() {
        resid=preferences.getString("resid",null);
        return resid;
    }

    public String getRestname() {
        restname=preferences.getString("restname",null);
        return restname;
    }

    public String getOwner() {
        owner=preferences.getString("owner",null);
        return owner;
    }

    public String getAddress() {
        address=preferences.getString("address",null);
        return address;
    }

    public String getCloudornot() {
        cloudornot=preferences.getString("cloudornot",null);
        return cloudornot;
    }

    public String getTables() {
        tables=preferences.getString("tables",null);
        return tables;
    }

    public String getEmployees() {
        employees=preferences.getString("employees",null);
        return employees;
    }

    public void delete()
    {
        preferences.edit().clear().commit();
    }
}
