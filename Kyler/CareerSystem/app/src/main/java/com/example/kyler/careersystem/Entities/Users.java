package com.example.kyler.careersystem.Entities;

import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Users {
    private int ID;
    private String username;
    private String password;
    private Date userRegistered;
    private String userEmail;
    private int userStatus;
    private String userActivationKey;
    private String userAvatar;
    private int groupID;

    public Users(int ID, String username, String password, Date userRegistered, String userEmail, int userStatus, String userActivationKey, String userAvatar, int groupID) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.userRegistered = userRegistered;
        this.userEmail = userEmail;
        this.userStatus = userStatus;
        this.userActivationKey = userActivationKey;
        this.userAvatar = userAvatar;
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

    public Date getUserRegistered() {
        return userRegistered;
    }

    public void setUserRegistered(Date userRegistered) {
        this.userRegistered = userRegistered;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
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

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
