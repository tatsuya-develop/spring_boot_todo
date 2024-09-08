package com.example.todo.dto.response.tasks;

import com.example.todo.enums.task.TaskPriority;
import lombok.Getter;

@Getter
public class TaskPriorityResponse {
  private final String name;
  private final int value;
  private final String label;

  public TaskPriorityResponse(TaskPriority taskPriority) {
    this.name = taskPriority.name();
    this.value = taskPriority.ordinal();
    this.label = taskPriority.getLabel();
  }
}
