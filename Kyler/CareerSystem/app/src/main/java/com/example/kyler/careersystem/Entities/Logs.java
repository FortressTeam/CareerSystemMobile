package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Logs {
    private int ID;
    private String logActivity;
    private String logDate;
    private int AdministratorID;

    public Logs(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("log_activity")) {
                this.logActivity = jsonObject.getString("log_activity");
            }
            if (jsonObject.has("log_date")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("log_date"));
                this.logDate = new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if (jsonObject.has("administrator_id")) {
                this.AdministratorID = jsonObject.getInt("administrator_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

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
