package com.kulzdev.bubblesproject.Models;

import java.util.List;

public class User {

    private String mFullName;
    private String email;
    private String mUserType;
    private String mPhoneNumber;
    private int mProfileImage;
    private String mUserAddress;
    private String id;
    private List<Services> mServices;



    public User() {

    }

    public User(String mFullName, int mProfileImage, List<Services> mServices) {
        this.mFullName = mFullName;
        this.mProfileImage = mProfileImage;
        this.mServices = mServices;
    }

    public User(String fullName, String email, String clientType) {
        this.mFullName = fullName;
        this.email = email;
        this.mUserType = clientType;

    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String mUserType) {
        this.mUserType = mUserType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Services> getServices() {
        return mServices;
    }

    public void setServices(List<Services> mServices) {
        this.mServices = mServices;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public int getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(int mProfileImage) {
        this.mProfileImage = mProfileImage;
    }
    public String getUserAddress() {
        return mUserAddress;
    }

    public void setUserAddress(String mUserAddress) {
        this.mUserAddress = mUserAddress;
    }
}
