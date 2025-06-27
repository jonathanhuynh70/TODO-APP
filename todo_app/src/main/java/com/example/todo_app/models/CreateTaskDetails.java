package com.example.todo_app.models;

public class CreateTaskDetails {
    private String title;
    private String description;

    public CreateTaskDetails(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

}
