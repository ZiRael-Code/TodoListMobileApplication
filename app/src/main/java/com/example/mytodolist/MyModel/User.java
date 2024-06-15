package com.example.mytodolist.MyModel;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonDeserialize
@JsonSerialize
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String email;
    private String username;
    private String password;
    private boolean enable = false;
    private List<ToDoItem> myTask = new ArrayList<>();
    private int currentId = -1;
    private List<Notification> myNotification = new ArrayList<>();
    private List<String> taskCategory = new ArrayList<>();

    public User() {}

    // Manually add Lombok generated methods (getters, setters, toString, etc.)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<ToDoItem> getMyTask() {
        return myTask;
    }

    public void setMyTask(List<ToDoItem> myTask) {
        this.myTask = myTask;
    }

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public List<Notification> getMyNotification() {
        return myNotification;
    }

    public void setMyNotification(List<Notification> myNotification) {
        this.myNotification = myNotification;
    }

    public List<String> getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(List<String> taskCategory) {
        this.taskCategory = taskCategory;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enable=" + enable +
                ", myTask=" + myTask +
                ", currentId=" + currentId +
                ", myNotification=" + myNotification +
                ", taskCategory=" + taskCategory +
                '}';
    }
}
