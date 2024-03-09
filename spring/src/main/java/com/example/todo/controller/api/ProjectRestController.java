package com.example.todo.controller.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todo.dto.request.projects.ProjectCreateRestRequest;
import com.example.todo.entity.Project;
import com.example.todo.service.projects.ProjectCreateService;
import com.example.todo.service.projects.ProjectListService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {
  private final ProjectListService projectListService;
  private final ProjectCreateService projectCreateService;

  public ProjectRestController(ProjectListService projectListService,
      ProjectCreateService projectCreateService) {
    this.projectListService = projectListService;
    this.projectCreateService = projectCreateService;
  }

  @GetMapping
  public ResponseEntity<List<Project>> invoke() {
    List<Project> projects = this.projectListService.invoke();
    return ResponseEntity.ok(projects);
  }

  @PostMapping
  public ResponseEntity<Project> invoke(@Valid @RequestBody ProjectCreateRestRequest request) {
    Project project = this.projectCreateService.invoke(request);
    return ResponseEntity.ok(project);
  }
}
