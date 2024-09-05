package com.example.todo.dto.request.projects;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class ProjectCreateRestRequest {
  public ProjectCreateRestRequest(String name, String summary) {
    this.name = name;
    this.summary = summary;
  }

  @NotEmpty
  private String name;

  private String summary;
}
