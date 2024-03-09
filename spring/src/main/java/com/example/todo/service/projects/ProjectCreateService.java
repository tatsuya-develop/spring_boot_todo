package com.example.todo.service.projects;

import org.springframework.stereotype.Service;
import com.example.todo.dto.request.projects.ProjectCreateRestRequest;
import com.example.todo.entity.Project;
import com.example.todo.repository.ProjectRepository;

@Service
public class ProjectCreateService {
  private final ProjectRepository projectRepository;

  public ProjectCreateService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  public Project invoke(ProjectCreateRestRequest request) {
    Project project = new Project();

    project.setName(request.getName());
    project.setSummary(request.getSummary());

    return this.projectRepository.save(project);
  }
}
