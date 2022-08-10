package ru.aleksseii.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.aleksseii.domain.Author;
import ru.aleksseii.rest.dto.AuthorDTO;
import ru.aleksseii.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author")
    public List<AuthorDTO> getAllAuthors() {

        return authorService.getAll().stream()
                .map(AuthorDTO::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/author/{id}")
    public AuthorDTO getAuthorById(@PathVariable(name = "id") long authorId) {

        Author author = authorService.getById(authorId);
        return AuthorDTO.toDTO(author);
    }

    @PostMapping("/author")
    public AuthorDTO insertAuthor(@RequestBody AuthorDTO authorDTO) {

        Author insertedAuthor = authorService.insert(AuthorDTO.toDomainObject(authorDTO));
        return AuthorDTO.toDTO(insertedAuthor);
    }

    @PutMapping("/author/{id}")
    public AuthorDTO updateAuthor(@PathVariable(name = "id") long authorId,
                                  @RequestParam(name = "name") String authorName) {

        Author updatedAuthor = authorService.update(authorId, authorName);
        return AuthorDTO.toDTO(updatedAuthor);
    }

    @DeleteMapping("/author/{id}")
    public void deleteAuthor(@PathVariable(name = "id") long authorId) {

        authorService.deleteById(authorId);
    }
}
