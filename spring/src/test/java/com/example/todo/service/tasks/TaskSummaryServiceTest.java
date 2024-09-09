package com.example.todo.service.tasks;

import com.example.todo.dto.request.tasks.TaskSummaryRestRequest;
import com.example.todo.dto.response.tasks.TaskSummaryResponse;
import com.example.todo.entity.Task;
import com.example.todo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TaskSummaryServiceTest {

  @Mock
  private TaskRepository taskRepository;

  @InjectMocks
  private TaskSummaryService taskSummaryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void タスクのサマリーが正しく取得されること() {
    // Arrange: リクエストオブジェクトとモックタスクの設定
    TaskSummaryRestRequest request = new TaskSummaryRestRequest(1);

    Task incompleteTask = new Task();
    incompleteTask.setCompletedAt(null);

    Task completedTask = new Task();
    completedTask.setCompletedAt(java.time.LocalDateTime.now());

    List<Task> tasks = Arrays.asList(incompleteTask, completedTask, completedTask);

    // TaskRepositoryのモックの振る舞いを設定
    when(this.taskRepository.findByProjectId(1)).thenReturn(tasks);

    // Act: サービスのinvokeメソッドを実行
    TaskSummaryResponse response = this.taskSummaryService.invoke(request);

    // Assert: サマリーが正しい値であることを検証
    assertEquals(1, response.getIncompleteCount());
    assertEquals(2, response.getCompleteCount());
  }
}
