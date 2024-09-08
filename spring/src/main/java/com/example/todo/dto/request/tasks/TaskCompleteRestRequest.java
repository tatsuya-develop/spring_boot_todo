package com.example.todo.dto.request.tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TaskCompleteRestRequest {

  @NotNull
  private Integer id;

  // 単一パラメータの場合はJsonCreatorで定義が必要
  // 複数パラメータの場合は、@RequiredArgsConstructorアノテーションによる自動コンストラクタで良い
  @JsonCreator
  public TaskCompleteRestRequest(@JsonProperty("id") Integer id) {
    this.id = id;
  }
}
