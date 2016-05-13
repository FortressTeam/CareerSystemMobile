package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class Notifications {
    private int ID;
    private String notificationTitle;
    private String notificationDetail;
    private int notificationType;
    private int notificationData;
    private boolean isSeen;
    private int userID;

    public Notifications(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("notification_title")) {
                this.notificationTitle = jsonObject.getString("notification_title");
            }
            if (jsonObject.has("notification_message")) {
                this.notificationDetail = jsonObject.getString("notification_message");
            }
            if (jsonObject.has("notification_type")) {
                this.notificationType = jsonObject.getInt("notification_type");
            }
            if (jsonObject.has("notification_object_id")) {
                this.notificationData = jsonObject.getInt("notification_object_id");
            }
            if (jsonObject.has("is_seen")) {
                this.isSeen = jsonObject.getBoolean("is_seen");
            }
            if (jsonObject.has("user_id")) {
                this.userID = jsonObject.getInt("user_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Notifications(int ID, String notificationTitle, String notificationDetail, int notificationType, int notificationData, boolean isSeen, int userID) {
        this.ID = ID;
        this.notificationTitle = notificationTitle;
        this.notificationDetail = notificationDetail;
        this.notificationType = notificationType;
        this.notificationData = notificationData;
        this.isSeen = isSeen;
        this.userID = userID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationDetail() {
        return notificationDetail;
    }

    public void setNotificationDetail(String notificationDetail) {
        this.notificationDetail = notificationDetail;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public int getNotificationData() {
        return notificationData;
    }

    public void setNotificationData(int notificationData) {
        this.notificationData = notificationData;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setIsSeen(boolean isSeen) {
        this.isSeen = isSeen;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
