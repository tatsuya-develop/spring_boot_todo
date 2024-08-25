package com.example.todo.dto.response.tasks;

import com.example.todo.enums.task.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskPriorityResponse {
  private final String name;
  private int value;
  private String label;

  public TaskPriorityResponse(TaskPriority taskPriority) {
    this.name = taskPriority.name();
    this.value = taskPriority.ordinal();
    this.label = taskPriority.getLabel();
  }
}
