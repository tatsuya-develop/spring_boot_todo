package com.example.todo.dto.request.tasks;

import com.example.todo.enums.task.TaskPriority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TaskCreateRestRequest {

  @NotEmpty
  private final String name;

  private final Integer projectId;

  @NotNull
  private final TaskPriority priority;
}
