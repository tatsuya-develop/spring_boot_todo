package com.example.todo.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import com.example.todo.entity.Task;

public class TaskSpecification {
  public Specification<Task> projectIdIsNull() {
    return (root, query, builder) -> builder.isNull(root.get("project"));
  }

  public Specification<Task> projectIdEqual(Integer projectId) {
    return projectId != null ? (root, query, builder) -> {
      return builder.equal(root.join("project").get("id"), projectId);
    } : null;
  }

  public Specification<Task> isCompleted(Boolean isCompleted) {
    return isCompleted != null ? (root, query, builder) -> {
      return isCompleted ? builder.isNotNull(root.get("completed_at"))
          : builder.isNull(root.get("completed_at"));
    } : null;
  }
}
