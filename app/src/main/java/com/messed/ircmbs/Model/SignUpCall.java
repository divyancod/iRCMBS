package com.messed.ircmbs.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class SignUpCall {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("restdata")
    @Expose
    private RestLoggedData restLoggedData;

    public RestLoggedData getRestLoggedData() {
        return restLoggedData;
    }

    public void setRestLoggedData(RestLoggedData restLoggedData) {
        this.restLoggedData = restLoggedData;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
