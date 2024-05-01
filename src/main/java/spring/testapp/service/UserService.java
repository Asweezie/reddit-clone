package spring.testapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.testapp.dto.UserDTO;
import spring.testapp.model.User;
import spring.testapp.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


//    CONSTRUCTOR
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    GETS
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.isEnabled());
    }

//    public List<User> findAllUsers() {
//        return userRepository.findAll();
//    }

//    CREATION
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }



//    UPDATE
    public User updateUser(Long id,User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword()); // Consider encrypting the password
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }

//    DELETE
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

//    Encrypt old passwords with Bcrypt
public void encodeExistingPasswords() {
    List<spring.testapp.model.User> allUsers = userRepository.findAll();
    allUsers.forEach(user -> {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        userRepository.save(user);
    });
}

}
