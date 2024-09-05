package com.example.todo.service.projects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.example.todo.entity.Project;
import com.example.todo.repository.ProjectRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProjectListServiceTest {

  @Autowired
  private ProjectListService projectListService;

  @MockBean
  private ProjectRepository projectRepository;

  private List<Project> mockProjects;

  /**
   * @BeforeEach 各テストメソッドの実行前に実行されるメソッドを示す。このアノテーションが付与されたメソッド（setUp）は、各テストメソッドの実行前に実行される。
   *             この準備には、モックオブジェクトの設定や共通のテストデータの初期化が含まれる。
   */
  @BeforeEach
  void setUp() {
    Project project1 = new Project();
    project1.setId(1);
    project1.setName("Project 1");

    Project project2 = new Project();
    project2.setId(2);
    project2.setName("Project 2");

    this.mockProjects = Arrays.asList(project1, project2);

    // リポジトリがfindAll()メソッドでこのモックデータを返すように設定
    when(this.projectRepository.findAll()).thenReturn(this.mockProjects);
  }

  @Test
  void Projectの一覧が返されること() {
    // サービスのinvokeメソッドを実行
    List<Project> projects = this.projectListService.invoke();

    // 期待する結果と一致しているか確認
    assertEquals(2, projects.size());
    assertEquals(1, projects.get(0).getId());
    assertEquals(2, projects.get(1).getId());

    // RepositoryのfindAll()が1回だけ呼ばれたことを確認
    Mockito.verify(this.projectRepository, Mockito.times(1)).findAll();
  }
}
