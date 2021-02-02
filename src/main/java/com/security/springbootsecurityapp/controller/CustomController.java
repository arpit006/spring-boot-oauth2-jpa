package com.security.springbootsecurityapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@RestController
@RequestMapping(value = "/get")
public class CustomController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping()
    public String get() {
        return "Welcome to get api of Custom App Test";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin")
    public String getA() {
        return "Welcome to get api of Custom App Test";
    }
}
