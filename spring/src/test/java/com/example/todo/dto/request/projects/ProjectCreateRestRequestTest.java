package com.example.todo.dto.request.projects;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectCreateRestRequestTest {

  // バリデーションを実行するための Validator
  // Validator は、データが正しいかどうか（例：名前が空でないか）を自動でチェックしてくれるもの
  private Validator validator;

  @BeforeEach
  void setUp() {
    // バリデーションのためのインスタンスを生成
    // Validation.buildDefaultValidatorFactory() は Validator を生成するためのファクトリー（工場的なもの）を生成する
    // そのfactoryから、getValidator() で Validator を生成する
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    this.validator = factory.getValidator();
  }

  @Test
  void 有効なリクエストであること() {
    // 正常なインスタンスの作成
    ProjectCreateRestRequest request =
        new ProjectCreateRestRequest("Project name", "Project summary");

    // バリデーションを実行し、違反が無いことを確認
    Set<ConstraintViolation<ProjectCreateRestRequest>> violations =
        this.validator.validate(request);
    assertTrue(violations.isEmpty());

    // フィールドの値が正しく設定されていることを確認
    assertEquals("Project name", request.getName());
    assertEquals("Project summary", request.getSummary());
  }

  @Test
  void 名前が空の場合にエラーになること() {
    // 名前が空のインスタンスの作成
    ProjectCreateRestRequest request = new ProjectCreateRestRequest("", "Project summary");

    // バリデーションを実行し、違反があることを確認
    // validator.validate() でバリデーションを実行し、違反がある場合は ConstraintViolation の Set が返される
    // ConstraintViolation は、どのフィールドがどのルールに違反してくれるかを示すもの
    Set<ConstraintViolation<ProjectCreateRestRequest>> violations =
        this.validator.validate(request);
    // violations.size() で違反の数を取得し、assertEquals() で違反が1つであることを確認
    assertEquals(1, violations.size());

    // nameフィールドが@NotEmptyの違反であることを確認
    // violations.iterator().next() で1つ目の違反を取得し、その違反が name フィールドの @NotEmpty であることを確認
    ConstraintViolation<ProjectCreateRestRequest> violation = violations.iterator().next();
    assertEquals("name", violation.getPropertyPath().toString());
    assertEquals("must not be empty", violation.getMessage());
  }

  @Test
  void 概要が空でも有効なリクエストであること() {
    // 概要が空のインスタンスの作成
    ProjectCreateRestRequest request = new ProjectCreateRestRequest("Project name", "");

    // バリデーションを実行し、違反が無いことを確認
    Set<ConstraintViolation<ProjectCreateRestRequest>> violations =
        this.validator.validate(request);
    assertTrue(violations.isEmpty());

    // フィールドの値が正しく設定されていることを確認
    assertEquals("Project name", request.getName());
    assertEquals("", request.getSummary());
  }
}
