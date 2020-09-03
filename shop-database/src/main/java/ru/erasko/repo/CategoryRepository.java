package ru.erasko.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.erasko.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findById(Long id);
}
