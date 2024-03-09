package com.example.todo.enums.task;

import lombok.Getter;

@Getter
public enum TaskPriority {
  LOW(0, "低"), MEDIUM(1, "中"), HIGH(2, "高");

  private final int value;
  private final String label;

  TaskPriority(int value, String label) {
    this.value = value;
    this.label = label;
  }
}
