package ru.aleksseii.library_manager_backend.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.aleksseii.library_manager_backend.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;

@DisplayName("class AuthorRepository")
@DataJpaTest
class AuthorRepositoryTest {

    private static final long EXISTING_AUTHOR_COUNT = 3L;

    private static final long EXISTING_ID1 = 1L;
    private static final long EXISTING_ID2 = 2L;
    private static final long EXISTING_ID3 = 3L;

    private static final String EXISTING_NAME1 = "First author name";
    private static final String EXISTING_NAME2 = "Second author name";
    private static final String EXISTING_NAME3 = "Third author name";

    @Autowired
    private AuthorRepository authorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Should get all authors")
    void shouldGetAllAuthors() {

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

        Assertions.assertThat(authorRepository.findAll().size()).isEqualTo(EXISTING_AUTHOR_COUNT);
        Assertions.assertThat(authorRepository.findAll())
                .containsExactlyInAnyOrder(expectedAuthor1, expectedAuthor2, expectedAuthor3);
    }

    @Test
    @DisplayName("Should get author by id")
    void shouldGetAuthorById() {

        Author expectedAuthor = Author.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .build();
        Author actualAuthor = authorRepository.getReferenceById(EXISTING_ID1);

        Assertions.assertThat(expectedAuthor).isEqualTo(actualAuthor);
    }

    @Test
    @DisplayName("Should find author by name")
    void shouldFindAuthorByName() {

        Author expectedAuthor = Author.builder()
                .id(EXISTING_ID1)
                .name("First author name")
                .build();

        Author actualAuthor = authorRepository.findByName(EXISTING_NAME1);

        Assertions.assertThat(expectedAuthor).isEqualTo(actualAuthor);
    }

    @Test
    @DisplayName("Should insert author")
    void shouldInsertAuthor() {

        Author expectedAuthor = Author.builder()
                .id(EXISTING_AUTHOR_COUNT + 1)
                .name("Ivan")
                .build();

        authorRepository.save(expectedAuthor);
        Author actualAuthor = authorRepository.getReferenceById(EXISTING_AUTHOR_COUNT + 1);

        Assertions.assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("Should update author")
    void shouldUpdateAuthor() {

        Author expectedAuthor = Author.builder()
                .id(EXISTING_ID1)
                .name("Ivan")
                .build();
        authorRepository.save(expectedAuthor);
        Author actualAuthor = authorRepository.getReferenceById(EXISTING_ID1);

        Assertions.assertThat(expectedAuthor).isEqualTo(actualAuthor);
    }

    @Test
    @DisplayName("Should delete author by id")
    void shouldDeleteAuthorById() {

        int beforeSize = authorRepository.findAll().size();
        authorRepository.deleteById(EXISTING_ID2);

        entityManager.flush();
        int afterSize = authorRepository.findAll().size();

        Assertions.assertThat(beforeSize).isEqualTo(afterSize + 1);
    }
}
