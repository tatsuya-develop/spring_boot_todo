package com.example.todo.service.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.example.todo.dto.request.tasks.TaskSearchRestRequest;
import com.example.todo.dto.response.tasks.TaskBaseResponse;
import com.example.todo.entity.Task;
import com.example.todo.repository.TaskRepository;

class TaskSearchServiceTest {

  @Mock
  private TaskRepository taskRepository;

  @InjectMocks
  private TaskSearchService taskSearchService;

  private LocalDateTime now = LocalDateTime.now();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @SuppressWarnings("unchecked")
  @Test
  void projectIdがnullの場合に検索が正しく行われること() {
    // テスト用のタスクデータ
    Task task1 = new Task();
    task1.setId(1);
    task1.setName("Test Task 1");
    task1.setCreatedAt(this.now);
    task1.setUpdatedAt(this.now);

    Task task2 = new Task();
    task2.setId(2);
    task2.setName("Test Task 2");
    task2.setCreatedAt(this.now);
    task2.setUpdatedAt(this.now);

    List<Task> mockTasks = List.of(task1, task2);

    // リクエスト作成
    TaskSearchRestRequest request = new TaskSearchRestRequest(null, null);

    when(this.taskRepository.findAll(any(Specification.class), any(Sort.class)))
        .thenReturn(mockTasks);

    // サービス実行
    List<TaskBaseResponse> result = this.taskSearchService.invoke(request);

    // 結果の検証
    assertEquals(2, result.size());
    assertEquals(1, result.get(0).getId());
    assertEquals("Test Task 1", result.get(0).getName());
    assertEquals(2, result.get(1).getId());
    assertEquals("Test Task 2", result.get(1).getName());

    // Specification の検証
    ArgumentCaptor<Specification<Task>> specCaptor = ArgumentCaptor.forClass(Specification.class);
    // this.taskRepository の findAll に、期待する引数で呼び出されたかを検証
    verify(this.taskRepository).findAll(specCaptor.capture(), any(Sort.class));

    Specification<Task> capturedSpec = specCaptor.getValue();
    assertNotNull(capturedSpec);
  }
}
