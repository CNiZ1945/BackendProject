package com.movie.rock.admin.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAddMovieController {
    @GetMapping("/add")
    public String adminAddPageForm() {
        return "admin_movie_upload";
    }
}
