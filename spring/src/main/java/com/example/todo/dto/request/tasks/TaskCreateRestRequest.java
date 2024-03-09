package com.example.todo.dto.request.tasks;

import com.example.todo.enums.task.TaskPriority;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TaskCreateRestRequest {
  @NotEmpty
  private final String name;

  private final Integer projectId;

  @NotEmpty
  private final String priority;

  public TaskPriority getPriority() {
    return TaskPriority.valueOf(this.priority);
  }
}
