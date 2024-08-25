package com.example.todo.service.tags;

import org.springframework.stereotype.Service;
import com.example.todo.dto.request.tags.TagCreateRestRequest;
import com.example.todo.entity.Tag;
import com.example.todo.repository.TagRepository;

@Service
public class TagCreateService {
  private final TagRepository tagRepository;

  public TagCreateService(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  public Tag invoke(TagCreateRestRequest request) {
    Tag tag = new Tag();

    tag.setName(request.getName());
    tag.setSummary(request.getSummary());

    return this.tagRepository.save(tag);
  }
}
