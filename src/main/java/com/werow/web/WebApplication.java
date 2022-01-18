package com.werow.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@SpringBootApplication
public class WebApplication {

    @GetMapping
    public String home() {
        return "redirect:/swagger-ui.html";
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
