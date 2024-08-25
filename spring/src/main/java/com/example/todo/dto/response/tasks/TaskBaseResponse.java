package com.example.todo.dto.response.tasks;

import java.util.Set;
import com.example.todo.entity.Project;
import com.example.todo.entity.Tag;
import com.example.todo.entity.Task;
import com.example.todo.util.TimeUtil;
import lombok.Data;

@Data
public class TaskBaseResponse {
  private Integer id;

  private Project project;

  private Set<Tag> tags;

  private Integer parentId;

  private String name;

  private TaskPriorityResponse priority;

  private String memo;

  private String deadlineAt;

  private String completedAt;

  private String createdAt;

  private String updatedAt;

  public TaskBaseResponse(Task task) {
    this.id = task.getId();
    this.project = task.getProject();
    this.tags = task.getTags();
    this.parentId = task.getParent() != null ? task.getParent().getId() : null;
    this.name = task.getName();
    this.priority = new TaskPriorityResponse(task.getPriority());
    this.memo = task.getMemo();
    this.deadlineAt =
        task.getDeadlineAt() != null ? TimeUtil.Format.toYmdhm(task.getDeadlineAt()) : null;
    this.completedAt =
        task.getCompletedAt() != null ? TimeUtil.Format.toYmdhm(task.getCompletedAt()) : null;
    this.createdAt = TimeUtil.Format.toYmdhm(task.getCreatedAt());
    this.updatedAt = TimeUtil.Format.toYmdhm(task.getUpdatedAt());
  }
}
