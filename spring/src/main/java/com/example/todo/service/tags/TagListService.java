package com.example.todo.service.tags;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.todo.entity.Tag;
import com.example.todo.repository.TagRepository;

@Service
public class TagListService {
  private final TagRepository tagRepository;

  public TagListService(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  public List<Tag> invoke() {
    return this.tagRepository.findAll();
  }
}
