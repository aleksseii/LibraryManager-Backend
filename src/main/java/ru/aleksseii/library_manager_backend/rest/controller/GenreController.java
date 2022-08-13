package ru.aleksseii.library_manager_backend.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.aleksseii.library_manager_backend.domain.Genre;
import ru.aleksseii.library_manager_backend.rest.dto.GenreDTO;
import ru.aleksseii.library_manager_backend.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping(value = "/genre")
    public List<GenreDTO> getAllGenres() {

        return genreService.getAll().stream()
                .map(GenreDTO::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/genre", params = { "name" })
    public GenreDTO getGenreByName(@RequestParam(name = "name") String genreName) {

        Genre genre = genreService.getByName(genreName);
        return GenreDTO.toDTO(genre);
    }

    @GetMapping(value = "/genre/{id}")
    public GenreDTO getGenreById(@PathVariable(name = "id") long genreId) {

        Genre genre = genreService.getById(genreId);
        return GenreDTO.toDTO(genre);
    }

    @PostMapping(value = "/genre")
    public GenreDTO insertGenre(@RequestBody GenreDTO genreDTO) {

        Genre insertedGenre = genreService.insert(GenreDTO.toDomainObject(genreDTO));
        return GenreDTO.toDTO(insertedGenre);
    }

    @PutMapping(value = "/genre/{id}", params = { "name" })
    public GenreDTO updateGenre(@PathVariable(name = "id") long genreId,
                                @RequestParam(name = "name") String genreName) {

        Genre updatedGenre = genreService.update(genreId, genreName);
        return GenreDTO.toDTO(updatedGenre);
    }

    @DeleteMapping(value = "genre/{id}")
    public void deleteGenre(@PathVariable(name = "id") long genreId) {

        genreService.deleteById(genreId);
    }
}
