package ru.aleksseii;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.aleksseii.service.SOutConsole;

import java.sql.SQLException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);


        SOutConsole contextBean = context.getBean(SOutConsole.class);

        contextBean.commentDemo();

        try {
            Console.main(args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        context.close();
    }
}
