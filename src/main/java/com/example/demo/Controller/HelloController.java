package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HelloController {
    @GetMapping("")
    public String showHomePage()
    {
        return "index";
    }

}
