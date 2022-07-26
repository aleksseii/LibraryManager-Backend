package ru.aleksseii.library_manager_backend.service;

import ru.aleksseii.library_manager_backend.domain.Author;

import java.util.List;

public interface AuthorService {

    Author insert(Author author);

    List<Author> getAll();

    Author getById(long id);

    Author getByName(String name);

    Author update(long id, String authorName);

    void deleteById(long id);
}
