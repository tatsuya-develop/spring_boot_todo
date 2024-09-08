package com.example.todo.dto.response.tasks;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TaskSummaryResponse {
  private final long incompleteCount;
  private final long completeCount;
}
