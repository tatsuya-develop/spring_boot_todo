package com.example.todo.dto.request.tasks;

import lombok.Data;

@Data
public class TaskListRestRequest {
  private final Integer projectId;

  private final Boolean isCompleted;
}
