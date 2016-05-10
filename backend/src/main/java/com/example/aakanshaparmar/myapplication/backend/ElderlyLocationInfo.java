package com.example.aakanshaparmar.myapplication.backend;

/**
 * Created by aakanshaparmar on 15/1/2016.
 */
public class ElderlyLocationInfo{

    float latitude;
    float longitude;
    int locID;
    String eID;

    public float getLatitude()
    {
        return latitude;
    }

    public float getLongitude()
    {
        return longitude;
    }

    public int getLocID()
    {
        return locID;
    }

    public String getEldID()
    {
        return eID;
    }


    public void setLatitude(float lat)
    {
        latitude = lat;
    }

    public void setLongitude(float longi)
    {
        longitude = longi;
    }

    public void setLocID(int lID)
    {
        locID = lID;
    }

    public void setEldID(String eldID)
    {
        eID = eldID;
    }



}
