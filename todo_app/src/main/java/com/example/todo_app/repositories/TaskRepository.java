package com.example.todo_app.repositories;

import com.example.todo_app.models.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findByTitle(String title);

}