package ru.aleksseii.library_manager_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aleksseii.library_manager_backend.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByName(String name);
}
