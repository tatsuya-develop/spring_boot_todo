package com.example.todo.controller.api;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.todo.dto.request.tasks.TaskSearchRestRequest;
import com.example.todo.dto.response.tasks.TaskBaseResponse;
import com.example.todo.entity.Task;
import com.example.todo.enums.task.TaskPriority;
import com.example.todo.service.tasks.TaskCreateService;
import com.example.todo.service.tasks.TaskDeleteService;
import com.example.todo.service.tasks.TaskSearchService;
import com.example.todo.service.tasks.TaskSummaryService;
import com.example.todo.service.tasks.TaskToggleService;
import com.example.todo.service.tasks.TaskUpdateService;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TaskRestController.class)
class TaskSearchTest {

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

  @BeforeEach
  void setUp() {
    LocalDateTime now = LocalDateTime.now();
    // モックレスポンスの設定
    Task task1 = new Task();
    task1.setId(1);
    task1.setName("Task 1");
    task1.setPriority(TaskPriority.MEDIUM);
    task1.setCreatedAt(now);
    task1.setUpdatedAt(now);

    Task task2 = new Task();
    task2.setId(2);
    task2.setName("Task 2");
    task2.setPriority(TaskPriority.LOW);
    task2.setCreatedAt(now);
    task2.setUpdatedAt(now);

    List<TaskBaseResponse> mockTasks =
        Arrays.asList(new TaskBaseResponse(task1), new TaskBaseResponse(task2));


    Mockito.when(this.taskSearchService.invoke(Mockito.any(TaskSearchRestRequest.class)))
        .thenReturn(mockTasks);
  }

  @Test
  void Taskの一覧が返されること() throws Exception {
    this.mockMvc.perform(get("/api/tasks")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Task 1"))
        .andExpect(jsonPath("$[0].priority.value").value(TaskPriority.MEDIUM.getValue()))
        .andExpect(jsonPath("$[1].id").value(2)).andExpect(jsonPath("$[1].name").value("Task 2"))
        .andExpect(jsonPath("$[1].priority.value").value(TaskPriority.LOW.getValue()));
  }
}
