package com.example.todo.dto.request.tags;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class TagCreateRestRequest {
  @NotEmpty
  private String name;

  private String summary;
}
