package ru.aleksseii.library_manager_backend.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.aleksseii.library_manager_backend.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DisplayName("class GenreRepository")
@DataJpaTest
class GenreRepositoryTest {

    private static final long EXISTING_GENRE_COUNT = 3L;

    private static final long EXISTING_ID1 = 1L;
    private static final long EXISTING_ID2 = 2L;
    private static final long EXISTING_ID3 = 3L;

    private static final String EXISTING_NAME1 = "First genre name";
    private static final String EXISTING_NAME2 = "Second genre name";
    private static final String EXISTING_NAME3 = "Third genre name";

    @Autowired
    private GenreRepository genreRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Should get all genres")
    void shouldGetAllGenres() {

        Genre expectedGenre1 = Genre.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .build();
        Genre expectedGenre2 = Genre.builder()
                .id(EXISTING_ID2)
                .name(EXISTING_NAME2)
                .build();
        Genre expectedGenre3 = Genre.builder()
                .id(EXISTING_ID3)
                .name(EXISTING_NAME3)
                .build();

        Assertions.assertThat(genreRepository.findAll().size()).isEqualTo(EXISTING_GENRE_COUNT);
        Assertions.assertThat(genreRepository.findAll())
                .containsExactlyInAnyOrder(expectedGenre1, expectedGenre2, expectedGenre3);
    }

    @Test
    @DisplayName("Should get genres by id")
    void shouldGetGenresById() {

        Genre expectedGenre = Genre.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .build();
        Genre actualGenre = genreRepository.getReferenceById(EXISTING_ID1);

        Assertions.assertThat(expectedGenre).isEqualTo(actualGenre);
    }

    @Test
    @DisplayName("Should get genre by name")
    void shouldGetGenreByName() {

        Genre expectedGenre = Genre.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .build();

        Genre actualGenre = genreRepository.findByName(EXISTING_NAME1);

        Assertions.assertThat(expectedGenre).isEqualTo(actualGenre);
    }

    @Test
    @DisplayName("Should insert genre")
    void shouldInsertGenre() {

        Genre expectedGenre = Genre.builder()
                .id(EXISTING_GENRE_COUNT + 1)
                .name("qwerty")
                .build();
        genreRepository.save(expectedGenre);
        Genre actualGenre = genreRepository.getReferenceById(EXISTING_GENRE_COUNT + 1);

        Assertions.assertThat(expectedGenre).isEqualTo(actualGenre);
    }

    @Test
    @DisplayName("Should delete genre by id")
    void shouldDeleteGenreById() {

        int beforeSize = genreRepository.findAll().size();
        genreRepository.deleteById(EXISTING_ID2);

        entityManager.flush();
        int afterSize = genreRepository.findAll().size();

        Assertions.assertThat(beforeSize).isEqualTo(afterSize + 1);
    }
}