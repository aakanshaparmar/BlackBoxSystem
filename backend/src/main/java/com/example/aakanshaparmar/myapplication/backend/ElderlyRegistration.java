package com.example.aakanshaparmar.myapplication.backend;

/**
 * Created by aakanshaparmar on 14/1/2016.
 */
public class ElderlyRegistration {

    //Elderly Registration Info
    private String fullName;
    private String phoneNo;
    private String address;
    private String eID;
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

    public String getEID() {
        return eID;
    }

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

    public void setEid(String eldID) {
        eID = eldID;
    }

    public void setCommonPass(int pass) {
        commonPass = pass;
    }





}
