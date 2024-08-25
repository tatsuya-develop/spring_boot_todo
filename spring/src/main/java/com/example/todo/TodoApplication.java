package com.example.todo;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class TodoApplication {

  public static void main(String[] args) {
    SpringApplication.run(TodoApplication.class, args);
  }

  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"));
  }
}
