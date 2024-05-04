package spring.testapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.testapp.config.SecurityUtility;
import spring.testapp.model.CustomUserDetails;


@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SecurityUtility securityUtility;

    @GetMapping
    public String test() {
        try {
            return securityUtility.getCurrentUserDetails().getUsername();
        } catch (IllegalStateException e) {
            return e.getMessage();  // Properly handle the error message in the response
        }
    }
}
