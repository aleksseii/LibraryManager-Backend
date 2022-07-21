package ru.aleksseii.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.aleksseii.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuthorRepositoryTest {

    private static final long EXISTING_AUTHOR_COUNT = 3;

    private static final long EXISTING_ID1 = 1;
    private static final long EXISTING_ID2 = 2;
    private static final long EXISTING_ID3 = 3;

    private static final String EXISTING_NAME1 = "First author name";
    private static final String EXISTING_NAME2 = "Second author name";
    private static final String EXISTING_NAME3 = "Third author name";

    @Autowired
    private AuthorRepository authorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Should insert author")
    void shouldInsertAuthor() {

        Author expectedAuthor = Author.builder()
                .id(EXISTING_AUTHOR_COUNT + 1)
                .name("Ivan")
                .build();

        authorRepository.save(expectedAuthor);
        Author actualAuthor = authorRepository.getReferenceById(EXISTING_AUTHOR_COUNT + 1);

        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("Should get author by id")
    void shouldGetAuthorById() {

        Author expectedAuthor = Author.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .build();
        Author actualAuthor = authorRepository.getReferenceById(EXISTING_ID1);

        assertThat(expectedAuthor).isEqualTo(actualAuthor);
    }


    @Test
    @DisplayName("Should delete author by id")
    void shouldDeleteAuthorById() {

        int beforeSize = authorRepository.findAll().size();
        authorRepository.deleteById(EXISTING_ID2);

        entityManager.flush();
        int afterSize = authorRepository.findAll().size();

        assertThat(beforeSize).isEqualTo(afterSize + 1);
    }

    @Test
    @DisplayName("Should find author by name")
    void shouldFindAuthorByName() {

        Author expectedAuthor = Author.builder()
                .id(EXISTING_ID1)
                .name("First author name")
                .build();

        Author actualAuthor = authorRepository.findByName(EXISTING_NAME1);

        assertThat(expectedAuthor).isEqualTo(actualAuthor);
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

        assertThat(expectedAuthor).isEqualTo(actualAuthor);
    }

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

        assertThat(authorRepository.findAll().size()).isEqualTo(EXISTING_AUTHOR_COUNT);
        assertThat(authorRepository.findAll())
                .containsExactlyInAnyOrder(expectedAuthor1, expectedAuthor2, expectedAuthor3);
    }
}
