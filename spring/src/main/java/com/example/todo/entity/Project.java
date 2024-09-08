package com.example.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "projects")
@EqualsAndHashCode(callSuper = false) // 親クラスの equals, hashCode を使わない（これを設定しないと警告が出る）
public class Project extends BaseEntity {

  public static final Integer DEFDULT_PROJECT_ID = null;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // @ManyToOne
  // @JoinColumn(name = "account_id")
  // private Account account;

  @Column(nullable = false, length = 255)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String summary;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;
}
