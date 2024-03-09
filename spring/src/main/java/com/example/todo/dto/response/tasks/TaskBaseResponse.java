package com.example.todo.dto.response.tasks;

import java.time.format.DateTimeFormatter;
import com.example.todo.entity.Task;
import com.example.todo.enums.task.TaskPriority;
import lombok.Data;

@Data
public class TaskBaseResponse {
  private Integer id;

  private Integer projectId;

  private Integer parentId;

  private String name;

  private TaskPriority priority;

  private String memo;

  private String deadlineAt;

  private String completedAt;

  public TaskBaseResponse(Task task) {
    this.id = task.getId();
    this.projectId = task.getProject() != null ? task.getProject().getId() : null;
    this.parentId = task.getParent() != null ? task.getParent().getId() : null;
    this.name = task.getName();
    this.priority = task.getPriority();
    this.memo = task.getMemo();
    this.deadlineAt = task.getDeadlineAt() != null
        ? task.getDeadlineAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        : null;
    this.completedAt = task.getCompletedAt() != null
        ? task.getCompletedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        : null;
  }
}
