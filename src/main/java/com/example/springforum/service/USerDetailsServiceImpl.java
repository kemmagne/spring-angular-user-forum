package com.example.springforum.service;

import com.example.springforum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;


@AllArgsConstructor
public class USerDetailsServiceImpl implements UserDetailsService {

   private final UserRepository userRepository;
    @Override
    @Transactional()
    public UserDetails loadUserByUsername(String username) {

    Optional<User> userOptional = userRepository.findByUsername(username);
    User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Message"));

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(),
            true, true, true, getAuthorties("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorties(String role){
        return singletonList(new SimpleGrantedAuthority(role));
    }

}
