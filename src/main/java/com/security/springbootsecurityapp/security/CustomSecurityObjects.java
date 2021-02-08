package com.security.springbootsecurityapp.security;


/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
public class CustomSecurityObjects {

    private static ThreadLocal<String> username = new ThreadLocal<>();

    private static ThreadLocal<String> mobileNo = new ThreadLocal<>();

    public static String getUsername() {
        return username.get();
    }

    public static void setUsername(String name) {
        username.set(name);
    }

    public static String getMobileNo() {
        return mobileNo.get();
    }

    public static void setMobileNo(String no) {
        mobileNo.set(no);
    }
}
