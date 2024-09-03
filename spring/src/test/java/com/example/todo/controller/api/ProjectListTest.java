package com.example.todo.controller.api;

import com.example.todo.entity.Project;
import com.example.todo.service.projects.ProjectCreateService;
import com.example.todo.service.projects.ProjectListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @WebMvcTest Spring MVCのテストに必要なコンポーネントだけを読み込む。今回だと、ProjectRestControllerが読み込まれる。
 *             Web層のコンポーネント（コントローラ、フィルタ、アドバイスなど）がテストに必要な最小限の設定でロードされ、他の層（サービス層やリポジトリ層など）はロードされない。
 *             これにより、Web層の動作を独立してテストすることが可能になる。注意点として、他のコンポーネント（ServiceやRepository）をMock化しておく必要がある。
 */
@WebMvcTest(ProjectRestController.class)
class ProjectListTest {
  /**
   * @Autowired Springの依存性注入（DI）機能を使用して、MockMvcオブジェクトをこのテストクラスに自動的に注入する。
   *            MockMvcはSpringMVCのモックオブジェクトで、Webアプリケーションのコントローラーレベルのテストに使用される。
   */
  @Autowired
  private MockMvc mockMvc;

  /**
   * @MockBean SpringのアプリケーションコンテキストにモックされたBeanを登録する。
   *           ProjectCreateServiceとProjectListServiceはモックとして登録され、このテスト内で使用される。
   *           これにより、これらのサービスの実際の動作をシミュレートしながら、依存する他のコンポーネントの挙動をコントロールすることができる。
   */
  @MockBean
  private ProjectCreateService projectCreateService;

  @MockBean
  private ProjectListService projectListService;

  private List<Project> mockProjects;

  /**
   * @BeforeEach 各テストメソッドの実行前に実行されるメソッドを示す。このアノテーションが付与されたメソッド（setUp）は、各テストメソッドの実行前に実行される。
   *             この準備には、モックオブジェクトの設定や共通のテストデータの初期化が含まれる。
   */
  @BeforeEach
  void setUp() {
    // モックレスポンスの設定
    Project project1 = new Project();
    project1.setId(1);
    project1.setName("Project 1");

    Project project2 = new Project();
    project2.setId(2);
    project2.setName("Project 2");

    this.mockProjects = Arrays.asList(project1, project2);

    Mockito.when(projectListService.invoke()).thenReturn(this.mockProjects);
  }

  @Test
  void Projectの一覧が返されること() throws Exception {
    mockMvc.perform(get("/api/projects").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].name").value("Project 1"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].name").value("Project 2"));
  }
}
