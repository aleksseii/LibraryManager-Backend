package ru.aleksseii.service;

import ru.aleksseii.domain.Author;

import java.util.List;

public interface AuthorService {

    Author insert(Author author);

    List<Author> getAll();

    Author getById(long id);

    Author getByName(String name);

    Author update(long id, String authorName);

    void deleteById(long id);
}
