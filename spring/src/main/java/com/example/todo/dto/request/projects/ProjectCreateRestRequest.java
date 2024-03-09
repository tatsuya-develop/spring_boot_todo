package com.example.todo.dto.request.projects;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class ProjectCreateRestRequest {
  @NotEmpty
  private String name;

  private String summary;
}
