package com.example.todo.service.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.example.todo.dto.request.projects.ProjectCreateRestRequest;
import com.example.todo.entity.Project;
import com.example.todo.repository.ProjectRepository;

@SpringBootTest
class ProjectCreateServiceTest {

  @MockBean
  private ProjectRepository projectRepository;

  private ProjectCreateService projectCreateService;

  @BeforeEach
  void setUp() {
    this.projectCreateService = new ProjectCreateService(this.projectRepository);
  }

  @Test
  void プロジェクトが作成されること() {
    // モックデータの設定
    Project mockProject = new Project();
    mockProject.setId(1);
    mockProject.setName("Test Project");
    mockProject.setSummary("Test Summary");

    // モックリポジトリの動作を設定
    when(this.projectRepository.save(any(Project.class))).thenReturn(mockProject);

    // リクエストオブジェクトを作成
    ProjectCreateRestRequest request = new ProjectCreateRestRequest("Test Project", "Test Summary");

    // サービスメソッドを実行
    Project createdProject = this.projectCreateService.invoke(request);

    // 結果を検証
    assertEquals(1, createdProject.getId());
    assertEquals("Test Project", createdProject.getName());
    assertEquals("Test Summary", createdProject.getSummary());

    // リポジトリのsaveメソッドが一回呼ばれたことを確認
    Mockito.verify(this.projectRepository, Mockito.times(1)).save(any(Project.class));
  }
}
