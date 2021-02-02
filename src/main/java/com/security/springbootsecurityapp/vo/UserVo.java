package com.security.springbootsecurityapp.vo;

import lombok.Data;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Data
public class UserVo {

    private String name;

    private String username;

    private String email;

    private String phoneNo;

    private String password;

    private String clientID;
}
