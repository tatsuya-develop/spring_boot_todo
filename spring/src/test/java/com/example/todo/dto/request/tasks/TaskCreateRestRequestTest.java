package com.example.todo.dto.request.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.todo.enums.task.TaskPriority;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class TaskCreateRestRequestTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    this.validator = factory.getValidator();
  }

  @Test
  void 有効なリクエストであること() {
    TaskCreateRestRequest request = new TaskCreateRestRequest("Task Name", null, TaskPriority.LOW);

    Set<ConstraintViolation<TaskCreateRestRequest>> violations = this.validator.validate(request);

    assertEquals(0, violations.size());
  }

  @Test
  void タスク名が空の場合にエラーになること() {
    TaskCreateRestRequest request = new TaskCreateRestRequest("", 1, TaskPriority.LOW);

    Set<ConstraintViolation<TaskCreateRestRequest>> violations = this.validator.validate(request);

    assertEquals(1, violations.size());

    ConstraintViolation<TaskCreateRestRequest> violation = violations.iterator().next();
    assertEquals("name", violation.getPropertyPath().toString());
    assertEquals("must not be empty", violation.getMessage());
  }

  @Test
  void 優先度がnullの場合にエラーになること() {
    TaskCreateRestRequest request = new TaskCreateRestRequest("Task Name", null, null);

    Set<ConstraintViolation<TaskCreateRestRequest>> violations = this.validator.validate(request);

    assertEquals(1, violations.size());

    ConstraintViolation<TaskCreateRestRequest> violation = violations.iterator().next();
    assertEquals("priority", violation.getPropertyPath().toString());
    assertEquals("must not be null", violation.getMessage());
  }
}
