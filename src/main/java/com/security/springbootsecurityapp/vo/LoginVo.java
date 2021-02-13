package com.security.springbootsecurityapp.vo;

import lombok.Data;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Data
public class LoginVo {

    private String username;

    private String password;

    private String grantType;
}
