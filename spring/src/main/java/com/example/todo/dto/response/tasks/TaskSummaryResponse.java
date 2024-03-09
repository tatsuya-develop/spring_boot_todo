package com.example.todo.dto.response.tasks;

import lombok.Data;

@Data
public class TaskSummaryResponse {
  private final long incompleteCount;
  private final long completeCount;

  public TaskSummaryResponse(long incompleteCount, long completeCount) {
    this.incompleteCount = incompleteCount;
    this.completeCount = completeCount;
  }
}
