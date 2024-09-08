package com.example.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import com.example.todo.enums.task.TaskPriority;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tasks")
@EqualsAndHashCode(callSuper = false) // 親クラスの equals, hashCode を使わない（これを設定しないと警告が出る）
public class Task extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // @ManyToOne
  // @JoinColumn(name = "account_id")
  // private Account account;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Task parent;

  @ManyToMany
  @JoinTable(name = "task_tags", joinColumns = @JoinColumn(name = "task_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags = new HashSet<>();

  @NotNull
  @Column(length = 255)
  private String name;

  @NotNull
  @Column(name = "priority")
  @Enumerated(EnumType.ORDINAL)
  private TaskPriority priority = TaskPriority.MEDIUM;

  @NotNull
  @Column(name = "memo", columnDefinition = "TEXT")
  private String memo = "";

  @Column(name = "deadline_at")
  private LocalDateTime deadlineAt;

  @Column(name = "completed_at")
  private LocalDateTime completedAt;

  public boolean isCompleted() {
    return this.getCompletedAt() != null;
  }

  public void toggleCompleted() {
    this.setCompletedAt(this.isCompleted() ? null : LocalDateTime.now());
  }
}

