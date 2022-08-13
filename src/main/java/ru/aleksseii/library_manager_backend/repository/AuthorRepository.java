package ru.aleksseii.library_manager_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aleksseii.library_manager_backend.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByName(String name);
}
