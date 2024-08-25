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

  // TODO: カスタムバリデーションアノテーションを追加するか...？
  private final Integer priority;

  public TaskPriority getPriority() {
    return TaskPriority.fromValue(this.priority);
  }
}
