package com.example.todo.controller.api;

import com.example.todo.dto.request.projects.ProjectCreateRestRequest;
import com.example.todo.entity.Project;
import com.example.todo.service.projects.ProjectCreateService;
import com.example.todo.service.projects.ProjectListService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/**
 * @WebMvcTest Spring MVCのテストに必要なコンポーネントだけを読み込む。今回だと、ProjectRestControllerが読み込まれる。
 *             Web層のコンポーネント（コントローラ、フィルタ、アドバイスなど）がテストに必要な最小限の設定でロードされ、他の層（サービス層やリポジトリ層など）はロードされない。
 *             これにより、Web層の動作を独立してテストすることが可能になる。注意点として、他のコンポーネント（ServiceやRepository）をMock化しておく必要がある。
 */
@WebMvcTest(ProjectRestController.class)
class ProjectCreateTest {

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

  @Test
  void 作成されたProjectが返されること() throws Exception {
    // モックレスポンスの設定
    Project mockProject = new Project();
    mockProject.setId(1);
    mockProject.setName("New Project");
    mockProject.setSummary("This is a new project");

    // モックサービスの振る舞いを定義
    // Mockito.when(モックオブジェクト.モックメソッド(引数)).thenReturn(戻り値)で、モックオブジェクトのメソッドの戻り値を設定できる。
    // この場合、projectCreateServiceのinvokeメソッドが呼び出された際に、mockProjectを返すように設定している。
    // Mockito.any(ProjectCreateRestRequest.class)は、ProjectCreateRestRequest型の任意の引数を受け付けることを示す。
    // これにより、どのような引数が渡されても、mockProjectが返される。
    // このようにして、モックオブジェクトの振る舞いを設定することで、テスト対象のコントローラが期待通りの動作をするかを検証できる。
    Mockito.when(this.projectCreateService.invoke(Mockito.any(ProjectCreateRestRequest.class)))
        .thenReturn(mockProject);

    // リクエストボディを作成
    String expectRequestBody = """
        {
            "name": "New Project",
            "summary": "This is a new project"
        }
        """;


    // テスト対象のコントローラの挙動を検証
    // mockMvc.perform()でHTTPリクエストをシミュレートし、コントローラの動作を検証できる。
    // post("/api/projects")でPOSTリクエストを送信し、contentType(MediaType.APPLICATION_JSON)でリクエストボディのContent-Typeを指定する。
    // content(this.expectRequestBody)でリクエストボディを指定する。
    // andExpect(status().isOk())でHTTPステータスが200であることを検証する。
    // andExpect(content().contentType(MediaType.APPLICATION_JSON))でレスポンスのContent-Typeがapplication/jsonであることを検証する。
    // andExpect(jsonPath("$.id").value(1))でレスポンスのidが1であることを検証する。
    // andExpect(jsonPath("$.name").value("New Project"))でレスポンスのnameが"New Project"であることを検証する。
    // andExpect(jsonPath("$.summary").value("This is a new project"))でレスポンスのsummaryが"This is a new
    // project"であることを検証する。
    this.mockMvc
        .perform(post("/api/projects").contentType(MediaType.APPLICATION_JSON)
            .content(expectRequestBody))
        .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("New Project"))
        .andExpect(jsonPath("$.summary").value("This is a new project"));
  }
}
