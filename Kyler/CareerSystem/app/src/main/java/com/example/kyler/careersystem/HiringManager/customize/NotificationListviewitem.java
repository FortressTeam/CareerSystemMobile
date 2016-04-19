package com.example.kyler.careersystem.HiringManager.customize;

import com.example.kyler.careersystem.Entities.Notifications;

/**
 * Created by kyler on 19/04/2016.
 */
public class NotificationListviewitem {
    private String notificationImage;
    private Notifications notifications;

    public NotificationListviewitem(String notificationImage, Notifications notifications) {
        this.notificationImage = notificationImage;
        this.notifications = notifications;
    }

    public String getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(String notificationImage) {
        this.notificationImage = notificationImage;
    }

    public Notifications getNotifications() {
        return notifications;
    }

    public void setNotifications(Notifications notifications) {
        this.notifications = notifications;
    }
}
