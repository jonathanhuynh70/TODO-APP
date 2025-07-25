package com.example.todo_app.services;

import com.example.todo_app.repositories.TaskRepository;
import com.example.todo_app.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(String title, String description) {
        Task newTask = new Task(title, description);
        return taskRepository.save(newTask);
    }

    public boolean deleteTask(Long id) {
        taskRepository.deleteById(id);
        return true;
    }

    public boolean updateTask(Task task) {
        taskRepository.save(task);
        return true;
    }

    public Optional<Task> getTask(Long id) {
        return taskRepository.findById(id);
    }

    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> searchTitle(String title) {
        return taskRepository.findByTitle(title);
    }
}
