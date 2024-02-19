package com.springbootexercisementoring.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootExerciseMentoringApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootExerciseMentoringApplication.class, args);
  }

}
