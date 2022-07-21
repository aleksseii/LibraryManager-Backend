package ru.aleksseii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aleksseii.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByName(String name);
}
