package com.kulzdev.bubblesproject.Models;

public class Appointment {

    private String mId;
    private String mClientId;
    private String mStylistId;
    private String mTime;
    private String mDate;


    private boolean mAccepted;

    public Appointment(String mId, String mClientId, String mStylistId, String mTime, String mDate) {
        this.mId = mId;
        this.mClientId = mClientId;
        this.mStylistId = mStylistId;
        this.mTime = mTime;
        this.mDate = mDate;
    }


    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmClientId() {
        return mClientId;
    }

    public void setmClientId(String mClientId) {
        this.mClientId = mClientId;
    }

    public String getmStylistId() {
        return mStylistId;
    }

    public void setmStylistId(String mStylistId) {
        this.mStylistId = mStylistId;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public boolean ismAccepted() {
        return mAccepted;
    }

    public void setmAccepted(boolean mAccepted) {
        this.mAccepted = mAccepted;
    }
}
