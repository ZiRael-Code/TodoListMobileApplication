package com.example.mytodolist.MyModel;


import android.os.Build;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ToDoItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private int todoItemId;
    private String title;
    private String description;

    @JsonIgnore
    @JsonProperty("dueDate")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDate dueDate;

    @JsonIgnore
    @JsonProperty("dueDate")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDate startDate;

    @JsonIgnore
    @JsonProperty("createdDate")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;

    private Priority priority;
    private boolean completed = false;
    private String taskName;
    private String taskType;

    private int userId;

    private String startTimer;
    private String endTimer;

    public ToDoItem() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.createdDate = LocalDateTime.now();
        }
    }

    public String getEndTimer() {
        return endTimer;
    }

    public void setEndTimer(String endTimer) {
        this.endTimer = endTimer;
    }

    public int getTodoItemId() {
        return todoItemId;
    }

    public void setTodoItemId(int todoItemId) {
        this.todoItemId = todoItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStartTimer() {
        return startTimer;
    }

    public void setStartTimer(String startTimer) {
        this.startTimer = startTimer;
    }


}