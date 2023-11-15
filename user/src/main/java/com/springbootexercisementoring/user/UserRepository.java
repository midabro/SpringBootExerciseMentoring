package com.springbootexercisementoring.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

  Optional<User> findByName(String name);

}
