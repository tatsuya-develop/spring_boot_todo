package com.example.todo.service.projects;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.todo.entity.Project;
import com.example.todo.repository.ProjectRepository;

@Service
public class ProjectListService {
  private final ProjectRepository projectRepository;

  public ProjectListService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  public List<Project> invoke() {
    return this.projectRepository.findAll();
  }
}
