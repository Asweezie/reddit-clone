package spring.testapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.testapp.service.ImageStorageService;

@Controller
@RequestMapping("/api/images")
public class ImageUploadController {
    private final ImageStorageService storageService;

    @Autowired
    public ImageUploadController(ImageStorageService storageService) {

        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile image, RedirectAttributes redirectAttributes) {

        try {
            String imageUrl = storageService.uploadFile(image);
            redirectAttributes.addFlashAttribute("imageMessage", "Image uploaded successfully: " + imageUrl);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("imageError", "Failed to upload image.");
        }
        return "redirect:/users";
    }
}

