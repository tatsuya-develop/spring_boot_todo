package com.example.todo.service.tasks;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.example.todo.dto.request.tasks.TaskCompleteRestRequest;
import com.example.todo.dto.response.tasks.TaskBaseResponse;
import com.example.todo.entity.Task;
import com.example.todo.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskCompleteService {
  private final TaskRepository taskRepository;

  public TaskCompleteService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public TaskBaseResponse invoke(TaskCompleteRestRequest request) {
    Task task = this.taskRepository.findById(request.getId().intValue()).orElseThrow(
        () -> new EntityNotFoundException("Task not found with ID: " + request.getId()));

    task.setCompletedAt(LocalDateTime.now());

    return new TaskBaseResponse(this.taskRepository.save(task));
  }
}
