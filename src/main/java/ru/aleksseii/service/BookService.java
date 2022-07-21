package ru.aleksseii.service;

import ru.aleksseii.domain.Book;

import java.util.List;

public interface BookService {

    Book insert(
            String bookName,
            String authorName,
            String genreName
    );

    List<Book> getAll();

    Book getById(long id);

    List<Book> getByName(String bookName);

    Book update(
            long id,
            String bookName,
            String authorName,
            String genreName
    );

    void deleteById(long id);
}
