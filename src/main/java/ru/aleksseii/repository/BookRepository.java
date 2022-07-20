package ru.aleksseii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aleksseii.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
