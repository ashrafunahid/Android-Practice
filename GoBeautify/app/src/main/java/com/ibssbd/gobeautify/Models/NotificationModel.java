package com.ibssbd.gobeautify.Models;

public class NotificationModel {

    int notificationImage;
    String notificationTitle;
    String notificationBody;

    public NotificationModel(int notificationImage, String notificationTitle, String notificationBody) {
        this.notificationImage = notificationImage;
        this.notificationTitle = notificationTitle;
        this.notificationBody = notificationBody;
    }

    public int getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(int notificationImage) {
        this.notificationImage = notificationImage;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }
}
