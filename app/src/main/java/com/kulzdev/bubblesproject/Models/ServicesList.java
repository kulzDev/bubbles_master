package com.kulzdev.bubblesproject.Models;

import java.util.List;

public class ServicesList {


    public ServicesList(){

    }

    private String mServiceCategory;
    private List<String> mServiceList;

    public ServicesList(String mServiceCategory, List<String> mServiceList) {
        this.mServiceCategory = mServiceCategory;
        this.mServiceList = mServiceList;
    }

    public String getServiceCategory() {
        return mServiceCategory;
    }

    public void setServiceCategory(String mServiceCategory) {
        this.mServiceCategory = mServiceCategory;
    }

    public List<String> getServiceList() {
        return mServiceList;
    }

    public void setServiceList(List<String> mServiceList) {
        this.mServiceList = mServiceList;
    }

    @Override
    public String toString() {
        return "ServicesList{" +
                "mServiceCategory='" + mServiceCategory + '\'' +
                ", mServiceList=" + mServiceList +
                '}';
    }
}
