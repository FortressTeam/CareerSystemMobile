package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class Skills {
    private int ID;
    private String skillName;
    private String skillDescription;

    public Skills(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("skill_name")) {
                this.skillName = jsonObject.getString("skill_name");
            }
            if (jsonObject.has("skill_description")) {
                this.skillDescription = jsonObject.getString("skill_description");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Skills(int ID, String skillName, String skillDescription) {
        this.ID = ID;
        this.skillName = skillName;
        this.skillDescription = skillDescription;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillDescription() {
        return skillDescription;
    }

    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }
}
