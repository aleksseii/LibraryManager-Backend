package ru.aleksseii.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;
import ru.aleksseii.domain.Book;

import java.util.ArrayList;
import java.util.List;

@DisplayName("class BookRepository")
@DataJpaTest
class BookRepositoryTest {

    private static final long EXISTING_BOOK_COUNT = 4L;

    private static final long EXISTING_ID1 = 1L;
    private static final long EXISTING_ID2 = 2L;
    private static final long EXISTING_ID3 = 3L;
    private static final long EXISTING_ID4 = 4L;

    private static final String EXISTING_NAME1 = "First book name";
    private static final String EXISTING_NAME2 = "Second book name";
    private static final String EXISTING_NAME3 = "Third book name";
    private static final String EXISTING_NAME4 = "Third book name";

    public static final long AUTHOR_ID1 = 1L;
    public static final long AUTHOR_ID2 = 2L;
    public static final long AUTHOR_ID3 = 3L;

    public static final long GENRE_ID1 = 1L;
    public static final long GENRE_ID2 = 2L;
    public static final long GENRE_ID3 = 3L;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Should get all books")
    void shouldGetAllBooks() {

        Assertions.assertThat(bookRepository.findAll().size()).isEqualTo(EXISTING_BOOK_COUNT);
    }

    @Test
    @DisplayName("Should get book by id")
    void shouldGetBookById() {

        Book expectedBook = Book.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .author(authorRepository.getReferenceById(AUTHOR_ID1))
                .genre(genreRepository.getReferenceById(GENRE_ID1))
                .build();

        Book actualBook = bookRepository.getReferenceById(EXISTING_ID1);

        Assertions.assertThat(expectedBook.getId()).isEqualTo(actualBook.getId());
        Assertions.assertThat(expectedBook.getName()).isEqualTo(actualBook.getName());
        Assertions.assertThat(expectedBook.getAuthor()).isEqualTo(actualBook.getAuthor());
        Assertions.assertThat(expectedBook.getGenre()).isEqualTo(actualBook.getGenre());
        Assertions.assertThat(actualBook.getComments().size()).isEqualTo(2L);
    }

    @Test
    @DisplayName("Should get book by name")
    void shouldGetBooksByName() {

        Book expectedBook1 = Book.builder()
                .id(EXISTING_ID3)
                .name(EXISTING_NAME3)
                .author(authorRepository.getReferenceById(AUTHOR_ID2))
                .genre(genreRepository.getReferenceById(GENRE_ID1))
                .build();

        Book expectedBook2 = Book.builder()
                .id(EXISTING_ID4)
                .name(EXISTING_NAME4)
                .author(authorRepository.getReferenceById(AUTHOR_ID3))
                .genre(genreRepository.getReferenceById(GENRE_ID2))
                .build();
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(expectedBook1);
        expectedBooks.add(expectedBook2);

        List<Book> actualBooks = bookRepository.findByName(EXISTING_NAME3);

        Assertions.assertThat(actualBooks.size()).isEqualTo(expectedBooks.size());

        for (int i = 0; i < expectedBooks.size(); ++i) {
            Book actualBook = actualBooks.get(i);
            Book expectedBook = expectedBooks.get(i);

            Assertions.assertThat(expectedBook.getId()).isEqualTo(actualBook.getId());
            Assertions.assertThat(expectedBook.getName()).isEqualTo(actualBook.getName());
            Assertions.assertThat(expectedBook.getAuthor()).isEqualTo(actualBook.getAuthor());
            Assertions.assertThat(expectedBook.getGenre()).isEqualTo(actualBook.getGenre());
        }
    }

    @Test
    @DisplayName(("Should insert book"))
    void shouldInsertBook() {

        Book expectedBook = Book.builder()
                .id(5L)
                .name("qwerty")
                .author(authorRepository.getReferenceById(AUTHOR_ID1))
                .genre(genreRepository.getReferenceById(GENRE_ID1))
                .build();
        bookRepository.save(expectedBook);
        Book actualBook = bookRepository.getReferenceById(5L);

        Assertions.assertThat(expectedBook).isEqualTo(actualBook);
    }

    @Test
    @DisplayName("Should update book")
    void shouldUpdateBook() {

        Book expectedBook = Book.builder()
                .id(EXISTING_ID1)
                .name("brand new updated name")
                .build();
        bookRepository.save(expectedBook);
        Book actualBook = bookRepository.getReferenceById(EXISTING_ID1);

        Assertions.assertThat(expectedBook).isEqualTo(actualBook);
    }

    @Test
    @DisplayName("Should delete book by id")
    void shouldDeleteBookById() {

        int beforeSize = bookRepository.findAll().size();
        bookRepository.deleteById(EXISTING_ID4);
        entityManager.flush();

        int afterSize = bookRepository.findAll().size();
        Assertions.assertThat(beforeSize).isEqualTo(afterSize + 1);
    }
}
