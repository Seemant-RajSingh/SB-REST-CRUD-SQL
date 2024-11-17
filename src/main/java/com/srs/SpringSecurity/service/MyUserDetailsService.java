package com.srs.SpringSecurity.service;

import com.srs.SpringSecurity.model.UserPrincipal;
import com.srs.SpringSecurity.model.Users;
import com.srs.SpringSecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByUsername(username);

        if(user == null) {
            System.out.println("User not found!");
            throw new UsernameNotFoundException("User Not Found!");
        }
        return new UserPrincipal(user);
    }
}
