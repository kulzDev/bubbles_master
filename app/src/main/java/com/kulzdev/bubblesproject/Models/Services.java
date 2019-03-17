package com.kulzdev.bubblesproject.Models;

public class Services {
    private String mServices;
    private int mServiceId;

    public Services(String mServices, int mServiceId) {
        this.mServices = mServices;
        this.mServiceId = mServiceId;
    }

    public String getmServices() {
        return mServices;
    }

    public void setmServices(String mServices) {
        this.mServices = mServices;
    }

    public int getmServiceId() {
        return mServiceId;
    }

    public void setmServiceId(int mServiceId) {
        this.mServiceId = mServiceId;
    }
}
