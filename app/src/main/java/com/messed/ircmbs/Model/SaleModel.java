package com.messed.ircmbs.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleModel {

    @SerializedName("tableno")
    @Expose
    private String tableno;
    @SerializedName("totalitems")
    @Expose
    private String totalitems;
    @SerializedName("totalprice")
    @Expose
    private String totalprice;
    @SerializedName("time")
    @Expose
    private String time;

    public String getTableno() {
        return tableno;
    }

    public void setTableno(String tableno) {
        this.tableno = tableno;
    }

    public String getTotalitems() {
        return totalitems;
    }

    public void setTotalitems(String totalitems) {
        this.totalitems = totalitems;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
