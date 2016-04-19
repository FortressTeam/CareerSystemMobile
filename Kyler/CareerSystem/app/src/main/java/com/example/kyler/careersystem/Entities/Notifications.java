package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Notifications {
    private int ID;
    private String notificationTitle;
    private String notificationDetail;
    private String notificationTime;
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
            if (jsonObject.has("notification_detail")) {
                this.notificationDetail = jsonObject.getString("notification_detail");
            }
            if (jsonObject.has("notification_time")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("notification_time"));
                this.notificationTime = new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if (jsonObject.has("is_seen")) {
                this.isSeen = jsonObject.getBoolean("is_seen");
            }
            if (jsonObject.has("user_id")) {
                this.userID = jsonObject.getInt("user_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Notifications(int ID, String notificationTitle, String notificationDetail, String notificationTime, boolean isSeen, int userID) {
        this.ID = ID;
        this.notificationTitle = notificationTitle;
        this.notificationDetail = notificationDetail;
        this.notificationTime = notificationTime;
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

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
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
