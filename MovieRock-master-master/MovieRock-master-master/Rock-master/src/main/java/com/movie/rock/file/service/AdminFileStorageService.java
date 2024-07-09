package com.movie.rock.file.service;


import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AdminFileStorageService {
//    @Value("${file.upload.dir}")
    private String uploadDir;

    public String storeFile(byte[] fileBytes, String fileName) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, fileBytes);
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public void deleteFile(String fileUrl) {
        try {
            Path filePath = Paths.get(fileUrl);
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }

    public String uploadFile(MultipartFile posterFile) {
        return this.uploadDir;
    }
}