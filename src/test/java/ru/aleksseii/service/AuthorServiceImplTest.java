package ru.aleksseii.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aleksseii.domain.Author;
import ru.aleksseii.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

@DisplayName("class AuthorServiceImplTest")
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    private static final long EXISTING_ID1 = 1;
    private static final long EXISTING_ID2 = 2;
    private static final long EXISTING_ID3 = 3;

    private static final String EXISTING_NAME1 = "First author name";
    private static final String EXISTING_NAME2 = "Second author name";
    private static final String EXISTING_NAME3 = "Third author name";

    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    private List<Author> authors;

    @BeforeEach
    void init() {
        authors = new ArrayList<>();

        Author expectedAuthor1 = Author.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .build();
        Author expectedAuthor2 = Author.builder()
                .id(EXISTING_ID2)
                .name(EXISTING_NAME2)
                .build();
        Author expectedAuthor3 = Author.builder()
                .id(EXISTING_ID3)
                .name(EXISTING_NAME3)
                .build();
        authors.add(expectedAuthor1);
        authors.add(expectedAuthor2);
        authors.add(expectedAuthor3);

        authorService = new AuthorServiceImpl(authorRepository);
    }

    @Test
    @DisplayName("Should get all authors")
    void shouldGetAllAuthors() {
        Mockito.when(authorRepository.findAll()).thenReturn(authors);

        List<Author> actualAuthors = authorService.getAll();

        Assertions.assertThat(authors).isEqualTo(actualAuthors);
    }

    @Test
    @DisplayName("Should get author by id")
    void shouldGetAuthorById() {

        Author expectedAuthor = Author.builder()
                .id(4L)
                .name("Ivan")
                .build();
        Mockito.when(authorRepository.getReferenceById(4L)).thenReturn(expectedAuthor);

        Author actualAuthor = authorService.getById(4L);

        Assertions.assertThat(expectedAuthor).isEqualTo(actualAuthor);
    }

    @Test
    @DisplayName("Should get author by name")
    void shouldGetAuthorByName() {

        Author expectedAuthor = Author.builder()
                .id(4L)
                .name("Ivan")
                .build();
        Mockito.when(authorRepository.findByName("Ivan")).thenReturn(expectedAuthor);

        Author actualAuthor = authorService.getByName("Ivan");

        Assertions.assertThat(expectedAuthor).isEqualTo(actualAuthor);
    }

    @Test
    @DisplayName("Should update")
    void shouldUpdate() {

        Author expectedAuthor = Author.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .build();
        Mockito.when(authorRepository.save(expectedAuthor)).thenReturn(expectedAuthor);

        Author actualAuthor = authorService.update(EXISTING_ID1, EXISTING_NAME1);

        Assertions.assertThat(expectedAuthor).isEqualTo(actualAuthor);
    }
}
