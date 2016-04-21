package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Users {
    private int ID;
    private String username;
    private String password;
    private String userRegistered;
    private String userEmail;
    private boolean userStatus;
    private String userActivationKey;
    private String userAvatar;
    private String userAndroidToken;
    private int groupID;

    public Users(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("username")) {
                this.username = jsonObject.getString("username");
            }
            if (jsonObject.has("user_registered")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("user_registered"));
                this.userRegistered = new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if (jsonObject.has("user_email")) {
                this.userEmail = jsonObject.getString("user_email");
            }
            if (jsonObject.has("user_status")) {
                this.userStatus = jsonObject.getBoolean("user_status");
            }
            if (jsonObject.has("user_activation_key")) {
                this.userActivationKey = jsonObject.getString("user_activation_key");
            }
            if (jsonObject.has("user_avatar")) {
                this.userAvatar = jsonObject.getString("user_avatar");
            }
            if (jsonObject.has("user_android_token")) {
                this.userAndroidToken = jsonObject.getString("user_android_token");
            }
            if (jsonObject.has("group_id")) {
                this.groupID = jsonObject.getInt("group_id");
            }
            if (jsonObject.has("user_password")) {
                this.password = jsonObject.getString("user_password");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Users(int ID, String username, String password, String userRegistered, String userEmail, boolean userStatus, String userActivationKey, String userAvatar, String userAndroidToken, int groupID) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.userRegistered = userRegistered;
        this.userEmail = userEmail;
        this.userStatus = userStatus;
        this.userActivationKey = userActivationKey;
        this.userAvatar = userAvatar;
        this.userAndroidToken = userAndroidToken;
        this.groupID = groupID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRegistered() {
        return userRegistered;
    }

    public void setUserRegistered(String userRegistered) {
        this.userRegistered = userRegistered;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserActivationKey() {
        return userActivationKey;
    }

    public void setUserActivationKey(String userActivationKey) {
        this.userActivationKey = userActivationKey;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserAndroidToken() {
        return userAndroidToken;
    }

    public void setUserAndroidToken(String userAndroidToken) {
        this.userAndroidToken = userAndroidToken;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
