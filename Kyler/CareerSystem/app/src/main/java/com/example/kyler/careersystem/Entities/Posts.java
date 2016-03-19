package com.example.kyler.careersystem.Entities;

import java.util.Date;

/**
 * Created by kyler on 09/03/2016.
 */
public class Posts {
    private int ID;
    private String postTitle;
    private String postContent;
    private int postSalary;
    private String postLocation;
    private Date postDate;
    private int postStatus;
    private int categoryID;
    private int hiringManagerID;

    public Posts(int ID, String postTitle, String postContent, int postSalary, String postLocation, Date postDate, int postStatus, int categoryID, int hiringManagerID) {
        this.ID = ID;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postSalary = postSalary;
        this.postLocation = postLocation;
        this.postDate = postDate;
        this.postStatus = postStatus;
        this.categoryID = categoryID;
        this.hiringManagerID = hiringManagerID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public int getPostSalary() {
        return postSalary;
    }

    public void setPostSalary(int postSalary) {
        this.postSalary = postSalary;
    }

    public String getPostLocation() {
        return postLocation;
    }

    public void setPostLocation(String postLocation) {
        this.postLocation = postLocation;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public int getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(int postStatus) {
        this.postStatus = postStatus;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getHiringManagerID() {
        return hiringManagerID;
    }

    public void setHiringManagerID(int hiringManagerID) {
        this.hiringManagerID = hiringManagerID;
    }
}
