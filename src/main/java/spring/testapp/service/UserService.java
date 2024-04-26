package spring.testapp.service;

import org.springframework.stereotype.Service;
import spring.testapp.model.User;
import spring.testapp.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

}
