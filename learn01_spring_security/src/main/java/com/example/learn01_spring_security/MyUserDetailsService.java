package com.example.learn01_spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.login(userName);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("找不到用户名");
        }
        org.springframework.security.core.userdetails.User results =
                new org.springframework.security.core.userdetails.User(
                        userName,
                        user.getPassword(),
                        AuthorityUtils.createAuthorityList()
                );
        return results;
    }
}
