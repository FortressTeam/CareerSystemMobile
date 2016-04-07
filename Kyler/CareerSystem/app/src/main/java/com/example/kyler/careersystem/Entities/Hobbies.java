package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class Hobbies {
    private int ID;
    private String hobbyName;

    public Hobbies(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("hobby_name")) {
                this.hobbyName = jsonObject.getString("hobby_name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Hobbies(int ID, String hobbyName) {
        this.ID = ID;
        this.hobbyName = hobbyName;
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
}
