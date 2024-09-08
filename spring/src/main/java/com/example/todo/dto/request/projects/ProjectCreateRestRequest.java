package com.example.todo.dto.request.projects;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProjectCreateRestRequest {

  @NotEmpty
  private final String name;

  private final String summary;
}
