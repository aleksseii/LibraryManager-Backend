package ru.aleksseii.library_manager_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aleksseii.library_manager_backend.domain.Genre;
import ru.aleksseii.library_manager_backend.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre insert(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getById(long id) {
        return genreRepository.getReferenceById(id);
    }

    @Override
    public Genre getByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    public Genre update(long id, String genreName) {

        Genre genre = Genre.builder()
                .id(id)
                .name(genreName)
                .build();

        return genreRepository.save(genre);
    }

    @Override
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }
}
