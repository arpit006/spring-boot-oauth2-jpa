package com.security.springbootsecurityapp.controller;

import com.security.springbootsecurityapp.config.CustomAuthProvider;
import com.security.springbootsecurityapp.principal.CustomAuthPrincipal;
import com.security.springbootsecurityapp.service.CustomUserDetailsService;
import com.security.springbootsecurityapp.token.CustomAccessToken;
import com.security.springbootsecurityapp.util.OAuth2Util;
import com.security.springbootsecurityapp.vo.LoginVo;
import com.security.springbootsecurityapp.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private TokenStore jdbcTokenStore;

    @Autowired
    private CustomAuthProvider customAuthProvider;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value = "")
    public String createUser(@RequestBody UserVo vo) {
        return customUserDetailsService.createUser(vo);
    }

    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        try {
            String auth = httpServletRequest.getHeader("Authorization");
            if (auth != null && auth.startsWith("Bearer")) {
                auth = auth.replace("Bearer ", "").trim();
                OAuth2AccessToken auth2AccessToken = jdbcTokenStore.readAccessToken(auth);
                jdbcTokenStore.removeAccessToken(auth2AccessToken);

                OAuth2RefreshToken refreshToken = auth2AccessToken.getRefreshToken();
                jdbcTokenStore.removeRefreshToken(refreshToken);
            }
        } catch (Exception e) {
            log.error("Error logging out. Exception is ", e.getMessage());
        }
        return "User Successfully logged out";
    }

    // Logging in as anonymous user to obtain token
    // can set security context explicitly or will be set up once we hit any api with valid token
    @PostMapping(value = "/login")
    public CustomAccessToken login(@RequestBody LoginVo vo,
                                       HttpServletRequest httpServletRequest) {
        String clientID = httpServletRequest.getHeader("x-client-id");
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientID);
        if (clientDetails == null) {
            throw new RuntimeException("This client is not registered with us");
        }

        //TODO: Find a fix for clientSecret being stored as hash and we have to pass raw value here
        OAuth2Util.setupClientData(clientDetails.getClientId(), "password");

        OAuth2AccessToken token = customAuthProvider.authenticate(new UsernamePasswordAuthenticationToken(vo.getUsername(), vo.getPassword(), Collections.emptyList()), vo.getGrantType());
        log.info("Token:: " + token);
        log.info("Security Context updated by extra DB hit to.: " + SecurityContextHolder.getContext());
        return CustomAccessToken.of(token);
    }
}
