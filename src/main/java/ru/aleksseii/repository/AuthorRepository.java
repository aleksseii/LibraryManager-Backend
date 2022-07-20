package ru.aleksseii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aleksseii.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
