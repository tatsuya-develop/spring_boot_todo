package com.example.todo.controller.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todo.dto.request.tags.TagCreateRestRequest;
import com.example.todo.entity.Tag;
import com.example.todo.service.tags.TagCreateService;
import com.example.todo.service.tags.TagListService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/tags")
public class TagRestController {
  private final TagListService tagListService;
  private final TagCreateService tagCreateService;

  public TagRestController(TagListService tagListService, TagCreateService tagCreateService) {
    this.tagListService = tagListService;
    this.tagCreateService = tagCreateService;
  }

  @GetMapping
  public ResponseEntity<List<Tag>> get() {
    List<Tag> tags = this.tagListService.invoke();
    return ResponseEntity.ok(tags);
  }


  @PostMapping
  public ResponseEntity<Tag> create(@Valid @RequestBody TagCreateRestRequest request) {
    Tag tag = this.tagCreateService.invoke(request);
    return ResponseEntity.ok(tag);
  }
}
