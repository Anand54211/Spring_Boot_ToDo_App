package com.codewithanand.todo_app.controller;

import com.codewithanand.todo_app.dto.TaskRequestDTO;
import com.codewithanand.todo_app.dto.TaskResponseDTO;
import com.codewithanand.todo_app.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // POST /api/v1/tasks
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO requestDTO) {
        TaskResponseDTO response = taskService.createTask(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/v1/tasks
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // GET /api/v1/tasks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // PUT /api/v1/tasks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable UUID id,
            @Valid @RequestBody TaskRequestDTO requestDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, requestDTO));
    }

    // PATCH /api/v1/tasks/{id}/close
    @PatchMapping("/{id}/close")
    public ResponseEntity<TaskResponseDTO> closeTask(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.closeTask(id));
    }

    // DELETE /api/v1/tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
