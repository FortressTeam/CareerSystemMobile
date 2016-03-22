package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 16/03/2016.
 */
public class AppointmentsHasApplicants {
    private int appointmentID;
    private int applicantID;
    private int userRating;

    public AppointmentsHasApplicants(JSONObject jsonObject){
        try {
            if (jsonObject.has("appointment_id")) {
                this.appointmentID = jsonObject.getInt("appointment_id");
            }
            if (jsonObject.has("applicant_id")) {
                this.applicantID = jsonObject.getInt("applicant_id");
            }
            if (jsonObject.has("user_rating")) {
                this.userRating = jsonObject.getInt("user_rating");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
