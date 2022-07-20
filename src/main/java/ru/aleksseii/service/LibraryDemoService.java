package ru.aleksseii.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksseii.domain.Author;
import ru.aleksseii.domain.Book;
import ru.aleksseii.domain.Comment;
import ru.aleksseii.repository.AuthorRepository;
import ru.aleksseii.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryDemoService implements ILibraryDemo {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

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
}
