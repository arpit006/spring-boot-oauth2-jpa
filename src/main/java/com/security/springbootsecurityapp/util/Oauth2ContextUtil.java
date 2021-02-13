//package com.security.springbootsecurityapp.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.ClientDetails;
//
///**
// * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
// */
//@Slf4j
//public class Oauth2ContextUtil {
//
//    private static ThreadLocal<OAuth2AccessToken> accessToken = new ThreadLocal<>();
//
//    private static ThreadLocal<String> clientSecret = new ThreadLocal<>();
//
//    private static ThreadLocal<String> clientId = new ThreadLocal<>();
//
//    public static void setToken(OAuth2AccessToken token) {
//        accessToken.set(token);
//    }
//
//    public static OAuth2AccessToken getToken() {
//        return accessToken.get();
//    }
//
//    public static void setClientID(String id) {
//        clientId.set(id);
//    }
//
//    public static String getClientID() {
//        return clientId.get();
//    }
//
//    public static void setClientSecret(String secret) {
//        clientSecret.set(secret);
//    }
//
//    public static String getClientSecret() {
//        return clientSecret.get();
//    }
//
//    public static void updateThread(ClientDetails clientDetails) {
//        try {
//            setClientID(clientDetails.getClientId());
//            setClientSecret(clientDetails.getClientSecret());
//        } catch (Exception e) {
//            log.error("Error updating value to thread. Error is " + e.getMessage());
//            throw new RuntimeException("Error setting value to thread. " + e.getMessage());
//        }
//    }
//
//}
