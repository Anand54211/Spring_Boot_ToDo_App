package com.codewithanand.todo_app.service;

import com.codewithanand.todo_app.dto.TaskRequestDTO;
import com.codewithanand.todo_app.dto.TaskResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskResponseDTO createTask(TaskRequestDTO requestDTO);

    TaskResponseDTO getTaskById(UUID id);

    List<TaskResponseDTO> getAllTasks();

    TaskResponseDTO updateTask(UUID id, TaskRequestDTO requestDTO);

    TaskResponseDTO closeTask(UUID id);

    void deleteTask(UUID id);
}
