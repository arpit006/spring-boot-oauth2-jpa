package com.security.springbootsecurityapp.util;

import com.security.springbootsecurityapp.principal.CustomAuthPrincipal;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Slf4j
public class SecurityContextUtil  {

    public static CustomAuthPrincipal getLoggedInUSer() {
         OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
         log.info(auth.toString());
         return (CustomAuthPrincipal) auth.getPrincipal();
    }
}
