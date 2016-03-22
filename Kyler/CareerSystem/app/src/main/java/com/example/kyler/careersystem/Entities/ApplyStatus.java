package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 22/03/2016.
 */
public class ApplyStatus {
    private int ID;
    private String statusName;

    public ApplyStatus(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("status_name")) {
                this.statusName = jsonObject.getString("status_name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ApplyStatus(int ID, String statusName) {
        this.ID = ID;
        this.statusName = statusName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
