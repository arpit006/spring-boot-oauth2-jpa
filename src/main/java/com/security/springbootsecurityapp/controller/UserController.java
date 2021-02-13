package com.security.springbootsecurityapp.controller;

import com.security.springbootsecurityapp.config.CustomOAuth2AuthenticationProvider;
import com.security.springbootsecurityapp.principal.CustomAuthPrincipal;
import com.security.springbootsecurityapp.service.CustomUserDetailsService;
import com.security.springbootsecurityapp.vo.LoginVo;
import com.security.springbootsecurityapp.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    private TokenStore jdbcTokenStore;

    @Autowired
    private CustomOAuth2AuthenticationProvider authenticationProvider;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Value("${oauth.server}")
    private String server;

    @Value("${oauth.url}")
    private String url;

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

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginVo loginVo,
            HttpServletRequest httpServletRequest) {
        log.info("Login request incoming");
        String clientId = httpServletRequest.getHeader("x-client-id");
        String grantType = httpServletRequest.getHeader("x-grant-type");

//        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
//        if (clientDetails == null) {
//            throw new RuntimeException("No Client exists with this clientID");
//        }
//
//        Oauth2ContextUtil.updateThread(clientDetails);
//        ClientResourceOwnerTokenUtil.setupResourceOwnerPasswordProvider(loginVo.getUsername(), loginVo.getPassword(), grantType, server + url);

        CustomAuthPrincipal customAuthPrincipal =  (CustomAuthPrincipal) (authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword())).getPrincipal());
        log.info("Auth received:: " + customAuthPrincipal.toString());
        return ResponseEntity.ok().body(/*Oauth2ContextUtil.getToken()*/"");
    }
}
