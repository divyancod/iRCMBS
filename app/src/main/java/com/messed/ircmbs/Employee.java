
package com.messed.ircmbs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Employee implements Serializable {

    @SerializedName("Sno")
    @Expose
    private String sno;
    @SerializedName("RestID")
    @Expose
    private String restID;
    @SerializedName("EmpID")
    @Expose
    private String empID;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("EmpPost")
    @Expose
    private String empPost;
    @SerializedName("EmpSalary")
    @Expose
    private String empSalary;
    @SerializedName("EmpGovtID")
    @Expose
    private String empGovtID;
    @SerializedName("EmpAddress")
    @Expose
    private String empAddress;
    @SerializedName("EmpPhone")
    @Expose
    private String empPhone;
    @SerializedName("EmpProfilePic")
    @Expose
    private String empProfilePic;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getRestID() {
        return restID;
    }

    public void setRestID(String restID) {
        this.restID = restID;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPost() {
        return empPost;
    }

    public void setEmpPost(String empPost) {
        this.empPost = empPost;
    }

    public String getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(String empSalary) {
        this.empSalary = empSalary;
    }

    public String getEmpGovtID() {
        return empGovtID;
    }

    public void setEmpGovtID(String empGovtID) {
        this.empGovtID = empGovtID;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getEmpProfilePic() {
        return empProfilePic;
    }

    public void setEmpProfilePic(String empProfilePic) {
        this.empProfilePic = empProfilePic;
    }

}
