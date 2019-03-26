package com.kulzdev.bubblesproject.Models;

public class ServicesList {

    private String serviceType; //change this to category
    private int imageId;
    private String name;
    private String serviceCheckboxId;
    private String serviceCheckboxType;
    private int serviceTypeId;

    public ServicesList(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public ServicesList(String serviceCheckboxType) {
        this.serviceCheckboxType = serviceCheckboxType;
    }

    public ServicesList(){    }



    public String getServiceType() {
        return serviceType;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
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

    public String getServiceCheckboxType() {
        return serviceCheckboxType;
    }

    public void setServiceCheckboxType(String serviceCheckboxType) {
        this.serviceCheckboxType = serviceCheckboxType;
    }
}
