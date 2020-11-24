
package com.messed.ircmbs.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */

public class MenuList {

    @SerializedName("items")
    @Expose
    private String items;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("itemtime")
    @Expose
    private String itemtime;
    @SerializedName("curravail")
    @Expose
    private String curravail;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("itemdesc")
    @Expose
    private String itemdesc;

    public String getItemdesc() {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    public String getItemtime() {
        return itemtime;
    }

    public void setItemtime(String itemtime) {
        this.itemtime = itemtime;
    }

    public String getCurravail() {
        return curravail;
    }

    public void setCurravail(String curravail) {
        this.curravail = curravail;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
