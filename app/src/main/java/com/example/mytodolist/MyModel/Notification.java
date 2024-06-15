package com.example.mytodolist.MyModel;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Notification implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String notificationTitle;
    private String notificationBody;
    private String notificationSender;

    public Notification(){
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

    public String getNotificationSender() {
        return notificationSender;
    }

    public void setNotificationSender(String notificationSender) {
        this.notificationSender = notificationSender;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationTitle='" + notificationTitle + '\'' +
                ", notificationBody='" + notificationBody + '\'' +
                ", notificationSender='" + notificationSender + '\'' +
                '}';
    }
}
