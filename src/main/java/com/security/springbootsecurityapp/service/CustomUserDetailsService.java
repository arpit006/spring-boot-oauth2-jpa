package com.security.springbootsecurityapp.service;

import com.security.springbootsecurityapp.entity.Role;
import com.security.springbootsecurityapp.entity.User;
import com.security.springbootsecurityapp.enums.RoleName;
import com.security.springbootsecurityapp.principal.CustomAuthPrincipal;
import com.security.springbootsecurityapp.repo.RoleRepository;
import com.security.springbootsecurityapp.repo.UserRepository;
import com.security.springbootsecurityapp.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.Optional;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhoneNo) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmailOrPhoneNoOrUsername(usernameOrEmailOrPhoneNo, usernameOrEmailOrPhoneNo, usernameOrEmailOrPhoneNo);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with the given username or email or phone-no");
        }
        return CustomAuthPrincipal.mapUserToPrincipal(optionalUser.get());
    }

    public String createUser(UserVo vo) {
        if (vo.getClientID() == null) {
            throw new RuntimeException("ClientID cannot be null");
        }
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(vo.getClientID());
        if (clientDetails == null) {
            throw new RuntimeException("No client with the given clientID " + vo.getClientID() + " exists");
        }
        User user = new User();
        user.setName(vo.getName());
        user.setUsername(vo.getUsername());
//        user.setPassword(passwordEncoder.encode(vo.getPassword()));
        user.setPassword(passwordEncoder.encode(vo.getPassword()));
        user.setEmail(vo.getEmail());
        user.setPhoneNo(vo.getPhoneNo());
        user.setClientId(clientDetails.getClientId());
        Optional<Role> byRole = roleRepository.findByRole(RoleName.ROLE_USER);
        /*if (byRole.isEmpty()) {
            Role role = new Role();
            role.setRole(RoleName.ROLE_USER);
            roleRepository.save(role);
        }*/
        user.setRoles(Collections.singletonList(byRole.get()));
        userRepository.save(user);
        return "Signup successful for " + vo.getEmail() + " with ClientID " + clientDetails.getClientId();
    }
}
