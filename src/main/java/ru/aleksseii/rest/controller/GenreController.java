package ru.aleksseii.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.aleksseii.domain.Genre;
import ru.aleksseii.rest.dto.GenreDTO;
import ru.aleksseii.service.GenreService;

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

    @GetMapping(value = "/genre", params = "name")
    public GenreDTO getGenreByName(@RequestParam(name = "name") String genreName) {

        Genre genre = genreService.getByName(genreName);
        return GenreDTO.toDTO(genre);
    }

    @GetMapping("/genre/{id}")
    public GenreDTO getGenreById(@PathVariable(name = "id") long genreId) {

        Genre genre = genreService.getById(genreId);
        return GenreDTO.toDTO(genre);
    }

    @PostMapping("/genre")
    public GenreDTO insertGenre(@RequestBody GenreDTO genreDTO) {

        Genre insertedGenre = genreService.insert(GenreDTO.toDomainObject(genreDTO));
        return GenreDTO.toDTO(insertedGenre);
    }

    @PutMapping("/genre/{id}")
    public GenreDTO updateGenre(@PathVariable(name = "id") long genreId,
                                @RequestParam(name = "newName") String genreName) {

        Genre updatedGenre = genreService.update(genreId, genreName);
        return GenreDTO.toDTO(updatedGenre);
    }

    @DeleteMapping("genre/{id}")
    public void deleteGenre(@PathVariable(name = "id") long genreId) {

        genreService.deleteById(genreId);
    }
}
