package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todo.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
