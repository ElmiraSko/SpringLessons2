package ru.erasko.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.erasko.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
