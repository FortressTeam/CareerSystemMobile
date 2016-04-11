package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Appointments {
    private int ID;
    private String appointmentName;
    private String appointmentDescription;
    private String appointmentStart;
    private String appointmentEnd;
    private String appointmentAddress;
    private int appointmentSMSAlert;
    private int hiringManagerID;

    public Appointments(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("appointment_name")) {
                this.appointmentName = jsonObject.getString("appointment_name");
            }
            if (jsonObject.has("appointment_description")) {
                this.appointmentDescription = jsonObject.getString("appointment_description");
            }
            if (jsonObject.has("appointment_start")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("appointment_start"));
                this.appointmentStart = new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if (jsonObject.has("appointment_end")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("appointment_end"));
                this.appointmentEnd = new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if (jsonObject.has("appointment_address")) {
                this.appointmentAddress = jsonObject.getString("appointment_address");
            }
            if (jsonObject.has("appointment_SMS_alert")) {
                this.appointmentSMSAlert = jsonObject.getInt("appointment_SMS_alert");
            }
            if (jsonObject.has("hiring_manager_id")) {
                this.hiringManagerID = jsonObject.getInt("hiring_manager_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Appointments(int ID, String appointmentName, String appointmentDescription, String appointmentStart, String appointmentEnd, String appointmentAddress, int appointmentSMSAlert, int hiringManagerID) {
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

    public String getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(String appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public String getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(String appointmentEnd) {
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
