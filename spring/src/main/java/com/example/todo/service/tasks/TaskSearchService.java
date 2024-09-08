package com.example.todo.service.tasks;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.todo.dto.request.tasks.TaskSearchRestRequest;
import com.example.todo.dto.response.tasks.TaskBaseResponse;
import com.example.todo.entity.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.specification.TaskSpecification;

@Service
public class TaskSearchService {
  private final TaskRepository taskRepository;

  public TaskSearchService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<TaskBaseResponse> invoke(TaskSearchRestRequest request) {
    TaskSpecification spec = new TaskSpecification();
    Sort sort = Sort.by(Sort.Order.desc("completedAt"), Sort.Order.asc("deadlineAt"),
        Sort.Order.desc("id"));

    Specification<Task> combinedSpec =
        request.getProjectId() == null ? Specification.where(spec.projectIdIsNull())
            : Specification.where(spec.projectIdEqual(request.getProjectId()));

    List<Task> tasks = this.taskRepository.findAll(combinedSpec, sort);

    return tasks.stream().map(task -> new TaskBaseResponse(task)).collect(Collectors.toList());
  }
}
