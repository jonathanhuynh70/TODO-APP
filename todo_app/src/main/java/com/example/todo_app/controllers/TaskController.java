package com.example.todo_app.controllers;

import com.example.todo_app.models.CreateTaskDetails;
import com.example.todo_app.models.Task;
import com.example.todo_app.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/tasksApi")
public class TaskController {

    @Autowired
    TaskService taskService;

    // create a Task
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping("/create")
    public Task create(@RequestBody CreateTaskDetails taskDetails) {
        return taskService.createTask(taskDetails.getTitle(), taskDetails.getDescription());
    }

    @PutMapping("/update")
    public boolean update(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public boolean deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

    @GetMapping("/getAllTasks")
    public Iterable<Task> getAllTasks() {
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
