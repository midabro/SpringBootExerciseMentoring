package user.com.sii.springbootexercisementoring;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

  Optional<User> findByLoginName(String loginName);

}
