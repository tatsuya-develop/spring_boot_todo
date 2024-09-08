package com.example.todo.dto.response.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.todo.entity.Project;
import com.example.todo.entity.Tag;
import com.example.todo.entity.Task;
import com.example.todo.enums.task.TaskPriority;

class TaskBaseResponseTest {

  private Task mockTask;
  private Project mockProject;
  private Set<Tag> mockTags;

  // Mock の Set.class を Set<Tag> に強制キャストしていることに対して警告が出ているが、検証用のMockで意図的に作っているものなので、警告は無視する
  @SuppressWarnings("unchecked")
  @BeforeEach
  void setUp() {
    this.mockTask = mock(Task.class);
    this.mockProject = mock(Project.class);
    this.mockTags = (Set<Tag>) mock(Set.class);

    LocalDateTime time = LocalDateTime.of(2024, 9, 1, 10, 0, 0);

    when(mockTask.getId()).thenReturn(1);
    when(mockTask.getProject()).thenReturn(mockProject);
    when(mockTask.getTags()).thenReturn(mockTags);
    when(mockTask.getParent()).thenReturn(null);
    when(mockTask.getName()).thenReturn("Sample task");
    when(mockTask.getPriority()).thenReturn(TaskPriority.LOW);
    when(mockTask.getMemo()).thenReturn("Sample memo");
    when(mockTask.getDeadlineAt()).thenReturn(time);
    when(mockTask.getCompletedAt()).thenReturn(time);
    when(mockTask.getCreatedAt()).thenReturn(time);
    when(mockTask.getUpdatedAt()).thenReturn(time);
  }

  @Test
  void 全てのフィールドが正しく設定されていること() {
    TaskBaseResponse response = new TaskBaseResponse(mockTask);

    // 各フィールドが正しく設定されていることを検証
    assertEquals(1, response.getId());
    assertEquals(mockProject, response.getProject());
    assertEquals(mockTags, response.getTags());
    assertNull(response.getParentId());
    assertEquals("Sample task", response.getName());
    assertEquals(TaskPriorityResponse.class, response.getPriority().getClass());
    assertEquals(new TaskPriorityResponse(TaskPriority.LOW).getValue(),
        response.getPriority().getValue());
    assertEquals("Sample memo", response.getMemo());
    assertEquals("2024-09-01 10:00", response.getDeadlineAt());
    assertEquals("2024-09-01 10:00", response.getCompletedAt());
    assertEquals("2024-09-01 10:00", response.getCreatedAt());
    assertEquals("2024-09-01 10:00", response.getUpdatedAt());
  }

  @Test
  void deadlineAtやcompletedAtがnullの場合にnullが設定されていること() {
    when(mockTask.getDeadlineAt()).thenReturn(null);
    when(mockTask.getCompletedAt()).thenReturn(null);

    TaskBaseResponse response = new TaskBaseResponse(mockTask);

    // deadlineAtとcompletedAtがnullであることを検証
    assertNull(response.getDeadlineAt());
    assertNull(response.getCompletedAt());
  }
}
