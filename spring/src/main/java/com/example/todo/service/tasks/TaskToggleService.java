package com.example.todo.service.tasks;

import org.springframework.stereotype.Service;
import com.example.todo.dto.response.tasks.TaskBaseResponse;
import com.example.todo.entity.Task;
import com.example.todo.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskToggleService {
  private final TaskRepository taskRepository;

  public TaskToggleService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public TaskBaseResponse invoke(Integer id) {
    Task task = this.taskRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));

    task.toggleCompleted();

    return new TaskBaseResponse(this.taskRepository.save(task));
  }
}
