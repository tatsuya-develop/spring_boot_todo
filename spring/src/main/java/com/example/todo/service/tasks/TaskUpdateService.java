package com.example.todo.service.tasks;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.example.todo.dto.request.tasks.TaskUpdateRestRequest;
import com.example.todo.dto.response.tasks.TaskBaseResponse;
import com.example.todo.entity.Tag;
import com.example.todo.entity.Task;
import com.example.todo.repository.ProjectRepository;
import com.example.todo.repository.TagRepository;
import com.example.todo.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskUpdateService {
  private final TaskRepository taskRepository;
  private final ProjectRepository projectRepository;
  private final TagRepository tagRepository;

  public TaskUpdateService(TaskRepository taskRepository, ProjectRepository projectRepository,
      TagRepository tagRepository) {
    this.taskRepository = taskRepository;
    this.projectRepository = projectRepository;
    this.tagRepository = tagRepository;
  }

  public TaskBaseResponse invoke(TaskUpdateRestRequest request) {
    Task task = this.taskRepository.findById(request.getId()).orElseThrow(
        () -> new EntityNotFoundException("Task not found with ID: " + request.getId()));

    if (request.getProjectId() != null) {
      task.setProject(this.projectRepository.findById(request.getProjectId().intValue())
          .orElseThrow(() -> new EntityNotFoundException(
              "Project not found with ID: " + request.getProjectId())));
    } else {
      task.setProject(null);
    }

    Set<Integer> currentTagIds =
        task.getTags().stream().map(Tag::getId).collect((Collectors.toSet()));

    if (!currentTagIds.equals(request.getTagIds())) {
      List<Tag> tags = this.tagRepository.findAllById(request.getTagIds());
      task.setTags(new HashSet<>(tags));
    }

    // DTOの同名プロパティをEntityにコピー（id は対象外）
    BeanUtils.copyProperties(request, task, "id");

    Task result = this.taskRepository.save(task);

    return new TaskBaseResponse(result);
  }
}
