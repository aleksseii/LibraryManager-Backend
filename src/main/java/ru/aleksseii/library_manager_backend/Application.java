package ru.aleksseii.library_manager_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

//        try {
//            Console.main(args);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        context.close();
    }
}
