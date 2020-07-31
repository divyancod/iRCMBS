package com.messed.ircmbs.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */

public class RestLoggedData {

    @SerializedName("resid")
    @Expose
    private String resid;
    @SerializedName("restname")
    @Expose
    private String restname;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("cloudornot")
    @Expose
    private String cloudornot;
    @SerializedName("tables")
    @Expose
    private String tables;
    @SerializedName("employees")
    @Expose
    private String employees;

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getRestname() {
        return restname;
    }

    public void setRestname(String restname) {
        this.restname = restname;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCloudornot() {
        return cloudornot;
    }

    public void setCloudornot(String cloudornot) {
        this.cloudornot = cloudornot;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }

    public String getEmployees() {
        return employees;
    }

    public void setEmployees(String employees) {
        this.employees = employees;
    }

}