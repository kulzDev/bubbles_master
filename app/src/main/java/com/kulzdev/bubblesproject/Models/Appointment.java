package com.kulzdev.bubblesproject.Models;

public class Appointment {

    private String mId;
    private String mClientId;
    private String mStylistId;
    private String mClientName;
    private String mStylistName;
    private String mStyleRequested;
    private String mTime;
    private String mDate;

    public Appointment(){

    }


    private boolean mAccepted;

    public Appointment(String mId, String mClientId, String mStylistId, String mClientName, String mStylistName, String mTime, String mDate) {
        this.mId = mId;
        this.mClientId = mClientId;
        this.mStylistId = mStylistId;
        this.mClientName = mClientName;
        this.mStylistName = mStylistName;
        this.mTime = mTime;
        this.mDate = mDate;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String mClientId) {
        this.mClientId = mClientId;
    }

    public String getmtylistId() {
        return mStylistId;
    }

    public void setStylistId(String mStylistId) {
        this.mStylistId = mStylistId;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public boolean isAccepted() {
        return mAccepted;
    }

    public void setAccepted(boolean mAccepted) {
        this.mAccepted = mAccepted;
    }


    public String getClientName() {
        return mClientName;
    }

    public void setClientName(String mClientName) {
        this.mClientName = mClientName;
    }

    public String getStylistName() {
        return mStylistName;
    }

    public void setStylistName(String mStylistName) {
        this.mStylistName = mStylistName;
    }


    public String getStyleRequested() {
        return mStyleRequested;
    }

    public void setStyleRequested(String mStyleRequested) {
        this.mStyleRequested = mStyleRequested;
    }


    @Override
    public String toString() {
        return "Appointment{" +
                "mId='" + mId + '\'' +
                ", mClientId='" + mClientId + '\'' +
                ", mStylistId='" + mStylistId + '\'' +
                ", mClientName='" + mClientName + '\'' +
                ", mStylistName='" + mStylistName + '\'' +
                ", mStyleRequested='" + mStyleRequested + '\'' +
                ", mTime='" + mTime + '\'' +
                ", mDate='" + mDate + '\'' +
                ", mAccepted=" + mAccepted +
                '}';
    }
}
