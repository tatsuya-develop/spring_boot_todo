package com.example.todo.dto.request.tasks;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;
import com.example.todo.enums.task.TaskPriority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TaskUpdateRestRequest {
  @NotNull
  @Positive
  private final Integer id;

  @NotEmpty
  private final String name;

  private final Integer projectId;

  private final Set<Integer> tagIds;

  @NotNull
  private final TaskPriority priority;

  private final String memo;

  private final ZonedDateTime deadlineAt;

  private final ZonedDateTime completedAt;

  public LocalDateTime getDeadlineAt() {
    return deadlineAt != null ? deadlineAt.toLocalDateTime() : null;
  }

  public LocalDateTime getCompletedAt() {
    return completedAt != null ? completedAt.toLocalDateTime() : null;
  }
}
