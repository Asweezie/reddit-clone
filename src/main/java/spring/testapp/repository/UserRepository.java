package spring.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.testapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
