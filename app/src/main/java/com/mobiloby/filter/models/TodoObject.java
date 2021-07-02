package com.mobiloby.filter.models;

public class TodoObject {
    String username, todoDescription, todoID, time;

    public TodoObject(String todoID, String username, String todoDescription) {
        this.todoID = todoID;
        this.username = username;
        this.todoDescription = todoDescription;
    }

    public String getTodoID() {
        return todoID;
    }

    public void setTodoID(String todoID) {
        this.todoID = todoID;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTodoDescription() {
        return todoDescription;
    }

    public void setTodoDescription(String todoDescription) {
        this.todoDescription = todoDescription;
    }
}
