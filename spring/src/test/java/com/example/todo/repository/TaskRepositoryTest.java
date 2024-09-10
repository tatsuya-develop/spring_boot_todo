package com.example.todo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import com.example.todo.entity.Project;
import com.example.todo.entity.Task;

/**
 * @DataJpaTest JPA関連のコンポーネント（リポジトリ、エンティティマネージャーなど）をテストするためのアノテーション
 */
@DataJpaTest
/**
 * @AutoConfigureTestDatabase 埋め込みデータベースの設定を無効化し、テスト用で独自で用意したデータベースを使用するためのアノテーション
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
/**
 * @ActiveProfiles テスト用のプロファイルを指定するためのアノテーション（application-test.yml を読み込む）
 */
@ActiveProfiles("test")
class TaskRepositoryTest {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private ProjectRepository projectRepository;

  private Project project;

  void setUpForFindByProjectId() {
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
  void findByProjectId_指定されたプロジェクトIDに紐づくタスクを取得できること() {
    // テスト用のデータを準備
    this.setUpForFindByProjectId();

    // クエリメソッドを呼び出し
    List<Task> tasks = this.taskRepository.findByProjectId(this.project.getId());

    // 検証：取得したタスクが正しいこと
    assertEquals(2, tasks.size());
    assertTrue(
        tasks.stream().allMatch(task -> task.getProject().getId().equals(this.project.getId())));
  }

  @Test
  void findByProjectId_プロジェクトに紐づくタスクがない場合は空のリストが返ること() {
    // テスト用のデータを準備
    this.setUpForFindByProjectId();

    // 存在しないプロジェクトIDを使用
    List<Task> tasks = this.taskRepository.findByProjectId(-1);

    // 検証：空のリストが返ること
    assertTrue(tasks.isEmpty());
  }
}
