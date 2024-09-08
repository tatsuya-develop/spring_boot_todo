package com.example.todo.enums.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class TaskPriorityTest {

  @Test
  void testGetValue() {
    // 各enumのvalueが正しく取得できることを確認
    assertEquals(0, TaskPriority.LOW.getValue());
    assertEquals(1, TaskPriority.MEDIUM.getValue());
    assertEquals(2, TaskPriority.HIGH.getValue());
  }

  @Test
  void testGetLabel() {
    // 各enumのlabelが正しく取得できることを確認
    assertEquals("低", TaskPriority.LOW.getLabel());
    assertEquals("中", TaskPriority.MEDIUM.getLabel());
    assertEquals("高", TaskPriority.HIGH.getLabel());
  }

  @Test
  void testFromValueValid() {
    // fromValueが正しいTaskPriorityを返すことを確認
    assertEquals(TaskPriority.LOW, TaskPriority.fromValue(0));
    assertEquals(TaskPriority.MEDIUM, TaskPriority.fromValue(1));
    assertEquals(TaskPriority.HIGH, TaskPriority.fromValue(2));
  }

  @Test
  void testFromValueInvalid() {
    // fromValueが無効な値の場合、例外を投げることを確認
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      TaskPriority.fromValue(3);
    });
    assertEquals("Unexpected value: 3", exception.getMessage());
  }
}
