package org.wordcloud;


import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
public class MainController {

    public static void main(String[] args) {
        SpringApplication.run(MainController.class, args);
    }

}