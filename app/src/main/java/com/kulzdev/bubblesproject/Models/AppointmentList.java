package com.kulzdev.bubblesproject.Models;

import java.util.List;

public class AppointmentList {

    private List<Appointment> mAppointment;

    public AppointmentList(List<Appointment> mAppointment) {
        this.mAppointment = mAppointment;
    }

    public List<Appointment> getmAppointment() {
        return mAppointment;
    }

    public void setmAppointment(List<Appointment> mAppointment) {
        this.mAppointment = mAppointment;
    }

    @Override
    public String toString() {
        return "AppointmentList{" +
                "mAppointment=" + mAppointment +
                '}';
    }
}
