package spring.testapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.testapp.dto.UserDTO;
import spring.testapp.model.User;
import spring.testapp.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }



//    GETS
    @GetMapping
    public String getUsersPage(Model model) {
        List<UserDTO> users = userService.findAllUsers();

        model.addAttribute("users", users);
        // Fetch the list of users from the service layer and add it to the model
        return "users";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        Optional<UserDTO> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "users/profile";
        } else {
            model.addAttribute("error", "User not found");
            return "users/error"; // Assuming 'error' is your error handling template
        }
    }

    @GetMapping("/search")
    public String searchUserById(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<UserDTO> userDTO = userService.findById(id);
        if (userDTO.isPresent()) {
            return "redirect:/users/" + id;
        } else {
            redirectAttributes.addFlashAttribute("error", "User with ID: " + id + " not found.");
            return "redirect:/users";  // Redirect back to the user list if not found
        }
    }


    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/create";
    }


//    POSTS
    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "users/create";
        }

        userService.createUser(user, request);
        return "redirect:/users";
    }

//    UPDATE
    @GetMapping("/edit/{id}")
    public String showUpdateUserForm(@PathVariable long id, Model model) {
        UserDTO user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") @Valid User user,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "users/edit";
        }
        userService.updateUser(id, user);
        return "redirect:/users";
    }

//    DELETE
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }


}
