package com.security.springbootsecurityapp.filter;

import com.security.springbootsecurityapp.principal.CustomAuthPrincipal;
import com.security.springbootsecurityapp.security.CustomSecurityObjects;
import com.security.springbootsecurityapp.util.SecurityContextUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Component
public class CustomFilter /*extends OncePerRequestFilter */{

//    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //intercept http requests and set something at global level
        String username = httpServletRequest.getHeader("x-username");
        CustomSecurityObjects.setUsername(username);
//        CustomAuthPrincipal loggedInUSer = SecurityContextUtil.getLoggedInUSer();
//        CustomSecurityObjects.setMobileNo(loggedInUSer.getPhoneNo());

//        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
