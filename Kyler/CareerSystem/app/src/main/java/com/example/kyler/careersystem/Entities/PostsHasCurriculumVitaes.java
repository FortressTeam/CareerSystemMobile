package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 14/03/2016.
 */
public class PostsHasCurriculumVitaes {
    private int PostID;
    private int CurriculumVitaes;

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
