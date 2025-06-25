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

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing with TestRestTemplate and @Testcontainers (image mysql:8.0-debian)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// activate automatic startup and stop of containers
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

        BASEURI = "http://localhost:" + port;

        taskRepository.deleteAll();

        Task b1 = new Task("test", "Test description", "productivity", new Date());
        Task b2 = new Task("test2", "Test description2", "productivity", new Date());
        Task b3 = new Task("test3", "Test description3", "productivity", new Date());
        Task b4 = new Task("test4", "Test description4", "productivity", new Date());

        taskRepository.saveAll(List.of(b1, b2, b3, b4));
    }

    @Test
    void testFindAll() {

        // ResponseEntity<List> response = restTemplate.getForEntity(BASEURI + "/tasks",
        // List.class);

        // find all Tasks and return List<Task>
        ParameterizedTypeReference<List<Task>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<Task>> response = restTemplate.exchange(
                BASEURI + "/tasks/getAllTasks",
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
                BASEURI + "/tasks/search/" + title,
                HttpMethod.GET,
                null,
                typeRef);

        // test response code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Task> list = response.getBody();
        assert list != null;

        assertEquals(1, list.size());

        // Test Task C details
        Task task = list.get(0);
        assertEquals("test2", task.getTitle());

    }

    @Test
    public void testDeleteById() {

        List<Task> list = taskRepository.findByTitle("test");
        Task test = list.get(0);

        // get Task A id
        Long id = test.getId();

        // delete by id
        ResponseEntity<Void> response = restTemplate.exchange(
                BASEURI + "/tasks/deleteTask/" + id,
                HttpMethod.DELETE,
                null,
                Void.class);

        // test 204
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // find Task A again, ensure no result
        List<Task> listAgain = taskRepository.findByTitle("test");
        assertEquals(0, listAgain.size());

    }

    @Test
    public void testCreate() {

        // Create a new Task E
        Task newTask = new Task("Test Task 5", "Test description", "test", new Date());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<Task> request = new HttpEntity<>(newTask, headers);

        // test POST save
        ResponseEntity<Task> responseEntity = restTemplate.postForEntity(BASEURI + "/tasks/create", request,
                Task.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // find Task E
        List<Task> list = taskRepository.findByTitle("Test Task 5");

        // Test Task E details
        Task task = list.get(0);
        assertEquals("Test Task 5", task.getTitle());
    }

    /**
     * Task b4 = new Task("Task D",
     * BigDecimal.valueOf(39.99),
     * LocalDate.of(2023, 5, 5));
     */
    @Test
    public void testUpdate() {
        // Find Task D
        Task TaskD = taskRepository.findAll().get(0);
        Long id = TaskD.getId();

        // Update the Task details
        TaskD.setTitle("Updated Task Name");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // put the updated Task in HttpEntity
        HttpEntity<Task> request = new HttpEntity<>(TaskD, headers);

        // Perform the PUT request to update the Task
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/tasks/update",
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

}