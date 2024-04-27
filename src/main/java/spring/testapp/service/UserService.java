package spring.testapp.service;

import org.springframework.stereotype.Service;
import spring.testapp.model.User;
import spring.testapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

//    CONSTRUCTOR
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    GETS
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

//    CREATION
    public void createUser(User user) {
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

}
