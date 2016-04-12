package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private String postDate;
    private boolean postStatus;
    private int categoryID;
    private int hiringManagerID;

    public Posts(JSONObject jsonObject){
        try {
            if(jsonObject.has("id")){
                this.ID=jsonObject.getInt("id");
            }
            if(jsonObject.has("post_title")){
                this.postTitle=jsonObject.getString("post_title");
            }
            if(jsonObject.has("post_content")){
                this.postContent=jsonObject.getString("post_content");
            }
            if(jsonObject.has("post_location")){
                this.postLocation=jsonObject.getString("post_location");
            }
            if(jsonObject.has("post_date")){
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("post_date"));
                this.postDate=new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if(jsonObject.has("post_status")){
                this.postStatus = jsonObject.getBoolean("post_status");
            }
            if(jsonObject.has("category_id")){
                this.categoryID=jsonObject.getInt("category_id");
            }
            if(jsonObject.has("hiring_manager_id")){
                this.hiringManagerID=jsonObject.getInt("hiring_manager_id");
            }
            if(jsonObject.has("post_salary")){
                this.postSalary=jsonObject.getInt("post_salary");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Posts(int ID, String postTitle, String postContent, int postSalary, String postLocation, String postDate, boolean postStatus, int categoryID, int hiringManagerID) {
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

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public boolean isPostStatus() {
        return postStatus;
    }

    public void setPostStatus(boolean postStatus) {
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
