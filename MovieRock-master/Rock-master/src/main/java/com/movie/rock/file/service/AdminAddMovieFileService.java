package com.movie.rock.file.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class AdminAddMovieFileService {
    private final String UPLOAD_DIR = "uploads/movies";

    private final AdminFileStorageService adminFileStorageService;

    @Autowired
    public AdminAddMovieFileService(AdminFileStorageService adminFileStorageService) {
        this.adminFileStorageService = adminFileStorageService;
    }

    public String uploadFile(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            return adminFileStorageService.storeFile(fileBytes, fileName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }
}