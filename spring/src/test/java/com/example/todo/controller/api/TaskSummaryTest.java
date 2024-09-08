package com.example.todo.controller.api;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.todo.dto.request.tasks.TaskSummaryRestRequest;
import com.example.todo.dto.response.tasks.TaskSummaryResponse;
import com.example.todo.service.tasks.TaskCreateService;
import com.example.todo.service.tasks.TaskDeleteService;
import com.example.todo.service.tasks.TaskSearchService;
import com.example.todo.service.tasks.TaskSummaryService;
import com.example.todo.service.tasks.TaskToggleService;
import com.example.todo.service.tasks.TaskUpdateService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TaskRestController.class)
class TaskSummaryTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TaskSearchService taskSearchService;

  @MockBean
  private TaskSummaryService taskSummaryService;

  @MockBean
  private TaskCreateService taskCreateService;

  @MockBean
  private TaskUpdateService taskUpdateService;

  @MockBean
  private TaskDeleteService taskDeleteService;

  @MockBean
  private TaskToggleService taskToggleService;

  @Test
  void プロジェクトごとのタスクの完了_未完了数が取得できること() throws Exception {
    // モックレスポンスの設定
    TaskSummaryResponse mockSummary = new TaskSummaryResponse(5, 3);
    Mockito.when(this.taskSummaryService.invoke(Mockito.any(TaskSummaryRestRequest.class)))
        .thenReturn(mockSummary);

    this.mockMvc.perform(get("/api/tasks/summary?projectId=1")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.incompleteCount").value(5))
        .andExpect(jsonPath("$.completeCount").value(3));

    // TaskSummaryRestRequest 型の引数をキャプチャするための ArgumentCaptor を生成
    // キャプチャとは、"捕まえる" という意味がり、ここで言うと「メソッドの引数に渡された値を取得すること」を指す
    ArgumentCaptor<TaskSummaryRestRequest> captor =
        ArgumentCaptor.forClass(TaskSummaryRestRequest.class);
    // this.taskSummaryService の invoke メソッドが呼び出された際に、実際に渡された引数をキャプチャする
    verify(this.taskSummaryService).invoke(captor.capture());

    // captorでキャプチャした引数を取得し、実際のリクエストを取得
    TaskSummaryRestRequest capturedRequest = captor.getValue();
    // 取得したリクエストのprojectIdが 1 であることを検証
    assertEquals(1, capturedRequest.getProjectId());
  }
}
