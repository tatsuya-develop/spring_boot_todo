package com.example.todo.repository.specification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import com.example.todo.entity.Project;
import com.example.todo.entity.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.ProjectRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TaskSpecificationTest {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private ProjectRepository projectRepository;

  private Project project;

  private TaskSpecification taskSpecification;

  @BeforeEach
  void setUp() {
    this.taskSpecification = new TaskSpecification();

    // テスト用のプロジェクトを作成し保存
    this.project = new Project();
    this.project.setName("Test Project");
    this.project.setSummary("This is a test project");
    this.projectRepository.save(this.project);

    // テスト用のタスクを複数作成し保存
    Task task1 = new Task();
    task1.setName("Test Task 1");
    task1.setProject(this.project);
    this.taskRepository.save(task1);

    Task task2 = new Task();
    task2.setName("Test Task 2");
    task2.setProject(this.project);
    this.taskRepository.save(task2);

    Task taskWithoutProject = new Task();
    taskWithoutProject.setName("Task Without Project");
    this.taskRepository.save(taskWithoutProject);
  }

  @Test
  void projectIdIsNull_プロジェクトがnullのタスクを取得できること() {
    // Specificationの作成
    Specification<Task> spec = this.taskSpecification.projectIdIsNull();

    // クエリ実行
    List<Task> tasks = this.taskRepository.findAll(spec);

    // 検証: プロジェクトがnullのタスクのみが取得されること
    assertEquals(1, tasks.size());
    assertTrue(tasks.get(0).getProject() == null);
  }

  @Test
  void projectIdEqual_指定されたプロジェクトIDのタスクを取得できること() {
    // Specificationの作成
    Specification<Task> spec = this.taskSpecification.projectIdEqual(this.project.getId());

    // クエリ実行
    List<Task> tasks = this.taskRepository.findAll(spec);

    // 検証: 取得したタスクが指定されたプロジェクトに関連付けられていること
    assertEquals(2, tasks.size());
    assertTrue(
        tasks.stream().allMatch(task -> task.getProject().getId().equals(this.project.getId())));
  }

  @Test
  void isCompleted_未完了タスクが取得できること() {
    // 完了していないタスクの取得（completedAtがnull）
    Specification<Task> spec = this.taskSpecification.isCompleted(false);

    // クエリ実行
    List<Task> tasks = this.taskRepository.findAll(spec);

    // 検証: 取得したタスクが未完了であること
    assertTrue(tasks.stream().allMatch(task -> task.getCompletedAt() == null));
  }

  @Test
  void isCompleted_完了済みタスクが取得できること() {
    // テスト用のタスクを完了させる
    Task task = this.taskRepository.findAll().get(0);
    task.setCompletedAt(LocalDateTime.now());
    this.taskRepository.save(task);

    // 完了済みタスクの取得（completedAtがnullでないもの）
    Specification<Task> spec = this.taskSpecification.isCompleted(true);

    // クエリ実行
    List<Task> tasks = this.taskRepository.findAll(spec);

    // 検証: 取得したタスクが完了済みであること
    assertTrue(tasks.stream().allMatch(t -> t.getCompletedAt() != null));
  }
}
