package com.example.todo_app;

import com.example.todo_app.models.Task;
import com.example.todo_app.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTests {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String BASEURI;

    @Autowired
    TaskRepository taskRepository;

    @BeforeEach
    void testSetUp() {

        BASEURI = "http://localhost:" + port + "/tasksApi";

        taskRepository.deleteAll();

        Task b1 = new Task("test", "Test description");
        Task b2 = new Task("test2", "Test description2");
        Task b3 = new Task("test3", "Test description3");
        Task b4 = new Task("test4", "Test description4");

        taskRepository.saveAll(List.of(b1, b2, b3, b4));
    }

    @Test
    void testFindAll() {

        // find all Tasks and return List<Task>
        ParameterizedTypeReference<List<Task>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<Task>> response = restTemplate.exchange(
                BASEURI + "/getAllTasks",
                HttpMethod.GET,
                null,
                typeRef);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(4, response.getBody().size());

    }

    @Test
    void testFindByTitle() {
        String title = "test2";
        ParameterizedTypeReference<List<Task>> typeRef = new ParameterizedTypeReference<>() {
        };

        // find Task C
        ResponseEntity<List<Task>> response = restTemplate.exchange(
                BASEURI + "/search/" + title,
                HttpMethod.GET,
                null,
                typeRef);

        // test response code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Task> list = response.getBody();
        assert list != null;

        assertEquals(1, list.size());

        // Test test2 title
        Task task = list.get(0);
        assertEquals("test2", task.getTitle());

    }

    @Test
    public void testDeleteById() {

        Task TaskD = taskRepository.findAll().iterator().next();
        Long id = TaskD.getId();

        // delete by id
        ResponseEntity<Void> response = restTemplate.exchange(
                BASEURI + "/" + id,
                HttpMethod.DELETE,
                null,
                Void.class);

        // test 204
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // find test again, ensure no result
        List<Task> listAgain = taskRepository.findByTitle("test");
        assertEquals(0, listAgain.size());
    }

    @Test
    public void testCreate() {

        // Create a new Task
        Task newTask = new Task("Test Task 5", "Test description");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<Task> request = new HttpEntity<>(newTask, headers);

        // test POST save
        ResponseEntity<Task> responseEntity = restTemplate.postForEntity(BASEURI + "/create", request,
                Task.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // find Test Task 5
        List<Task> list = taskRepository.findByTitle("Test Task 5");

        // Test Task 5 details
        Task task = list.get(0);
        assertEquals("Test Task 5", task.getTitle());
    }

    @Test
    public void testUpdate() {
        // Find Task D
        Task TaskD = taskRepository.findAll().iterator().next();
        Long id = TaskD.getId();

        // Update the Task details
        TaskD.setTitle("Updated Task Name");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // put the updated Task in HttpEntity
        HttpEntity<Task> request = new HttpEntity<>(TaskD, headers);

        // Perform the PUT request to update the Task
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                BASEURI + "/update",
                HttpMethod.PUT,
                request,
                Boolean.class);

        // ensure OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // verify the updated Task
        Task updatedTask = taskRepository.findById(id).orElseThrow();

        assertEquals(id, updatedTask.getId());
        assertEquals("Updated Task Name", updatedTask.getTitle());
    }

    @Test
    public void testComplete() {
        // Find Task D
        Task TaskD = taskRepository.findAll().iterator().next();
        Long id = TaskD.getId();

        // Update the Task details
        TaskD.setCompleted(true);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // put the updated Task in HttpEntity
        HttpEntity<Task> request = new HttpEntity<>(TaskD, headers);

        // Perform the PUT request to update the Task
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                BASEURI + "/update",
                HttpMethod.PUT,
                request,
                Boolean.class);

        // ensure OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // verify the updated Task
        Task updatedTask = taskRepository.findById(id).orElseThrow();

        assertEquals(id, updatedTask.getId());
        assertEquals(true, updatedTask.getCompleted());
    }

}