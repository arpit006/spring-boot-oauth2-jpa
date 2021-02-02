package com.security.springbootsecurityapp.controller;

import com.security.springbootsecurityapp.service.CustomUserDetailsService;
import com.security.springbootsecurityapp.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping(value = "")
    public String createUser(@RequestBody UserVo vo) {
        return customUserDetailsService.createUser(vo);
    }
}
