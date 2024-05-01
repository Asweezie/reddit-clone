package spring.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.testapp.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
