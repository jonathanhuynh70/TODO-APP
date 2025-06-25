package com.example.todo_app.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="TASKS")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "deadline")
    private Date deadline;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "dateCreated")
    private Date creationDate;

    @Column(name = "dateCompleted")
    private Date completionDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Task(){};

    public Task(String title, String description, String type, Date deadline) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.deadline = deadline;
        this.completed = false;
        this.creationDate = new Date();
        this.completionDate = null;
    }

}
