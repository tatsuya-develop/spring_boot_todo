package com.example.todo.dto.request.tags;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TagCreateRestRequest {

  @NotEmpty
  private String name;

  private String summary;
}
