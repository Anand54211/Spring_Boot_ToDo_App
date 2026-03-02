package com.codewithanand.todo_app.repository;

import com.codewithanand.todo_app.entity.Task;
import com.codewithanand.todo_app.entity.TaskPriority;
import com.codewithanand.todo_app.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByTaskStatus(TaskStatus status);

    List<Task> findByTaskPriority(TaskPriority priority);

    List<Task> findByTaskStatusAndTaskPriority(TaskStatus status, TaskPriority priority);

}
