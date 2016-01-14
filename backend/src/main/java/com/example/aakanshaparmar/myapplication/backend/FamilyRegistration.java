package com.example.aakanshaparmar.myapplication.backend;

/**
 * Created by aakanshaparmar on 14/1/2016.
 */
public class FamilyRegistration {

    //Family Registration Info
    private String fullName;
    private String phoneNo;
    private String address;
    private String eID;
    private String emailID;
    private String fID;
    private int commonPass;

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public String getEldID() {
        return eID;
    }

    public String getEmailID()  {return emailID;}

    public String getFamID() {return fID;}

    public int getCommonPass() {
        return commonPass;
    }

    public void setFullName(String name) {
        fullName = name;
    }

    public void setPhoneNo(String num) {
        phoneNo = num;
    }

    public void setAddress(String add) {
        address=add;
    }

    public void setEldID(String eldID) {
        eID = eldID;
    }

    public void setEmailID(String email) {emailID = email;}

    public void setFamID(String famID) {
        fID = famID;
    }

    public void setCommonPass(int pass) {
        commonPass = pass;
    }



}
