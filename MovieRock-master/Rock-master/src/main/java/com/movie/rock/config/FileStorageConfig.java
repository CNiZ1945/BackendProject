package com.movie.rock.config;

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfig {
    @Value("${file.upload.dir}")
    private String uploadDir;

    @Bean
    public String getUploadDir() {
        return uploadDir;
    }
}
