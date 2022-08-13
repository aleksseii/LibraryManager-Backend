package ru.aleksseii.library_manager_backend.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.aleksseii.library_manager_backend.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph(attributePaths = { "author", "genre", "comments" })
    List<Book> findAll();

    List<Book> findByName(String name);
}
