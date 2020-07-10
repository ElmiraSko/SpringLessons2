package ru.erasko.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.erasko.model.Picture;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
