package com.example.todo_app.controllers;

import com.example.todo_app.models.Task;
import com.example.todo_app.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Restcontroller vs controller?
@RequestMapping("/tasks")
public class TodoController {

    @Autowired
    TaskService taskService;

    public String index() {
        return "index.html";
    }

    // create a book
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping("/create")
    public Task create(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // update a book
    @PutMapping("/update")
    public boolean update(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public boolean deleteTask(Long id) {
        return taskService.deleteTask(id);
    }

    @GetMapping("/getAllTasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{taskID}")
    public Optional<Task> getTasks(
            @PathVariable Long taskID) {
        return taskService.getTask(taskID);
    }

    @GetMapping("/search/{title}")
    public List<Task> searchTitle(
            @PathVariable String title) {
        return taskService.searchTitle(title);
    }
}
