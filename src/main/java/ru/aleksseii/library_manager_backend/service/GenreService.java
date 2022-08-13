package ru.aleksseii.library_manager_backend.service;

import ru.aleksseii.library_manager_backend.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre insert(Genre genre);

    List<Genre> getAll();

    Genre getById(long id);

    Genre getByName(String name);

    Genre update(long id, String genreName);

    void deleteById(long id);
}
