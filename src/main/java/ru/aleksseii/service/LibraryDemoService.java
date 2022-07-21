package ru.aleksseii.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksseii.domain.Author;
import ru.aleksseii.domain.Book;
import ru.aleksseii.domain.Comment;
import ru.aleksseii.repository.AuthorRepository;
import ru.aleksseii.repository.BookRepository;
import ru.aleksseii.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryDemoService implements ILibraryDemo {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    @Override
    public void authorDemo() {
        List<Author> authors = authorRepository.findAll();

        System.out.println("-----------------------------");
        authors.forEach(System.out::println);
        System.out.println("-----------------------------");

        Author ivanAuthor = Author.builder()
                .name("Ivan")
                .build();
        authorRepository.save(ivanAuthor);

        authors = authorRepository.findAll();
        authors.forEach(System.out::println);

        System.out.println("-----------------------------");
    }

    @Transactional
    @Override
    public void bookDemo() {

        List<Book> books = bookRepository.findAll();

        for (Book book : books) {
            System.out.format("Book: %s:%nAuthor: %s; Genre: %s%n",
                    book.getName(),
                    book.getAuthor().getName(),
                    book.getGenre().getName());

            List<Comment> comments = book.getComments();
            System.out.print("Comments: ");
            for (Comment comment : comments) {
                System.out.format("%s, ", comment.getContent());
            }
            System.out.format("%n-------------------------%n");
        }
    }

    @Transactional
    @Override
    public void commentDemo() {
        
        commentRepository.updateCommentById(1, "new comment 1");

        List<Comment> comments = commentRepository.findAll();

        for (Comment comment : comments) {

            System.out.format("%s:%n%d -- %s%n",
                    comment.getBook().getName(),
                    comment.getId(),
                    comment.getContent());
        }

        List<Comment> commentsTo2ndBook = commentRepository.findByBookId(2);
        System.out.println("=======================\nComments to 2nd book:");

        for (Comment comment : commentsTo2ndBook) {
            System.out.format("%d -- %s%n",
                    comment.getId(),
                    comment.getContent());
        }
    }
}
