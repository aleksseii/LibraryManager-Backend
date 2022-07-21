package ru.aleksseii.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aleksseii.domain.Author;
import ru.aleksseii.domain.Book;
import ru.aleksseii.domain.Genre;
import ru.aleksseii.repository.AuthorRepository;
import ru.aleksseii.repository.BookRepository;
import ru.aleksseii.repository.GenreRepository;

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
            author = Author.builder()
                    .name(authorName)
                    .build();
        }

        Genre genre = genreRepository.findByName(genreName);
        if (genre == null) {
            genre = Genre.builder()
                    .name(genreName)
                    .build();
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
            author = Author.builder()
                    .name(authorName)
                    .build();
        }

        Genre genre = genreRepository.findByName(genreName);
        if (genre == null) {
            genre = Genre.builder()
                    .name(genreName)
                    .build();
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
}
