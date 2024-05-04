package spring.testapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.testapp.service.UserService;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";  // name of the Thymeleaf template for the home page
    }

    @PostMapping("/home")
    public void post() {
        System.out.println("hi");
    }
}

