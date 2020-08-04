package com.messed.ircmbs.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmpDataModel {
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("empname")
    @Expose
    private String empname;
    @SerializedName("emppost")
    @Expose
    private String emppost;
    @SerializedName("empsalary")
    @Expose
    private String empsalary;
    @SerializedName("empgovtid")
    @Expose
    private String empgovtid;
    @SerializedName("empaddress")
    @Expose
    private String empaddress;
    @SerializedName("empphone")
    @Expose
    private String empphone;

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getEmppost() {
        return emppost;
    }

    public void setEmppost(String emppost) {
        this.emppost = emppost;
    }

    public String getEmpsalary() {
        return empsalary;
    }

    public void setEmpsalary(String empsalary) {
        this.empsalary = empsalary;
    }

    public String getEmpgovtid() {
        return empgovtid;
    }

    public void setEmpgovtid(String empgovtid) {
        this.empgovtid = empgovtid;
    }

    public String getEmpaddress() {
        return empaddress;
    }

    public void setEmpaddress(String empaddress) {
        this.empaddress = empaddress;
    }

    public String getEmpphone() {
        return empphone;
    }

    public void setEmpphone(String empphone) {
        this.empphone = empphone;
    }
}
