package com.example.todo.controller.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todo.dto.request.tasks.TaskCompleteRestRequest;
import com.example.todo.dto.request.tasks.TaskCreateRestRequest;
import com.example.todo.dto.request.tasks.TaskListRestRequest;
import com.example.todo.dto.request.tasks.TaskSummaryRestRequest;
import com.example.todo.dto.request.tasks.TaskUpdateRestRequest;
import com.example.todo.dto.response.tasks.TaskBaseResponse;
import com.example.todo.dto.response.tasks.TaskSummaryResponse;
import com.example.todo.service.tasks.TaskCompleteService;
import com.example.todo.service.tasks.TaskCreateService;
import com.example.todo.service.tasks.TaskDeleteService;
import com.example.todo.service.tasks.TaskListService;
import com.example.todo.service.tasks.TaskSummaryService;
import com.example.todo.service.tasks.TaskToggleService;
import com.example.todo.service.tasks.TaskUpdateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {
  private final TaskListService taskListService;
  private final TaskSummaryService taskSummaryService;
  private final TaskCreateService taskCreateService;
  private final TaskUpdateService taskUpdateService;
  private final TaskDeleteService taskDeleteService;
  private final TaskCompleteService taskCompleteService;
  private final TaskToggleService taskToggleService;

  public TaskRestController(TaskListService taskListService, TaskSummaryService taskSummaryService,
      TaskCreateService taskCreateService, TaskUpdateService taskUpdateService,
      TaskDeleteService taskDeleteService, TaskCompleteService taskCompleteService,
      TaskToggleService taskToggleService) {
    this.taskListService = taskListService;
    this.taskCreateService = taskCreateService;
    this.taskUpdateService = taskUpdateService;
    this.taskDeleteService = taskDeleteService;
    this.taskCompleteService = taskCompleteService;
    this.taskSummaryService = taskSummaryService;
    this.taskToggleService = taskToggleService;
  }

  @GetMapping
  public ResponseEntity<List<TaskBaseResponse>> get(
      @Valid @ModelAttribute TaskListRestRequest request) {
    List<TaskBaseResponse> tasks = this.taskListService.invoke(request);
    return ResponseEntity.ok(tasks);
  }

  @GetMapping("/summary")
  public ResponseEntity<TaskSummaryResponse> getSummary(
      @Valid @ModelAttribute TaskSummaryRestRequest request) {
    TaskSummaryResponse summary = this.taskSummaryService.invoke(request);
    return ResponseEntity.ok(summary);
  }

  @PostMapping
  public ResponseEntity<TaskBaseResponse> create(
      @Valid @RequestBody TaskCreateRestRequest request) {
    TaskBaseResponse task = this.taskCreateService.invoke(request);
    return ResponseEntity.ok(task);
  }

  @PutMapping
  public ResponseEntity<TaskBaseResponse> update(
      @Valid @RequestBody TaskUpdateRestRequest request) {
    TaskBaseResponse task = this.taskUpdateService.invoke(request);
    return ResponseEntity.ok(task);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    this.taskDeleteService.invoke(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/done")
  public ResponseEntity<TaskBaseResponse> invoke(
      @Valid @RequestBody TaskCompleteRestRequest request) {
    TaskBaseResponse task = this.taskCompleteService.invoke(request);
    return ResponseEntity.ok(task);
  }

  @PutMapping("/{id}/toggle")
  public ResponseEntity<TaskBaseResponse> toggle(@PathVariable Integer id) {
    TaskBaseResponse task = this.taskToggleService.invoke(id);
    return ResponseEntity.ok(task);
  }
}
