package com.example.todo.service.tasks;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.todo.dto.request.tasks.TaskSummaryRestRequest;
import com.example.todo.dto.response.tasks.TaskSummaryResponse;
import com.example.todo.entity.Task;
import com.example.todo.repository.TaskRepository;

@Service
public class TaskSummaryService {
  private final TaskRepository taskRepository;

  public TaskSummaryService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public TaskSummaryResponse invoke(TaskSummaryRestRequest request) {
    List<Task> tasks = this.taskRepository.findByProjectId(request.getProjectId());

    final long incompleteCount = tasks.stream().filter(task -> !task.isCompleted()).count();
    final long completeCount = tasks.stream().filter(task -> task.isCompleted()).count();

    return new TaskSummaryResponse(incompleteCount, completeCount);
  }
}
