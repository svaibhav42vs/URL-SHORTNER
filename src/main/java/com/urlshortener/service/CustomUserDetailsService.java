package com.urlshortener.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.urlshortener.repository.UserRepository;

@Service 
public class CustomUserDetailsService implements UserDetailsService{

    public final UserRepository userRepository;


    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user  = userRepository.findByEmail(username)
                 .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

                 return User.withUsername(user.getEmail()).password(user.getPassword()).build();
                        
    }

    
}
