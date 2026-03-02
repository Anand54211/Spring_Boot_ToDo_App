package com.codewithanand.todo_app.service;

import com.codewithanand.todo_app.dto.TaskMapper;
import com.codewithanand.todo_app.dto.TaskRequestDTO;
import com.codewithanand.todo_app.dto.TaskResponseDTO;
import com.codewithanand.todo_app.entity.Task;
import com.codewithanand.todo_app.entity.TaskStatus;
import com.codewithanand.todo_app.exception.TaskNotFoundException;
import com.codewithanand.todo_app.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        Task task = TaskMapper.toEntity(requestDTO);
        Task saved = taskRepository.save(task);
        return TaskMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(UUID id) {
        Task task = findTaskOrThrow(id);
        return TaskMapper.toResponseDTO(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDTO updateTask(UUID id, TaskRequestDTO requestDTO) {
        Task task = findTaskOrThrow(id);

        task.setTaskTitle(requestDTO.getTaskTitle());
        task.setTaskDescription(requestDTO.getTaskDescription());
        task.setTaskStartDate(requestDTO.getTaskStartDate());
        task.setTaskEndDate(requestDTO.getTaskEndDate());
        task.setTaskStatus(requestDTO.getTaskStatus());
        task.setTaskPriority(requestDTO.getTaskPriority());
        task.setTaskUpdated(Instant.now());

        Task updated = taskRepository.save(task);
        return TaskMapper.toResponseDTO(updated);
    }

    @Override
    public TaskResponseDTO closeTask(UUID id) {
        Task task = findTaskOrThrow(id);
        task.setTaskStatus(TaskStatus.CLOSED);
        task.setTaskUpdated(Instant.now());
        Task closed = taskRepository.save(task);
        return TaskMapper.toResponseDTO(closed);
    }

    @Override
    public void deleteTask(UUID id) {
        Task task = findTaskOrThrow(id);
        taskRepository.delete(task);
    }

    private Task findTaskOrThrow(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }
}
