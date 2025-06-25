package com.example.todo_app.repositories;

import com.example.todo_app.models.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {

    Optional<Task> findById(Long id);

    List<Task> findAll();

    List<Task> findByTitle(String title);

}