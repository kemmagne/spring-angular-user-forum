package com.example.springforum.service;


import com.example.springforum.controller.dto.LoginRequest;
import com.example.springforum.controller.dto.RegisterRequest;
import com.example.springforum.model.NotificationEmail;
import com.example.springforum.model.User;
import com.example.springforum.model.VerificationToken;
import com.example.springforum.repository.UserRepository;
import com.example.springforum.repository.VerificationTokenRepository;
import com.example.springforum.service.exception.SpringRedditException;
import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final  UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    private final AuthenticationManager authenticationManager;
    private final Mailservice mailservice;

    @Transactional
    public void signup(RegisterRequest registerRequest) throws SpringRedditException {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        generateVerificvationToken(user);

        String token = generateVerificvationToken(user);

        mailservice.sendMail(new NotificationEmail("Please", user.getEmail(),
                "" + token));
    }

    private String generateVerificvationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) throws SpringRedditException {
      Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Toke"));
        fetchUSerAndEnable(verificationToken.get());

    }

    private void fetchUSerAndEnable(VerificationToken verificationToken) {
      String username = verificationToken.getUser().getUsername();
      userRepository.findByUsername(username);
    }


    public void login(LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(),
                loginRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //String token = JwtProvider

    }
}
