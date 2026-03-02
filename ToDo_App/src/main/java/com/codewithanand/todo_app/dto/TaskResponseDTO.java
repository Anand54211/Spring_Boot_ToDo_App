package com.codewithanand.todo_app.dto;

import com.codewithanand.todo_app.entity.TaskPriority;
import com.codewithanand.todo_app.entity.TaskStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class TaskResponseDTO {
    private UUID taskId;
    private String taskTitle;
    private String taskDescription;
    private LocalDate taskStartDate;
    private LocalDate taskEndDate;
    private TaskStatus taskStatus;
    private TaskPriority taskPriority;
    private Instant taskCreated;
    private Instant taskUpdated;

    public TaskResponseDTO() {}

    public TaskResponseDTO(UUID taskId, String taskTitle, String taskDescription,
                           LocalDate taskStartDate, LocalDate taskEndDate,
                           TaskStatus taskStatus, TaskPriority taskPriority,
                           Instant taskCreated, Instant taskUpdated) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.taskStatus = taskStatus;
        this.taskPriority = taskPriority;
        this.taskCreated = taskCreated;
        this.taskUpdated = taskUpdated;
    }

    public UUID getTaskId() { return taskId; }
    public void setTaskId(UUID taskId) { this.taskId = taskId; }

    public String getTaskTitle() { return taskTitle; }
    public void setTaskTitle(String taskTitle) { this.taskTitle = taskTitle; }

    public String getTaskDescription() { return taskDescription; }
    public void setTaskDescription(String taskDescription) { this.taskDescription = taskDescription; }

    public LocalDate getTaskStartDate() { return taskStartDate; }
    public void setTaskStartDate(LocalDate taskStartDate) { this.taskStartDate = taskStartDate; }

    public LocalDate getTaskEndDate() { return taskEndDate; }
    public void setTaskEndDate(LocalDate taskEndDate) { this.taskEndDate = taskEndDate; }

    public TaskStatus getTaskStatus() { return taskStatus; }
    public void setTaskStatus(TaskStatus taskStatus) { this.taskStatus = taskStatus; }

    public TaskPriority getTaskPriority() { return taskPriority; }
    public void setTaskPriority(TaskPriority taskPriority) { this.taskPriority = taskPriority; }

    public Instant getTaskCreated() { return taskCreated; }
    public void setTaskCreated(Instant taskCreated) { this.taskCreated = taskCreated; }

    public Instant getTaskUpdated() { return taskUpdated; }
    public void setTaskUpdated(Instant taskUpdated) { this.taskUpdated = taskUpdated; }
}
