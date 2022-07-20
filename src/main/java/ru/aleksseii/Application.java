package ru.aleksseii;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.aleksseii.domain.Author;
import ru.aleksseii.domain.Book;
import ru.aleksseii.domain.Comment;
import ru.aleksseii.domain.Genre;
import ru.aleksseii.repository.AuthorRepository;
import ru.aleksseii.repository.BookRepository;
import ru.aleksseii.repository.CommentRepository;
import ru.aleksseii.repository.GenreRepository;

import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        List<Author> authors = context.getBean(AuthorRepository.class).findAll();
        List<Genre> genres = context.getBean(GenreRepository.class).findAll();
        List<Book> books = context.getBean(BookRepository.class).findAll();
        List<Comment> comments = context.getBean(CommentRepository.class).findAll();

        authors.forEach(System.out::println);
        genres.forEach(System.out::println);

        context.close();

//        try {
//            Console.main(args);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
