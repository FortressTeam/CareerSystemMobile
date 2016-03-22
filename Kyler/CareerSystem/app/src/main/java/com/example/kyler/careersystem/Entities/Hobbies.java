package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class Hobbies {
    private int ID;
    private String hobbyName;
    private String hobbyDescription;

    public Hobbies(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("hobby_name")) {
                this.hobbyName = jsonObject.getString("hobby_name");
            }
            if (jsonObject.has("hobby_description")) {
                this.hobbyDescription = jsonObject.getString("hobby_description");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Hobbies(int ID, String hobbyName, String hobbyDescription) {
        this.ID = ID;
        this.hobbyName = hobbyName;
        this.hobbyDescription = hobbyDescription;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    public String getHobbyDescription() {
        return hobbyDescription;
    }

    public void setHobbyDescription(String hobbyDescription) {
        this.hobbyDescription = hobbyDescription;
    }
}
