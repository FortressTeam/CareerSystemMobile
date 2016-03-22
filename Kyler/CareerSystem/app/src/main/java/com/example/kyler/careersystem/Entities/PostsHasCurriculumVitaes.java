package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class PostsHasCurriculumVitaes {
    private int PostID;
    private int CurriculumVitaes;

    public PostsHasCurriculumVitaes(JSONObject jsonObject){
        try {
            if (jsonObject.has("post_id")) {
                this.PostID = jsonObject.getInt("post_id");
            }
            if (jsonObject.has("curriculum_vitae_id")) {
                this.CurriculumVitaes = jsonObject.getInt("curriculum_vitae_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public PostsHasCurriculumVitaes(int postID, int curriculumVitaes) {
        PostID = postID;
        CurriculumVitaes = curriculumVitaes;
    }

    public int getPostID() {
        return PostID;
    }

    public void setPostID(int postID) {
        PostID = postID;
    }

    public int getCurriculumVitaes() {
        return CurriculumVitaes;
    }

    public void setCurriculumVitaes(int curriculumVitaes) {
        CurriculumVitaes = curriculumVitaes;
    }
}
