package com.example.todo.controller.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.todo.dto.request.tasks.TaskCreateRestRequest;
import com.example.todo.dto.response.tasks.TaskBaseResponse;
import com.example.todo.entity.Task;
import com.example.todo.enums.task.TaskPriority;
import com.example.todo.service.tasks.TaskCreateService;
import com.example.todo.service.tasks.TaskDeleteService;
import com.example.todo.service.tasks.TaskSearchService;
import com.example.todo.service.tasks.TaskSummaryService;
import com.example.todo.service.tasks.TaskToggleService;
import com.example.todo.service.tasks.TaskUpdateService;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TaskRestController.class)
class TaskCreateTest {

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

  private TaskBaseResponse taskBaseResponse;

  @Test
  void 作成されたTaskが返されること() throws Exception {
    // モックレスポンスの設定
    Task task = new Task();
    task.setId(1);
    task.setName("New Task");
    task.setPriority(TaskPriority.MEDIUM);
    LocalDateTime now = LocalDateTime.now();
    task.setCreatedAt(now);
    task.setUpdatedAt(now);

    this.taskBaseResponse = new TaskBaseResponse(task);

    // モックサービスの振る舞いを定義
    Mockito.when(this.taskCreateService.invoke(Mockito.any(TaskCreateRestRequest.class)))
        .thenReturn(this.taskBaseResponse);

    String requestBody = """
        {
          "name": "New Task",
          "projectId": null,
          "priority": 1
        }
        """;

    this.mockMvc
        .perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("New Task"))
        .andExpect(jsonPath("$.project").isEmpty())
        .andExpect(jsonPath("$.priority.value").value(TaskPriority.MEDIUM.getValue()));
  }
}
