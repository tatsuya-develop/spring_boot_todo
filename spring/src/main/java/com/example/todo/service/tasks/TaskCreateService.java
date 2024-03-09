package com.example.todo.service.tasks;

import org.springframework.stereotype.Service;
import com.example.todo.dto.request.tasks.TaskCreateRestRequest;
import com.example.todo.dto.response.tasks.TaskBaseResponse;
import com.example.todo.entity.Project;
import com.example.todo.entity.Task;
import com.example.todo.repository.ProjectRepository;
import com.example.todo.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskCreateService {
  private final TaskRepository taskRepository;
  private final ProjectRepository projectRepository;

  public TaskCreateService(TaskRepository taskRepository, ProjectRepository projectRepository) {
    this.taskRepository = taskRepository;
    this.projectRepository = projectRepository;
  }

  public TaskBaseResponse invoke(TaskCreateRestRequest request) {
    Task task = new Task();

    task.setName(request.getName());
    task.setPriority(request.getPriority());

    if (request.getProjectId() != null) {
      Project project = this.projectRepository.findById(request.getProjectId().intValue())
          .orElseThrow(() -> new EntityNotFoundException(
              "Project not found with ID: " + request.getProjectId()));

      task.setProject(project);
    }

    return new TaskBaseResponse(this.taskRepository.save(task));
  }
}
