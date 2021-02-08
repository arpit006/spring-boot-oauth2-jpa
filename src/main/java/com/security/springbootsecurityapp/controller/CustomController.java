package com.security.springbootsecurityapp.controller;

import com.security.springbootsecurityapp.config.CustomAuthorizationServerConfigurerAdapter;
import com.security.springbootsecurityapp.principal.CustomAuthPrincipal;
import com.security.springbootsecurityapp.security.CustomSecurityObjects;
import com.security.springbootsecurityapp.util.SecurityContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@RestController
@RequestMapping(value = "/get")
@Slf4j
public class CustomController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping()
    public String get() {
        CustomAuthPrincipal loggedInUSer = SecurityContextUtil.getLoggedInUSer();
        log.info("Getting SecurityContext:: " + loggedInUSer);
        log.info("Custom Header::::: " + CustomSecurityObjects.getMobileNo() + " ::::: " + CustomSecurityObjects.getUsername());
        return "Welcome to get api of Custom App Test " + loggedInUSer.getUsername();
    }

    //todo create an admin user and validate this API
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin")
    public String getA() {
        return "Welcome to get api of Custom App Test";
    }
}
