
package com.messed.ircmbs.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuList {

    @SerializedName("items")
    @Expose
    private String items;
    @SerializedName("price")
    @Expose
    private String price;

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
