package com.codewithanand.todo_app.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",updatable = false,nullable = false)
    private UUID taskId;

    @Column(name = "title",nullable = false)
    private String taskTitle;

    @Column(name="description",length = 500)
    private String taskDescription;


    @Column(name="start_date")
    private LocalDate taskStartDate;

    @Column(name="end_date")
    private LocalDate taskEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false)
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    @Column(name="priority",nullable = false)
    private TaskPriority taskPriority;

    @Column(name="created",updatable = false,nullable = false)
    private Instant taskCreated;

    @Column(name="updated",updatable = false,nullable = false)
    private Instant taskUpdated;


    public Task() {
    }

    public Task(UUID taskId, String taskTitle, String taskDescription, LocalDate taskStartDate, LocalDate taskEndDate, TaskStatus taskStatus, TaskPriority taskPriority, Instant taskCreated, Instant taskUpdated) {
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


    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDate getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(LocalDate taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public LocalDate getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(LocalDate taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Instant getTaskCreated() {
        return taskCreated;
    }

    public void setTaskCreated(Instant taskCreated) {
        this.taskCreated = taskCreated;
    }

    public Instant getTaskUpdated() {
        return taskUpdated;
    }

    public void setTaskUpdated(Instant taskUpdated) {
        this.taskUpdated = taskUpdated;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskId, task.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(taskId);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStartDate=" + taskStartDate +
                ", taskEndDate=" + taskEndDate +
                ", taskStatus=" + taskStatus +
                ", taskPriority=" + taskPriority +
                ", taskCreated=" + taskCreated +
                ", taskUpdated=" + taskUpdated +
                '}';
    }
}
