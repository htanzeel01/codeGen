package io.swagger.service;

import io.swagger.model.UserToCreate;
import io.swagger.repository.UserToCreateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserToCreateRepository userToCreateRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserToCreate userToCreate =userToCreateRepository.findUserToCreateByUsername(username);
        if(userToCreate==null){
            throw new UsernameNotFoundException("User "+username+" not found");
        }
        UserDetails userDetails=org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(userToCreate.getPassword())
                .authorities(userToCreate.getUserType())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
        return userDetails;
    }
}
