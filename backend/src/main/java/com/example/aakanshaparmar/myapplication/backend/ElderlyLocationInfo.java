package com.example.aakanshaparmar.myapplication.backend;

import java.sql.Date;

/**
 * Created by aakanshaparmar on 15/1/2016.
 */
public class ElderlyLocationInfo {

    float latitude;
    float longitude;
    String locID;
    String eID;
    Date locDate;

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getLocID() {
        return locID;
    }

    public String getEldID() {
        return eID;
    }

    public Date getLocDate() {return locDate;}

    public void setLatitude(float lat) {
        latitude = lat;
    }

    public void setLongitude(float longi) {
        longitude = longi;
    }

    public void setLocID(String lID) {
        locID = lID;
    }

    public void setEldID(String eldID) {
        eID = eldID;
    }

    public void setLocDate(Date lDate) { locDate = lDate; }


}
