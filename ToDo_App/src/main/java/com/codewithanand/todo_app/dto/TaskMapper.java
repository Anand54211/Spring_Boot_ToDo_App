package com.codewithanand.todo_app.dto;

import com.codewithanand.todo_app.entity.Task;

import java.time.Instant;

public class TaskMapper {
    public static Task toEntity(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTaskTitle(dto.getTaskTitle());
        task.setTaskDescription(dto.getTaskDescription());
        task.setTaskStartDate(dto.getTaskStartDate());
        task.setTaskEndDate(dto.getTaskEndDate());
        task.setTaskStatus(dto.getTaskStatus());
        task.setTaskPriority(dto.getTaskPriority());
        task.setTaskCreated(Instant.now());
        task.setTaskUpdated(Instant.now());
        return task;
    }

    public static TaskResponseDTO toResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getTaskId(),
                task.getTaskTitle(),
                task.getTaskDescription(),
                task.getTaskStartDate(),
                task.getTaskEndDate(),
                task.getTaskStatus(),
                task.getTaskPriority(),
                task.getTaskCreated(),
                task.getTaskUpdated()
        );
    }
}
