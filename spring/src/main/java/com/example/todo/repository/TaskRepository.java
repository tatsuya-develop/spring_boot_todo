package com.example.todo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.todo.entity.Task;

public interface TaskRepository
    extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

  @Query(value = "SELECT t FROM Task t WHERE t.project.id = :projectId")
  List<Task> findByProjectId(@Param("projectId") int projectId);
}
