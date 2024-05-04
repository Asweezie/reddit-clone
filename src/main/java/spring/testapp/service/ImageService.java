package spring.testapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.testapp.model.Image;
import spring.testapp.model.User;
import spring.testapp.repository.ImageRepository;
import spring.testapp.repository.UserRepository;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ImageStorageService imageStorageService;

    @Autowired
    public ImageService(ImageRepository imageRepository, UserRepository userRepository, ImageStorageService imageStorageService) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.imageStorageService = imageStorageService;
    }

    public Image saveImage(MultipartFile file, Long userId) throws IOException {
        String imageUrl = imageStorageService.uploadFile(file, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Image image = new Image();
        image.setImageUrl(imageUrl);
        image.setUser(user);
        return imageRepository.save(image);
    }

    // Additional methods to handle image retrieval, updating, and deletion
}
