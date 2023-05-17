package vn.btl.hdvcoreservice.controller;

import io.imagekit.sdk.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.btl.hdvcoreservice.service.ImageService;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity uploadImage(MultipartFile file) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException, IOException {
        String url = imageService.uploadImage(file);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @PostMapping("/uploads")
    public ResponseEntity uploadImages(List<MultipartFile> files){
        List<String> urls = imageService.uploadImages(files);
        return ResponseFactory.success(urls, List.class);
    }
}
