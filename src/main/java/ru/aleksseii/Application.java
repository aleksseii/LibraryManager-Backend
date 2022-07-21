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
import ru.aleksseii.service.LibraryDemoService;

import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        LibraryDemoService contextBean = context.getBean(LibraryDemoService.class);

        contextBean.commentDemo();

        context.close();

//        try {
//            Console.main(args);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
