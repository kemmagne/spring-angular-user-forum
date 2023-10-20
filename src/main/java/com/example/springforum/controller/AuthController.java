package com.example.springforum.controller;


import com.example.springforum.controller.dto.LoginRequest;
import com.example.springforum.controller.dto.RegisterRequest;
import com.example.springforum.service.AuthService;
import com.example.springforum.service.exception.SpringRedditException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
   @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) throws SpringRedditException {
      authService.signup(registerRequest);
      return new ResponseEntity<>("User Registration Successgul",
              OK);
   }

   @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) throws SpringRedditException {
       authService.verifyAccount(token);
       return new ResponseEntity<>("Account Avtivated Successfully", OK);
   }

   @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest){
       authService.login(loginRequest);

   }
}
