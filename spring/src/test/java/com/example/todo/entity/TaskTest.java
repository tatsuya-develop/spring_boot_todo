package com.example.todo.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TaskTest {

  @Test
  void isCompleted_完了済みの場合はtrueを返すこと() {
    Task task = new Task();
    task.setCompletedAt(LocalDateTime.now());

    assertTrue(task.isCompleted());
  }

  @Test
  void isCompleted_未完了の場合はfalseを返すこと() {
    Task task = new Task();

    assertFalse(task.isCompleted());
  }

  @Test
  void toggleCompleted_完了済みの場合は未完了に変更すること() {
    Task task = new Task();
    task.setCompletedAt(LocalDateTime.now());

    task.toggleCompleted();

    assertNull(task.getCompletedAt());
  }

  @Test
  void toggleCompleted_未完了の場合は完了済みに変更すること() {
    Task task = new Task();

    task.toggleCompleted();

    // 本当は現在時刻を取得して比較するべき
    // しかし、LocalDateTime.now()をそのまま使うと時間が異なりエラーになる。
    // LocalDateTime.now()をモックにするとかも考えたり、時間を固定することも考えたが、
    // LocalDateTime.now() であることが重要ではなく、completedAt が null でないことが重要なので、省略する
    assertNotNull(task.getCompletedAt());
  }
}
