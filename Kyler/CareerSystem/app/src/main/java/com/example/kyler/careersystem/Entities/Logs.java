package com.example.kyler.careersystem.Entities;

import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Logs {
    private int ID;
    private String logActivity;
    private String logDate;
    private int AdministratorID;

    public Logs(int ID, String logActivity, String logDate, int administratorID) {
        this.ID = ID;
        this.logActivity = logActivity;
        this.logDate = logDate;
        AdministratorID = administratorID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLogActivity() {
        return logActivity;
    }

    public void setLogActivity(String logActivity) {
        this.logActivity = logActivity;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public int getAdministratorID() {
        return AdministratorID;
    }

    public void setAdministratorID(int administratorID) {
        AdministratorID = administratorID;
    }
}
