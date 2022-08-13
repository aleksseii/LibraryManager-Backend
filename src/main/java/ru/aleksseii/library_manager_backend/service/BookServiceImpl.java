package ru.aleksseii.library_manager_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aleksseii.library_manager_backend.domain.Author;
import ru.aleksseii.library_manager_backend.domain.Book;
import ru.aleksseii.library_manager_backend.domain.Genre;
import ru.aleksseii.library_manager_backend.repository.AuthorRepository;
import ru.aleksseii.library_manager_backend.repository.BookRepository;
import ru.aleksseii.library_manager_backend.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    public Book insert(String bookName, String authorName, String genreName) {

        Author author = authorRepository.findByName(authorName);
        if (author == null) {
            author = buildNewAuthor(authorName);
        }

        Genre genre = genreRepository.findByName(genreName);
        if (genre == null) {
            genre = buildNewGenre(genreName);
        }

        Book book = Book.builder()
                .name(bookName)
                .author(author)
                .genre(genre)
                .build();

        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(long id) {
        return bookRepository.getReferenceById(id);
    }

    @Override
    public List<Book> getByName(String bookName) {
        return bookRepository.findByName(bookName);
    }

    @Override
    public Book update(long id, String bookName, String authorName, String genreName) {
        Author author = authorRepository.findByName(authorName);
        if (author == null) {
            author = buildNewAuthor(authorName);
        }

        Genre genre = genreRepository.findByName(genreName);
        if (genre == null) {
            genre = buildNewGenre(genreName);
        }

        Book book = Book.builder()
                .id(id)
                .name(bookName)
                .author(author)
                .genre(genre)
                .build();

        return bookRepository.save(book);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private static Author buildNewAuthor(String authorName) {
        return Author.builder().
                name(authorName)
                .build();
    }

    private static Genre buildNewGenre(String genreName) {
        return Genre.builder()
                .name(genreName)
                .build();
    }
}
