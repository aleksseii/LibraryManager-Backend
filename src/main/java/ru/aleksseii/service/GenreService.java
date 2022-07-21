package ru.aleksseii.service;

import ru.aleksseii.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre insert(Genre genre);

    List<Genre> getAll();

    Genre getById(long id);

    Genre getByName(String name);

    Genre update(long id, String genreName);

    void deleteById(long id);
}
