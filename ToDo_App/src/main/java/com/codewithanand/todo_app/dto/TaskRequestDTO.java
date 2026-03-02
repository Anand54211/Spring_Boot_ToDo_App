package com.codewithanand.todo_app.dto;

import com.codewithanand.todo_app.entity.TaskPriority;
import com.codewithanand.todo_app.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TaskRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String taskTitle;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String taskDescription;

    private LocalDate taskStartDate;

    private LocalDate taskEndDate;

    @NotNull(message = "Status is required")
    private TaskStatus taskStatus;

    @NotNull(message = "Priority is required")
    private TaskPriority taskPriority;

    public TaskRequestDTO() {}

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
}
