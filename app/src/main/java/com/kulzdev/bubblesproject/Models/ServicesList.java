package com.kulzdev.bubblesproject.Models;

public class ServicesList {

    private String serviceType; //change this to category
    private int imageId;
    private String name;
    private String serviceCheckboxId;

    public ServicesList(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public ServicesList(){


    }

    public ServicesList(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceCheckboxId() {
        return serviceCheckboxId;
    }

    public void setServiceCheckboxId(String serviceCheckboxId) {
        this.serviceCheckboxId = serviceCheckboxId;
    }
}
