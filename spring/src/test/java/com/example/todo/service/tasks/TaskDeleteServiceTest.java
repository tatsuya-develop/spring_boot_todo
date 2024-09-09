package com.example.todo.service.tasks;

import com.example.todo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class TaskDeleteServiceTest {

  @Mock
  private TaskRepository taskRepository;

  @InjectMocks
  private TaskDeleteService taskDeleteService;

  @BeforeEach
  void setUp() {
    // Mockitoのモックを初期化する
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void タスクが削除されること() {
    // Arrange: 削除対象のID
    Integer taskId = 1;

    // Act: サービスのinvokeメソッドを実行
    this.taskDeleteService.invoke(taskId);

    // Assert: リポジトリのdeleteByIdが正しく呼び出されていることを検証
    verify(this.taskRepository, times(1)).deleteById(taskId);
  }
}
