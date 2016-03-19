package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 16/03/2016.
 */
public class AppointmentsHasApplicants {
    private int appointmentID;
    private int applicantID;
    private int userRating;

    public AppointmentsHasApplicants(int appointmentID, int applicantID, int userRating) {
        this.appointmentID = appointmentID;
        this.applicantID = applicantID;
        this.userRating = userRating;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }
}
