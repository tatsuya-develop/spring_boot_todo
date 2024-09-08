package com.example.todo.dto.request.tasks;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TaskSearchRestRequest {

  private final Integer projectId;

  private final Boolean isCompleted;
}
