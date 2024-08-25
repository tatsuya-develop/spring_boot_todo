package com.example.todo.service.tasks;

import org.springframework.stereotype.Service;
import com.example.todo.repository.TaskRepository;

@Service
public class TaskDeleteService {
  private final TaskRepository taskRepository;

  public TaskDeleteService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public void invoke(Integer id) {
    this.taskRepository.deleteById(id);
  }
}
