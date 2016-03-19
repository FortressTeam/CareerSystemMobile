package com.example.kyler.careersystem.Entities;

import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Appointments {
    private int ID;
    private String appointmentName;
    private String appointmentDescription;
    private Date appointmentStart;
    private Date appointmentEnd;
    private String appointmentAddress;
    private int appointmentSMSAlert;
    private int hiringManagerID;

    public Appointments(int ID, String appointmentName, String appointmentDescription, Date appointmentStart, Date appointmentEnd, String appointmentAddress, int appointmentSMSAlert, int hiringManagerID) {
        this.ID = ID;
        this.appointmentName = appointmentName;
        this.appointmentDescription = appointmentDescription;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.appointmentAddress = appointmentAddress;
        this.appointmentSMSAlert = appointmentSMSAlert;
        this.hiringManagerID = hiringManagerID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public Date getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(Date appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public Date getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(Date appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    public String getAppointmentAddress() {
        return appointmentAddress;
    }

    public void setAppointmentAddress(String appointmentAddress) {
        this.appointmentAddress = appointmentAddress;
    }

    public int getAppointmentSMSAlert() {
        return appointmentSMSAlert;
    }

    public void setAppointmentSMSAlert(int appointmentSMSAlert) {
        this.appointmentSMSAlert = appointmentSMSAlert;
    }

    public int getHiringManagerID() {
        return hiringManagerID;
    }

    public void setHiringManagerID(int hiringManagerID) {
        this.hiringManagerID = hiringManagerID;
    }
}
