package spring.testapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.testapp.service.ImageStorageService;

import java.util.List;

@Controller
@RequestMapping("/images")
public class ImageUploadController {
    private final ImageStorageService storageService;

    @Autowired
    public ImageUploadController(ImageStorageService storageService) {

        this.storageService = storageService;
    }

    @GetMapping("/gallery")
    public String gallery(Model model) {
        List<String> imageUrls = storageService.listImages("redditcloneimages");
        model.addAttribute("imageUrls", imageUrls);
        return "images/gallery";
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

