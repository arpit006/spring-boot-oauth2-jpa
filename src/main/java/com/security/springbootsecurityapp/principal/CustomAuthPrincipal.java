package com.security.springbootsecurityapp.principal;

import com.security.springbootsecurityapp.entity.User;
import com.security.springbootsecurityapp.vo.LoginVo;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@ToString
@Data
public class CustomAuthPrincipal extends User implements UserDetails {

    private String username;

    private String password;

    private String email;

    private String phoneNo;

//    private String client;
//
//    private String clientSecret;
//
//    private String grantType;

    private List<? extends GrantedAuthority> authorities;

    /*public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }*/

    public static CustomAuthPrincipal mapUserToPrincipal(User user) {
        CustomAuthPrincipal customAuthPrincipal = new CustomAuthPrincipal();
        customAuthPrincipal.setPassword(user.getPassword()); //exception: Empty Encoded Password
        customAuthPrincipal.setUsername(user.getUsername());
        customAuthPrincipal.setEmail(user.getEmail());
        customAuthPrincipal.setPhoneNo(user.getPhoneNo());
        customAuthPrincipal.setAuthorities(user.getRoles().stream().map(ro -> new SimpleGrantedAuthority(ro.getRole().name())).collect(Collectors.toList()));
        return customAuthPrincipal;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setAuthorities(List<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
