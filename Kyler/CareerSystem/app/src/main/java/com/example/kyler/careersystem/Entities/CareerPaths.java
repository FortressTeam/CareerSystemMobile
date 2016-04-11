package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class CareerPaths {
    private int ID;
    private String careerPathName;
    private String careerPathDescription;

    public CareerPaths(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("career_path_name")) {
                this.careerPathName = jsonObject.getString("career_path_name");
            }
            if (jsonObject.has("career_path_description")) {
                this.careerPathDescription = jsonObject.getString("career_path_description");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CareerPaths(int ID, String careerPathName, String careerPathDescription) {
        this.ID = ID;
        this.careerPathName = careerPathName;
        this.careerPathDescription = careerPathDescription;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCareerPathName() {
        return careerPathName;
    }

    public void setCareerPathName(String careerPathName) {
        this.careerPathName = careerPathName;
    }

    public String getCareerPathDescription() {
        return careerPathDescription;
    }

    public void setCareerPathDescription(String careerPathDescription) {
        this.careerPathDescription = careerPathDescription;
    }
}
