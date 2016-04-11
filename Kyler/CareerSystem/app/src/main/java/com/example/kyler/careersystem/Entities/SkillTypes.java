package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 30/03/2016.
 */
public class SkillTypes {
    private int ID;
    private String skillTypeName;

    public SkillTypes(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("skill_type_name")) {
                this.skillTypeName = jsonObject.getString("skill_type_name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public SkillTypes(int ID, String skillTypeName) {
        this.ID = ID;
        this.skillTypeName = skillTypeName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSkillTypeName() {
        return skillTypeName;
    }

    public void setSkillTypeName(String skillTypeName) {
        this.skillTypeName = skillTypeName;
    }
}
