package spring.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.testapp.model.Image;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByUserId(Long userId);
}
