package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class Majors {
    private int ID;
    private String MajorName;
    private String MajorDescription;

    public Majors(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("major_name")) {
                this.MajorName = jsonObject.getString("major_name");
            }
            if (jsonObject.has("major_description")) {
                this.MajorDescription = jsonObject.getString("major_description");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Majors(int ID, String majorName, String majorDescription) {
        this.ID = ID;
        MajorName = majorName;
        MajorDescription = majorDescription;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMajorName() {
        return MajorName;
    }

    public void setMajorName(String majorName) {
        MajorName = majorName;
    }

    public String getMajorDescription() {
        return MajorDescription;
    }

    public void setMajorDescription(String majorDescription) {
        MajorDescription = majorDescription;
    }
}
