package ru.aleksseii.library_manager_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksseii.library_manager_backend.domain.Author;
import ru.aleksseii.library_manager_backend.domain.Book;
import ru.aleksseii.library_manager_backend.domain.Comment;
import ru.aleksseii.library_manager_backend.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SOutConsole implements LibraryDemoService {

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookService bookService;

    private final CommentService commentService;

    @Override
    public void authorDemo() {
        List<Author> authors = authorService.getAll();

        System.out.println("-----------------------------");
        authors.forEach(System.out::println);
        System.out.println("-----------------------------");

        Author ivanAuthor = Author.builder()
                .name("Ivan")
                .build();
        authorService.insert(ivanAuthor);

        authors = authorService.getAll();
        authors.forEach(System.out::println);

        System.out.println("-----------------------------");

        System.out.println(authorService.getByName("First author name") +
                "\n-----------------------------");

        authorService.update(2L, "Updated author name");
        System.out.println(authorService.getById(2L) +
                "\n-----------------------------");
    }

    @Override
    public void genreDemo() {
        List<Genre> genres = genreService.getAll();

        System.out.println("-----------------------------");
        genres.forEach(System.out::println);
        System.out.println("-----------------------------");

        Genre newGenre = Genre.builder()
                .name("new modern genre")
                .build();
        genreService.insert(newGenre);

        genres = genreService.getAll();
        genres.forEach(System.out::println);

        System.out.println("-----------------------------");

        System.out.println(genreService.getByName("First genre name") +
                "\n-----------------------------");

        genreService.update(2L, "Updated genre name");
        System.out.println(authorService.getById(2L) +
                "\n-----------------------------");
    }

    @Transactional
    @Override
    public void bookDemo() {

        List<Book> books = bookService.getAll();

        printBooks(books);

        bookService.insert(
                "Fourth book name",
                "Fourth author name",
                "Fourth genre name"
        );
        books = bookService.getAll();

        printBooks(books);

        List<Book> booksWithThirdName = bookService.getByName("Third book name");
        for (Book book : booksWithThirdName) {
            System.out.format("BookId: %d\tBook name: %s\tAuthor data: %s%n\tComments:",
                    book.getId(), book.getName(), book.getAuthor());

            List<Comment> comments = book.getComments();
            for (Comment comment : comments) {
                System.out.format("%s;\t", comment.getContent());
            }
            System.out.println();
        }
    }

    private static void printBooks(List<Book> books) {
        for (Book book : books) {
            System.out.format("Book: %s:%nAuthor: %s; Genre: %s%n",
                    book.getName(),
                    book.getAuthor().getName(),
                    book.getGenre().getName());

            List<Comment> comments = book.getComments();
            if (comments != null && !comments.isEmpty()) {
                System.out.print("Comments: ");
                for (Comment comment : comments) {
                    System.out.format("%s, ", comment.getContent());
                }
            } else {
                System.out.println("No comments");
            }
            System.out.format("%n-------------------------%n");
        }
    }

    @Transactional
    @Override
    public void commentDemo() {
        
        commentService.update(1L, "new comment 1");

        List<Comment> comments = commentService.getAll();

        for (Comment comment : comments) {

            System.out.format("%s:%n%d -- %s%n",
                    comment.getBook().getName(),
                    comment.getId(),
                    comment.getContent());
        }

        long bookId = 2L;
        String commentContent = "this is second book, right?";

        commentService.insert(bookId, commentContent);

        List<Comment> commentsTo2ndBook = commentService.getByBookId(bookId);
        System.out.println("=======================\nComments to 2nd book:");

        for (Comment comment : commentsTo2ndBook) {
            System.out.format("%d -- %s%n",
                    comment.getId(),
                    comment.getContent());
        }

        System.out.println("===========================");

        comments = commentService.getAll();
        for (Comment comment : comments) {

            System.out.format("%s:%n%d -- %s%n",
                    comment.getBook().getName(),
                    comment.getId(),
                    comment.getContent());
        }
    }
}
