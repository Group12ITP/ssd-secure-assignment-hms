package com.example.test.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/home")
    public String homeAlias() {
        return "index";
    }

    @GetMapping("/logins")
    public String logins() {
        return "logins";
    }

    @GetMapping("/registers")
    public String registers() {
        return "registers";
    }
}


