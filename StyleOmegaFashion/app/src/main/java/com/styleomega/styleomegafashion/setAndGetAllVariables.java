package com.styleomega.styleomegafashion;

/**
 * Created by HP on 8/23/2017.
 */

public class setAndGetAllVariables {
    String cusid,Name,pwd,itmid,email;
    int telNo;

    public void setCusid(String cusid){
        this.cusid=cusid;
    }
    public String getCusid(){
        return this.cusid;
    }
    public void setCusName(String name){
        this.Name=name;
    }
    public String getCusName(){
        return this.Name;
    }
    public void setPwd(String pwd){
        this.pwd=pwd;
    }
    public String getPwd(){
        return this.pwd;
    }
    public void setItmID(String itmid){
        this.itmid=itmid;
    }
    public String getItmID(){
        return this.itmid;
    }

    public void setTelNo(int TelNo){
        this.telNo=TelNo;
    }
    public int getTelNo(){
        return this.telNo;
    }

    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return this.email;
    }
}
